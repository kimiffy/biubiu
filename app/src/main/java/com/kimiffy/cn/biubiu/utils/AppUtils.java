package com.kimiffy.cn.biubiu.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Looper;

import com.kimiffy.cn.biubiu.app.MyApplication;

/**
 * Description:app 工具类
 * Created by kimiffy on 2019/3/11.
 */

public class AppUtils {

    private static Boolean isDebug = null;
    private static Handler mainHandler;

    /**
     * 是否是debug模式
     *
     * @return
     */
    public static boolean isDebug() {
        return isDebug == null ? false : isDebug;
    }

    /**
     * 同步是否是debug模式,需要在application中初始化
     *
     * @param context
     */
    public static void syncIsDebug(Context context) {
        if (isDebug == null) {
            isDebug = context.getApplicationInfo() != null &&
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }

    /**
     * 获取Application上下文对象
     *
     * @return 上下文对象
     */
    public static Context getContext() {
        return MyApplication.getInstance().getApplicationContext();
    }


    /**
     * 获取主线程的Looper
     *
     * @return
     */
    public static Looper getMainLooper() {
        return MyApplication.getInstance().getMainLooper();
    }


    /**
     * 获取主线程的Handler
     *
     * @return
     */
    public static Handler getMainHandler() {
        if (mainHandler == null) {
            return new Handler(MyApplication.getInstance().getMainLooper());
        } else {
            return mainHandler;
        }
    }
}
