package com.kimiffy.cn.biubiu.ui.system.detail.tab;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.SystemDetailBean;
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
 * Description:知识体系文章列表控制层
 * Created by kimiffy on 2019/5/20.
 */

public class SysTabPresenter extends BasePresenter<SysTabContract.View> implements SysTabContract.Presenter {

    private int currentPage;
    private int id;
    private boolean isRefresh;
    private boolean isFirstTimeLoad = true;

    SysTabPresenter(SysTabContract.View view) {
        mView = view;
    }


    @Override
    public void firstFresh(int id) {
        this.id = id;
        Disposable disposable = Observable.timer(Config.LOAD_DELAY_TIME, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        reFresh();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void reFresh() {
        currentPage = 0;
        isRefresh = true;
        getData(id, currentPage);
    }

    @Override
    public void loadMore() {
        currentPage++;
        isRefresh = false;
        getData(id, currentPage);
    }

    @Override
    public void doCollect(int id,final int position) {
        addDisposable(mApiService.collectArticle(id), new BaseObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean bean) {
                mView.collectSuccess(position);
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                mView.collectFail(position,msg);
            }
        });

    }

    @Override
    public void unCollect(int id,final int position) {
        addDisposable(mApiService.unCollectArticle(id), new BaseObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean bean) {
                mView.unCollectSuccess(position);
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                mView.unCollectFail(position,msg);
            }
        });
    }


    private void getData(int id, int page) {
        addDisposable(mApiService.getSystemDetailList(page,id), new BaseObserver<BaseBean<SystemDetailBean>>() {
            @Override
            public void onSuccess(BaseBean<SystemDetailBean> bean) {
                List<SystemDetailBean.DatasBean> datas = bean.data.getDatas();
                if (null != datas&&!datas.isEmpty()) {
                    isFirstTimeLoad = false;
                    mView.showContent();
                    mView.getDataSuccess(datas, isRefresh);
                } else {
                    if (isFirstTimeLoad) {
                        mView.showDataEmpty();
                    }else {
                        mView.noMoreData();
                    }
                }
                if(isRefresh){
                    mView.stopRefresh();
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
                if(isRefresh){
                    mView.stopRefresh();
                }

            }
        });
    }

}
