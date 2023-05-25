package com.rzon.myback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author pc
 */
@SpringBootApplication
@MapperScan("com.rzon.myback.mapper")
public class MybackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybackApplication.class, args);
    }

}
