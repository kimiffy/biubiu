package com.kimiffy.cn.biubiu.utils.aop.aspect;

import com.kimiffy.cn.biubiu.utils.AppUtils;
import com.kimiffy.cn.biubiu.utils.LogUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;

/**
 * Description:打印方法耗时,入参,出参,的切面
 * Created by kimiffy on 2019/3/7.
 */
@Aspect
public class DebugLogAspect {

//    @Pointcut("execution(@com.kimiffy.cn.biubiu.utils.aop.annotation.DebugLog * *(..))")
//    public void methodAnnotated() {
//
//    }

    //    @Around("methodAnnotated()")
//    public void debugLogPoint( ProceedingJoinPoint point) throws Throwable {
//        long beforeTime = System.currentTimeMillis();
//        point.proceed();
//        long afterTime = System.currentTimeMillis();
//        long time=afterTime-beforeTime;
//        LogUtil.d("耗时---> " + time + " ms");
//
//    }

    @Around("execution(!synthetic * *(..)) && onDebugLogMethod()")
    public Object debugLogPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        return methodAnnotated(joinPoint);
    }

    @Pointcut("@within(com.kimiffy.cn.biubiu.utils.aop.annotation.DebugLog)||@annotation(com.kimiffy.cn.biubiu.utils.aop.annotation.DebugLog)")
    public void onDebugLogMethod() {
    }

    private Object methodAnnotated(ProceedingJoinPoint point) throws Throwable {
        Object result;
        if (!AppUtils.isDebug()) {
            result = point.proceed();
        } else {
            long beforeTime = System.currentTimeMillis();
            result = point.proceed();
            long afterTime = System.currentTimeMillis();
            long time = afterTime - beforeTime;
            MethodSignature signature = (MethodSignature) point.getSignature();
            String method = signature.toShortString();
            String inString = ": " + (point.getArgs() != null ? Arrays.deepToString(point.getArgs()) : "");
            String type = ((MethodSignature) point.getSignature()).getReturnType().toString();
            String outString = ": " + ("void".equalsIgnoreCase(type) ? "void" : result);
            LogUtil.d("方法名---> " + method
                    + "  耗时---> " + time + "ms"
                    + "  入参---> " + inString
                    + "  出参---> " + outString);
        }
        return result;
    }


}



