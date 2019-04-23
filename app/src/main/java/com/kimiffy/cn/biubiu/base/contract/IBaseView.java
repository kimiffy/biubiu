package com.kimiffy.cn.biubiu.base.contract;

import com.kimiffy.cn.biubiu.utils.stateview.StateViewProxy;
import com.kimiffy.cn.biubiu.utils.stateview.IStateView;

/**
 * Description:
 * Created by kimiffy on 2019/3/11.
 */

public interface IBaseView {

    /**
     * 获取状态布局
     * @return
     */
    StateViewProxy getStateView();

    /**
     * 显示内容
     */
    void showContent();

    /**
     * 数据出错
     */
    void showDataError();

    /**
     * 无数据
     */
    void showDataEmpty();

    /**
     * 网络出错
     */
    void showNetError();

    /**
     * 正在加载
     */
    void showLoading();




}
