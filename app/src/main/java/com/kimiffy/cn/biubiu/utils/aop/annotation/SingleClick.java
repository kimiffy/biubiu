package com.kimiffy.cn.biubiu.utils.aop.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:防止过快点击
 * 需要防止过快点击可以使用该注解
 * Created by kimiffy on 2019/3/4.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {
}
