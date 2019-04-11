package com.kimiffy.cn.biubiu.utils.imageloader.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description:动态代理图片加载
 * Created by kimiffy on 2019/2/14.
 */
public class ImageLoaderHandler implements InvocationHandler {
    private Object target;

    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = null;
        try {
            result = method.invoke(target, args);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            System.out.println(e.toString());
        }
        return result;
    }
}
