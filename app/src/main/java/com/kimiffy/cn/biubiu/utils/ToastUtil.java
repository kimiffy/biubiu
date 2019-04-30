package com.kimiffy.cn.biubiu.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.kimiffy.cn.biubiu.app.MyApplication;

/**
 * Description:吐司工具类
 * Created by kimiffy on 2019/2/13.
 */

public class ToastUtil {

    private ToastUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 显示位于底部的Toast
     *
     * @param msg 内容
     */
    public static void showToast(String msg) {
        Toast.makeText(MyApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示位于底部的Toast
     *
     * @param msg 内容
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示位于底部的Toast
     *
     * @param msgId 内容
     */
    public static void showToast(int msgId) {
        Toast.makeText(MyApplication.getInstance(), msgId, Toast.LENGTH_SHORT).show();
    }


    /**
     * 显示位于中部的Toast
     *
     * @param msg 内容
     */
    public static void showMidToast(String msg) {
        Toast toast = Toast.makeText(MyApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 显示位于中部的Toast
     *
     * @param context 上下文
     * @param msg 内容
     */
    public static void showMidToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 显示位于中部的Toast
     *
     * @param msgId 内容
     */
    public static void showMidToast(int msgId) {
        Toast toast = Toast.makeText(MyApplication.getInstance().getApplicationContext(), msgId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}
