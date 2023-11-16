package com.lingfenglong.spzx.config;

import com.lingfenglong.spzx.interceptor.LoginAuthInterceptor;
import com.lingfenglong.spzx.properties.UserAuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(UserAuthProperties.class)
public class WebMvcConfiguration implements WebMvcConfigurer {

    private LoginAuthInterceptor loginAuthInterceptor;
    private UserAuthProperties userAuthProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 解决跨域请求问题
        registry
                .addMapping("/admin/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册登录校验的拦截器
        registry.addInterceptor(loginAuthInterceptor)
                .excludePathPatterns(userAuthProperties.getNoAuthUrl())
                .addPathPatterns("/**");
    }

    @Autowired
    public void setLoginAuthInterceptor(LoginAuthInterceptor loginAuthInterceptor) {
        this.loginAuthInterceptor = loginAuthInterceptor;
    }

    @Autowired
    public void setUserAuthProperties(UserAuthProperties userAuthProperties) {
        this.userAuthProperties = userAuthProperties;
    }
}
