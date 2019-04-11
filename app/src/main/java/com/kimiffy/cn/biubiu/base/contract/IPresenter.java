package com.kimiffy.cn.biubiu.base.contract;


import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.Observer;

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
