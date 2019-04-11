package com.kimiffy.cn.biubiu.utils.aop.aspect;

import android.os.Handler;
import android.os.Looper;

import com.kimiffy.cn.biubiu.utils.AppUtils;
import com.kimiffy.cn.biubiu.utils.LogUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Description:主线程执行方法切面
 * Created by kimiffy on 2019/3/18.
 */

@Aspect
public class MainThreadAspect {

    @Pointcut("execution(@com.kimiffy.cn.biubiu.utils.aop.annotation.MainThread * *(..))")
    public void methodAnnotated() {

    }

    @Around("methodAnnotated()")
    public void MainThreadPoint(final ProceedingJoinPoint point) throws Throwable {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            point.proceed();
        } else {
            Handler mainHandler = AppUtils.getMainHandler();
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        point.proceed();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
