package com.rzon.myback.controller;

import com.rzon.myback.entity.User;
import com.rzon.myback.model.ResponseData;
import com.rzon.myback.model.Result;
import com.rzon.myback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author pc
 */
@RestController
@RequestMapping("/user")
public class UserInfo {

    @Autowired
    private UserService userService;

//    @PostMapping(value = "/login")
//    public Result login(@RequestBody Map<String, Object> userInfo) {
//        Boolean log = userService.login(userInfo);
//        return ResponseData.success(log);
//    }

    @GetMapping("/info")
    public Result<List<User>> getUserInfo(@RequestParam Map<String, Object> params) {

        List<User> users = userService.getUserInfo(params);
        return ResponseData.success(users);
    }

    @PutMapping("/add")
    public Result<Boolean> addUser(@RequestBody User userInfo) {

        Integer index = userService.addUser(userInfo);
        if (index == 1) {
            return ResponseData.success(true);
        }else {
            return ResponseData.error("用户姓名已存在");
        }
    }
}
