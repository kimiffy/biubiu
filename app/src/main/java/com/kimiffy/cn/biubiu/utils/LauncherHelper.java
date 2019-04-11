package com.kimiffy.cn.biubiu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Description:打开新的activity
 * Created by kimiffy on 2019/3/6.
 */

public class LauncherHelper {
    private static LauncherHelper instance;

    /**
     * 单例
     *
     * @return
     */
    public static synchronized LauncherHelper getInstance() {
        if (instance == null) {
            instance = new LauncherHelper();
        }
        return instance;
    }

    /**
     * 打开指定Activity
     *
     * @param context
     * @param class1
     */
    public void launcherActivity(Context context, Class<?> class1) {
        Intent intent = new Intent(context, class1);
        context.startActivity(intent);
    }

    /**
     * 打开指定Activity并传递数据
     *
     * @param context
     * @param class1
     * @param bundle
     */
    public void launcherActivity(Context context, Class<?> class1, Bundle bundle) {
        Intent intent = new Intent(context, class1);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 打开指定Activity并要求获取返回结果
     *
     * @param context
     * @param class1
     * @param requestCode
     */
    public void launcherActivity(Context context, Class<?> class1, int requestCode) {
        Intent intent = new Intent(context, class1);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }


    /**
     * 打开指定Activity传递数据并要求获取返回结果
     *
     * @param context
     * @param class1
     * @param bundle
     * @param requestCode
     */
    public void launcherActivity(Context context, Class<?> class1, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, class1);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 打开指定Activity传递数据并要求获取返回结果
     *
     * @param context
     * @param class1
     * @param bundle
     * @param requestCode
     * @param forResult   是否需要返回值
     */
    public void launcherActivity(Context context, Class<?> class1, Bundle bundle, int requestCode, boolean forResult) {
        Intent intent = new Intent(context, class1);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (forResult)
            ((Activity) context).startActivityForResult(intent, requestCode);
        else
            context.startActivity(intent);

    }
}
