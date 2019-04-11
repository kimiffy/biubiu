package com.kimiffy.cn.biubiu.utils.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * Description:防止过快点击的切面
 * Created by kimiffy on 2019/3/4.
 */

@Aspect
public class SingleClickAspect {

    private static long lastClickTime = 0;//上一次点击的时间
    private final static int SPACE_TIME = 400;//间隔时间 毫秒

    @Pointcut("execution(@com.kimiffy.cn.biubiu.utils.aop.annotation.SingleClick * *(..))")
    public void methodAnnotated() {

    }
    @Around("methodAnnotated()")
    public void singleClickPoint(ProceedingJoinPoint point) throws Throwable {

        long currentTime = System.currentTimeMillis();
        boolean isClick2 =  currentTime - lastClickTime <= SPACE_TIME;
        if (!isClick2) {
             point.proceed();
             lastClickTime = currentTime;
        }
    }

}
