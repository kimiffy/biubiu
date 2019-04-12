package com.kimiffy.cn.biubiu.utils.aop.aspect;

import android.app.Activity;

import com.kimiffy.cn.biubiu.utils.LogUtil;
import com.kimiffy.cn.biubiu.utils.ToastUtil;
import com.kimiffy.cn.biubiu.utils.aop.annotation.NeedPermission;
import com.kimiffy.cn.biubiu.utils.permission.OnPermission;
import com.kimiffy.cn.biubiu.utils.permission.XXPermissions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.List;


/**
 * Description:申请危险权限的切面
 * Created by kimiffy on 2019/3/7.
 */
@Aspect
public class PermissionAspect {
    private static final String POINTCUT = "execution(@com.kimiffy.cn.biubiu.utils.aop.annotation.NeedPermission * *(..))";

    @Pointcut(POINTCUT)
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")
    public void needPermissionPoint(final ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        NeedPermission annotation = method.getAnnotation(NeedPermission.class);
        String[] permissions = annotation.value();
        if (permissions.length == 0) {
            LogUtil.e("the request permission can not be empty!!");
            return;
        }
        Object aThis = point.getThis();
        Activity context = null;
        if (aThis instanceof Activity) {
            context = (Activity) aThis;
        } else if (aThis instanceof android.app.Fragment) {
            context = ((android.app.Fragment) aThis).getActivity();
        } else if (aThis instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) aThis).getActivity();
        }
        // TODO: 2019/3/8 这里上下文对象是否还需要更多的类型判断
        if (context == null) {
            return;
        }
        final Activity finalContext = context;
        //如果需要更换权限申请框架,可以修改此处的实现方式
        XXPermissions.with(context)
                .permission(permissions)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            try {
                                point.proceed();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        } else {
                            ToastUtil.showToast("部分权限未正常授予,请重新授权");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtil.showToast("被永久拒绝授权，请手动授予权限");
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(finalContext);
                        } else {
                            ToastUtil.showToast("获取权限失败");
                        }
                    }
                });

    }

}
