package com.lingfenglong.spzx.log.annotation;

import com.lingfenglong.spzx.log.enums.BusinessType;
import com.lingfenglong.spzx.log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    String title();

    /**
     * 操作人类别
     */
    OperatorType operatorType() default OperatorType.MANAGER;

    BusinessType businessType() default BusinessType.NOT_SET;

    boolean isSaveRequestData() default true;

    boolean isSaveResponseData() default true;
}
