package com.lingfenglong.spzx.common.config;

import com.lingfenglong.spzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final UserLoginAuthInterceptor userLoginAuthInterceptor;

    public WebMvcConfiguration(UserLoginAuthInterceptor userLoginAuthInterceptor) {
        this.userLoginAuthInterceptor = userLoginAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginAuthInterceptor)
                .addPathPatterns("/api/**");    // 前端路径
    }
}
