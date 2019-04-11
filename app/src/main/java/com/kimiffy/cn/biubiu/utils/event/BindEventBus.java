package com.kimiffy.cn.biubiu.utils.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:绑定EventBus的注解 需要注册EventBus的类可以使直接使用该注解
 * Created by kimiffy on 2019/2/13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindEventBus {
}
