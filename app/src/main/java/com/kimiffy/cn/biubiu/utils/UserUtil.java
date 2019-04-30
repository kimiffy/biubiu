package com.kimiffy.cn.biubiu.utils;

import android.text.TextUtils;

import com.kimiffy.cn.biubiu.constant.SpKey;

/**
 * Description:用户相关工具类
 * Created by kimiffy on 2019/3/6.
 */

public class UserUtil {

    private UserUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 用户是否登录了
     *
     * @return 登录状态
     */
    public static boolean isUserLogin() {
        return !TextUtils.isEmpty(SpUtil.getString(SpKey.LOGIN_INFO, ""));
    }

}
