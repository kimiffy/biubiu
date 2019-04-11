package com.kimiffy.cn.biubiu.utils.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:权限申请注解
 * Created by kimiffy on 2019/3/7.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NeedPermission {
    //需要申请的危险权限数组
    String[] value();

}
