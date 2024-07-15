package com.lingfenglong.spzx.service.cart;

import com.lingfenglong.spzx.common.annotation.EnableCommonModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCommonModule
@EnableFeignClients(basePackages = "com.lingfenglong.spzx.feign.product")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CartServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }
}
