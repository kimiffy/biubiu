package com.kimiffy.cn.biubiu.utils.aop;

/**
 * Description:登录过滤提示
 * Created by kimiffy on 2019/3/6.
 */

public enum FilterType {
    /**
     * 显示吐司
     */
    TOAST,
    /**
     * 直接跳转至登录界面
     */
    JUMP,
    /**
     * 显示提醒未登录的dialog
     */
    DIALOG
}
