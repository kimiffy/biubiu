package com.kimiffy.cn.biubiu.utils;

import android.text.TextUtils;

import com.kimiffy.cn.biubiu.constant.SpKey;

/**
 * Description:用户相关工具类
 * Created by kimiffy on 2019/3/6.
 */

public class UserUtil {

    /**
     * 用户是否登录了
     *
     * @return
     */
    public static boolean isUserLogin() {
        return !TextUtils.isEmpty(SpUtil.getString(SpKey.LOGIN_INFO, ""));
    }

}
