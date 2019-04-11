package com.kimiffy.cn.biubiu.utils.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:监测方法执行耗时,并打印log,可以用来做性能检测用
 * Created by kimiffy on 2019/3/7.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DebugLog {

}
