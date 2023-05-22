package com.rzon.myback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rzon.myback.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author pc
 */
public interface UserService extends IService<User> {

    public List<User> getUserInfo(Map<String, Object> params);

    public Integer addUser(User userInfo);

//    public Boolean login(Map<String, Object> userInfo);

}
