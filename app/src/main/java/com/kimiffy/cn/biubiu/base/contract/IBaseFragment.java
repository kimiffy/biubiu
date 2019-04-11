package com.kimiffy.cn.biubiu.base.contract;

import android.app.Activity;

/**
 * Description:
 * Created by kimiffy on 2019/3/12.
 */

public interface IBaseFragment extends IBaseView {

    /**
     * 返回当前fragment绑定的activity
     *
     * @return mActivity
     */
    Activity getBindActivity();
}
