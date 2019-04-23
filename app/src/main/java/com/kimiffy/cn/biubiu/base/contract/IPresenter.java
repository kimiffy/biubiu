package com.kimiffy.cn.biubiu.base.contract;


public interface IPresenter<V extends IBaseView> {
    /**
     * 关联P与V
     *
     * @param v
     */
    void attach(V v);

    /**
     * 取消关联P与V
     */
    void detach();

}
