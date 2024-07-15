package com.lingfenglong.spzx.service.order;

import com.lingfenglong.spzx.common.annotation.EnableCommonModule;
import com.lingfenglong.spzx.common.annotation.EnableUserTokenFeignInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.lingfenglong.spzx.feign.cart")
@EnableCommonModule
@EnableUserTokenFeignInterceptor
@MapperScan(basePackages = "com.lingfenglong.spzx.service.order.mapper")
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
