package com.lingfenglong.spzx.gateway;

import com.lingfenglong.spzx.common.annotation.EnableCommonModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableCommonModule
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
