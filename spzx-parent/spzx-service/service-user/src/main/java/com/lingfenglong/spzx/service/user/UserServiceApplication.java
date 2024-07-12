package com.lingfenglong.spzx.service.user;

import com.lingfenglong.spzx.common.annotation.EnableCommonModule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCommonModule
@SpringBootApplication
@MapperScan(basePackages = "com.lingfenglong.spzx.service.user.mapper")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
