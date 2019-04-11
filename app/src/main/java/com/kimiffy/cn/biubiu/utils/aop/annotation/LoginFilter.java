package com.kimiffy.cn.biubiu.utils.aop.annotation;

import com.kimiffy.cn.biubiu.utils.aop.FilterType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:统一登录管理
 * Created by kimiffy on 2019/3/5.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginFilter {
     //未登录时的行为  默认吐司
     FilterType  value() default FilterType.TOAST;
}
