package com.kimiffy.cn.biubiu.utils.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:子线程执行方法切面
 * Created by kimiffy on 2019/3/7.
 */
@Aspect
public class IOThreadAspect {

    @Pointcut("execution(@com.kimiffy.cn.biubiu.utils.aop.annotation.IOThread * *(..))")
    public void methodAnnotated() {

    }

    @Around("methodAnnotated()")
    public void IOThreadPoint(final ProceedingJoinPoint point) throws Throwable {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    point.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }
}
