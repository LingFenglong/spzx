package com.lingfenglong.spzx.common.annotation;

import com.lingfenglong.spzx.common.feign.UserTokenFeignInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
        UserTokenFeignInterceptor.class
})
public @interface EnableUserTokenFeignInterceptor {
}
