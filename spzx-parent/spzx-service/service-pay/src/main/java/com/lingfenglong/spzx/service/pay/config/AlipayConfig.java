package com.lingfenglong.spzx.service.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.lingfenglong.spzx.service.pay.properties.AlipayProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {

    @Bean
    public AlipayClient alipayClient(AlipayProperties alipayProperties) {
        return new DefaultAlipayClient(
                alipayProperties.getAlipayUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getAppPrivateKey(),
                alipayProperties.format,
                alipayProperties.charset,
                alipayProperties.alipayPublicKey,
                alipayProperties.sign_type
        );
    }


}
