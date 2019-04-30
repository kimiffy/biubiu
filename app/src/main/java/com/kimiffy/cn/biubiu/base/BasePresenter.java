package com.kimiffy.cn.biubiu.base;


import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.http.ApiManager;
import com.kimiffy.cn.biubiu.http.ApiService;
import com.kimiffy.cn.biubiu.http.callback.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<V extends IBaseView> implements IPresenter<V> {

    protected V mView;
    private CompositeDisposable compositeDisposable;//用于管理observer
    protected ApiService mApiService;

    /**
     * 绑定view 层
     *
     * @param v view
     */
    @Override
    public void attach(V v) {
        if (null == mApiService) {
            mApiService = ApiManager.getApiService();
        }
        this.mView = v;
    }

    /**
     * 解绑view层
     */
    @Override
    public void detach() {
        removeDisposable();
        this.mView = null;
        mApiService = null;
    }


    /**
     * 新增disposable
     *
     * @param observable
     * @param observer
     */
    protected void addDisposable(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    /**
     * 移除disposable
     */
    private void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();//清空订阅,防止内存泄露
            compositeDisposable = null;
        }
    }

}
