package com.kimiffy.cn.biubiu.utils.stateview;

/**
 * Description:状态布局功能接口
 * Created by kimiffy on 2019/3/21.
 */

public interface IStateView {

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
