package com.rzon.myback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzon.myback.entity.User;
import com.rzon.myback.mapper.UserMapper;
import com.rzon.myback.service.UserService;
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
    public Integer addUser(User userInfo) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("name", userInfo.getName());
        List<User> users = userMapper.selectList(query);
        if (users == null || users.size() == 0) {
            Integer index = this.baseMapper.insert(userInfo);
            return index;
        } else {
          return -1;
        }
    }
}
