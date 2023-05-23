package com.rzon.myback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzon.myback.entity.User;
import com.rzon.myback.mapper.UserMapper;
import com.rzon.myback.service.UserService;
import com.rzon.myback.utils.AesUtil;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author pc
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
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
    public Integer addUser(@NotNull User userInfo) {
        if(userInfo.getUsername() == null || userInfo.getPassword() == null) {
            return -1;
        }
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("username", userInfo.getUsername());
        List<User> users = userMapper.selectList(query);
        if (users == null || users.size() == 0) {
            try {
                String encrypedPwd = AesUtil.encryptDO(userInfo.getPassword());
                userInfo.setPassword(encrypedPwd);
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
        if(userInfo.getUsername() == null || userInfo.getPassword() == null) {
            data.put("flag", false);
            data.put("msg", "用户名、密码不能为空");
            return data;
        }

        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("username", userInfo.getUsername());
        User user = userMapper.selectOne(query);
        if (user == null) {
            data.put("flag", false);
            data.put("msg", "用户不存在");
            return data;
        } else {
            if(user.getPassword().equals(AesUtil.encryptDO(userInfo.getPassword()))) {
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
