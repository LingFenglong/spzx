package com.lingfenglong.spzx;

import com.lingfenglong.spzx.log.annotation.EnableLog;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = {
        "com.lingfenglong.spzx.mapperplus",
        "com.lingfenglong.spzx.mapper"
})
@EnableScheduling
@EnableAsync
@EnableLog
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
