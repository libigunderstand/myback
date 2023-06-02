package com.rzon.myback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.rzon.myback.entity.User;
import com.rzon.myback.mapper.UserMapper;
import com.rzon.myback.service.UserService;
import com.rzon.myback.utils.AesUtil;
import com.rzon.myback.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author pc
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<User> getUserInfo(Map<String, Object> params) {
        List<User> users;
        QueryWrapper<User> query = new QueryWrapper<>();
        System.out.println(params);
        if(!params.isEmpty()) {
            params.forEach((key, value)->query.eq(key, value));
            users = userMapper.selectList(query);
        }else {
            users = new ArrayList<>(0);
        }
        return users;
    }

    @Override
    public Integer addUser(User userInfo) {
        if(userInfo.getTickname().isEmpty() || userInfo.getPassword().isEmpty()) {
            return -1;
        }
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("tickname", userInfo.getTickname());
        List<User> users = userMapper.selectList(query);
        if (users == null || users.size() == 0) {
            try {
                String encrypedPwd = AesUtil.encryptDO(userInfo.getPassword());
                userInfo.setPassword(encrypedPwd);
                if(userInfo.getGender() == null) {
                    userInfo.setGender(0);
                }
                return userMapper.insert(userInfo);
            }catch (Exception ex) {
                System.out.println(ex.getMessage());
                return -1;
            }
        } else {
          return -2;
        }
    }


    @Override
    public Map<String, Object> login(User userInfo) throws Exception {
        Map<String, Object> data = new HashMap<>();
        if(userInfo.getTickname() == null || userInfo.getPassword() == null) {
            data.put("flag", false);
            data.put("msg", "用户名、密码不能为空");
            return data;
        }

        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("tickname", userInfo.getTickname());
        User user = userMapper.selectOne(query);
        if (user == null) {
            data.put("flag", false);
            data.put("msg", "用户不存在");
            return data;
        } else {
            if(user.getPassword().equals(AesUtil.encryptDO(userInfo.getPassword()))) {
                Boolean hasToken = stringRedisTemplate.hasKey(user.getId());
                if(Boolean.TRUE.equals(hasToken)) {
                    stringRedisTemplate.delete(user.getId());
                }
                String token = JwtUtil.getToken(user);
                stringRedisTemplate.opsForValue().set(user.getId(), token);

                LocalDateTime loginTime = LocalDateTime.now();
                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", user.getId());
                user.setLogin_time(loginTime);
                userMapper.update(user, updateWrapper);

                data.put("token", token);
                data.put("flag", true);
                data.put("msg", "登录成功");
                return data;
            }else {
                data.put("flag", false);
                data.put("msg", "用户名、密码错误");
                return data;
            }
        }
    }
}
