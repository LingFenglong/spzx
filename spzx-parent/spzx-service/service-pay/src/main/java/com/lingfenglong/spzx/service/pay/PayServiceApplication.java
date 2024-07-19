package com.lingfenglong.spzx.service.pay;

import com.lingfenglong.spzx.feign.order.OrderFeignClient;
import com.lingfenglong.spzx.service.pay.properties.AlipayProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackageClasses = { OrderFeignClient.class })
@EnableConfigurationProperties({ AlipayProperties.class })
@SpringBootApplication
@MapperScan(basePackages = "com.lingfenglong.spzx.service.pay.mapper")
public class PayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayServiceApplication.class, args);
    }
}
