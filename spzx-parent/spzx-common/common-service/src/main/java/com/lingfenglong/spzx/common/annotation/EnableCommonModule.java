package com.lingfenglong.spzx.common.annotation;

import com.lingfenglong.spzx.common.config.Knife4jConfig;
import com.lingfenglong.spzx.common.config.SecurityConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ Knife4jConfig.class, SecurityConfig.class })
public @interface EnableCommonModule {
}
