package com.rzon.myback;

import com.rzon.myback.entity.User;
import com.rzon.myback.mapper.UserMapper;
import com.rzon.myback.utils.AesUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybackApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findAll() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void Encrypt() throws Exception {
        String encypted = AesUtil.encrypt("ys123456");
        System.out.println(encypted);
    }

}
