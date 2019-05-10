package com.kimiffy.cn.biubiu.ui.wechat.tab;

import android.os.Handler;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.WxArticleListBean;
import com.kimiffy.cn.biubiu.constant.Config;
import com.kimiffy.cn.biubiu.http.callback.BaseObserver;
import com.kimiffy.cn.biubiu.http.exception.ErrorType;

/**
 * Description:公众号文章列表控制层
 * Created by kimiffy on 2019/5/3.
 */

public class WeChatTabPresenter extends BasePresenter<WeChatTabContract.View> implements WeChatTabContract.Presenter {

    private int currentPage;
    private int id;
    private boolean isRefresh;
    private boolean isFirstTimeLoad = true;

    WeChatTabPresenter(WeChatTabContract.View view) {
        mView = view;
    }


    @Override
    public void firstFresh(int id) {
        this.id = id;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reFresh();
            }
        }, Config.LOAD_DELAY_TIME);

    }

    @Override
    public void reFresh() {
        currentPage = 1;
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
        addDisposable(mApiService.getWxArticleList(id, page), new BaseObserver<BaseBean<WxArticleListBean>>() {
            @Override
            public void onSuccess(BaseBean<WxArticleListBean> bean) {
                WxArticleListBean data = bean.data;
                if (null != data) {
                    isFirstTimeLoad = false;
                    mView.showContent();
                    mView.getDataSuccess(data, isRefresh);
                } else {
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
        });
    }

}
