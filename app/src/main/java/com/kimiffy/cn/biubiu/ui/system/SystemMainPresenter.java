package com.kimiffy.cn.biubiu.ui.system;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.SystemBean;
import com.kimiffy.cn.biubiu.constant.Config;
import com.kimiffy.cn.biubiu.http.callback.BaseObserver;
import com.kimiffy.cn.biubiu.http.exception.ErrorType;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:知识体系控制层
 * Created by kimiffy on 2019/5/20.
 */

public class SystemMainPresenter extends BasePresenter<SystemMainContract.View> implements SystemMainContract.Presenter {
    private boolean isFirstTimeLoad=true;
    SystemMainPresenter(SystemMainContract.View view) {
        mView = view;
    }

    @Override
    public void firstFresh() {
        Disposable disposable = Observable.timer(Config.LOAD_DELAY_TIME, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        refresh();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void refresh() {
        addDisposable(mApiService.getSystemList(), new BaseObserver<BaseBean<List<SystemBean>>>() {
                    @Override
                    public void onSuccess(BaseBean<List<SystemBean>> bean) {
                        List<SystemBean> data = bean.data;
                        if (!data.isEmpty()) {
                            isFirstTimeLoad=false;
                            mView.showContent();
                            mView.getDataSuccess(bean.data);
                        }else {
                            if (isFirstTimeLoad) {
                                mView.showDataEmpty();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String msg, ErrorType errorType) {
                        if (isFirstTimeLoad) {
                            if (errorType.ordinal() == 0) {
                                mView.showNetError();
                            } else {
                                mView.showDataError();
                            }
                        }
                           mView.getDataFail(msg);
                    }
                }
        );
    }
}
