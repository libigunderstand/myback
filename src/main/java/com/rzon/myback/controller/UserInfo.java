package com.rzon.myback.controller;

import com.rzon.myback.entity.User;
import com.rzon.myback.model.ResponseData;
import com.rzon.myback.model.Result;
import com.rzon.myback.model.ResultsCode;
import com.rzon.myback.service.UserService;
import com.rzon.myback.utils.JwtUtil;
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

    @PostMapping(value = "/login")
    public Result login(@RequestBody User userInfo) throws Exception {
        Map<String, Object> log = userService.login(userInfo);
        if (log.get("flag").equals(false)){
            return ResponseData.error(log.get("msg").toString());
        }
        String token = JwtUtil.getToken(userInfo);
        log.put("token", token);
        return ResponseData.success(log);
    }

    @GetMapping("/info")
    public Result<List<User>> getUserInfo(@RequestParam Map<String, Object> params) {

        List<User> users = userService.getUserInfo(params);
        return ResponseData.success(users);
    }

    @PostMapping("/add")
    public Result<Boolean> addUser(@RequestBody User userInfo) throws Exception {
        Integer index = userService.addUser(userInfo);
        if (index == -1) {
            return ResponseData.error("用户名、密码不能为空");
        } else if (index == -2) {
            return ResponseData.error("用户名已存在");
        }
        return ResponseData.success(true);
    }
}
