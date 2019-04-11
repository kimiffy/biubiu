package com.kimiffy.cn.biubiu.utils.aop.aspect;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.kimiffy.cn.biubiu.ui.login.LoginActivity;
import com.kimiffy.cn.biubiu.utils.LauncherHelper;
import com.kimiffy.cn.biubiu.utils.LogUtil;
import com.kimiffy.cn.biubiu.utils.ToastUtil;
import com.kimiffy.cn.biubiu.utils.UserUtil;
import com.kimiffy.cn.biubiu.utils.aop.annotation.LoginFilter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Description:统一登录管理切面
 * Created by kimiffy on 2019/3/5.
 */

@Aspect
public class LoginFilterAspect {

    // private static final String POINTCUT = "execution(@com.kimiffy.cn.biubiu.utils.aop.annotation.LoginFilter * *(..))";
    private static final String POINTCUT = "@within(com.kimiffy.cn.biubiu.utils.aop.annotation.LoginFilter)||@annotation(com.kimiffy.cn.biubiu.utils.aop.annotation.LoginFilter)";

    @Pointcut(POINTCUT)
    public void loginFilterPointCut() {
    }

    @Around("execution(!synthetic * *(..)) && loginFilterPointCut()")
    public Object loginFilterPoint(ProceedingJoinPoint point) throws Throwable {
        boolean isLogin = UserUtil.isUserLogin();
        Object result=null;
        if (isLogin) {
             result = point.proceed();
        } else {
            Context context = null;
            Object aThis = point.getThis();
            if (aThis instanceof Activity) {
                context = (Activity) aThis;
            } else if (aThis instanceof Fragment) {
                context = ((Fragment) aThis).getActivity();
            } else if (aThis instanceof android.support.v4.app.Fragment) {
                context = ((android.support.v4.app.Fragment) aThis).getActivity();
            }
            // TODO: 2019/3/8 这里上下文对象是否还需要更多的类型判断
            if (context == null) {
                LogUtil.e("LoginFilterAspect  context is null  !!");
            }
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Method method = methodSignature.getMethod();
            LoginFilter annotation = method.getAnnotation(LoginFilter.class);
            int ordinal = annotation.value().ordinal();
            switch (ordinal) {
                case 0:
                    ToastUtil.showToast(context, "请先登录");
                    break;
                case 1:
                    LauncherHelper.getInstance().launcherActivity(context, LoginActivity.class);
                    break;
                case 2:
                    showDialog(context);
                    break;
                default:
                    ToastUtil.showToast(context, "请先登录");
                    break;

            }


        }
        return result;
    }


    /**
     * 显示一个dialog 提示登录
     */
    private void showDialog(final Context context) {

        new AlertDialog.Builder(context)
                .setTitle("登录提示")
                .setMessage("当前还未登录,是否前往登录?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("前往登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        LauncherHelper.getInstance().launcherActivity(context, LoginActivity.class);
                    }
                }).show();

    }

}
