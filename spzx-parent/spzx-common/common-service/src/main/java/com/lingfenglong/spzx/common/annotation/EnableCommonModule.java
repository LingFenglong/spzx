package com.lingfenglong.spzx.common.annotation;

import com.lingfenglong.spzx.common.config.Knife4jConfig;
import com.lingfenglong.spzx.common.config.RedisConfig;
import com.lingfenglong.spzx.common.config.SecurityConfig;
import com.lingfenglong.spzx.common.config.WebMvcConfiguration;
import com.lingfenglong.spzx.common.exception.CommonGlobalRuntimeException;
import com.lingfenglong.spzx.common.exception.ExceptionController;
import com.lingfenglong.spzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
        Knife4jConfig.class,
        RedisConfig.class,
        SecurityConfig.class,
        WebMvcConfiguration.class,
        ExceptionController.class,
        UserLoginAuthInterceptor.class
})
public @interface EnableCommonModule {
}
