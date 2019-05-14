package com.kimiffy.cn.biubiu.ui.articledetail;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.http.callback.BaseObserver;
import com.kimiffy.cn.biubiu.http.exception.ErrorType;

/**
 * Description:文章详情控制
 * Created by kimiffy on 2019/5/13.
 */

public class ArticleDetailPresenter extends BasePresenter<ArticleDetailContract.View> implements ArticleDetailContract.Presenter {


    ArticleDetailPresenter(ArticleDetailContract.View view) {
        mView = view;
    }


    @Override
    public void collectClick(Boolean isCollected, int id) {
        if (isCollected) {
            unCollect(id);
        } else {
            doCollect(id);
        }
    }

    @Override
    public void doCollect(int id) {
        addDisposable(mApiService.collectArticle(id), new BaseObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean bean) {
                mView.collectSuccess();
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                mView.collectFail(msg);
            }
        });
    }

    @Override
    public void unCollect(int id) {
        addDisposable(mApiService.unCollectArticle(id), new BaseObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean bean) {
                mView.unCollectSuccess();
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                mView.unCollectFail(msg);
            }
        });
    }
}
