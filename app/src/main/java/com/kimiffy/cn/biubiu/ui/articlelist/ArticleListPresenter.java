package com.kimiffy.cn.biubiu.ui.articlelist;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.ArticleBean;
import com.kimiffy.cn.biubiu.http.callback.BaseObserver;
import com.kimiffy.cn.biubiu.http.exception.ErrorType;
import com.kimiffy.cn.biubiu.utils.LogUtil;

import java.util.List;

/**
 * Description:文章列表控制层
 * Created by kimiffy on 2019/3/13.
 */

public class ArticleListPresenter extends BasePresenter<ArticleListContract.View> implements ArticleListContract.Presenter {

    private boolean isRefresh;
    private int currentPage;
    private boolean isFirstTimeLoadData = true;

    ArticleListPresenter(ArticleListContract.View view) {
        mView = view;
    }

    @Override
    public void refresh() {
        isRefresh = true;
        if (isFirstTimeLoadData) {
            mView.showLoading();
        }
        currentPage = 0;
        getArticleList(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getArticleList(currentPage);
    }

    @Override
    public void getArticleList(int page) {

        addDisposable(mApiService.getArticleList(page), new BaseObserver<BaseBean<ArticleBean>>() {
            @Override
            public void onSuccess(BaseBean<ArticleBean> bean) {
                List<ArticleBean.DatasBean> datas = bean.data.getDatas();
                if (datas.isEmpty()) {
                    if (isFirstTimeLoadData) {
                        mView.showDataEmpty();
                    }
                } else {
                    isFirstTimeLoadData = false;
                    mView.showContent();
                    mView.getArticleListSuccess(bean.data, isRefresh);
                }
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                if (isFirstTimeLoadData) {
                    LogUtil.d("errorType" +errorType.ordinal());
                    if (errorType.ordinal() == 0) {
                        mView.showNetError();
                    } else {
                        mView.showDataError();
                    }
                }
                mView.getArticleListFail(msg);
            }
        });

    }
}
