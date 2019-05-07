package com.kimiffy.cn.biubiu.ui.wechat.tab;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.WxArticleListBean;
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

    WeChatTabPresenter(WeChatTabContract.View view) {
        mView = view;
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
    public void getData(int id, int page) {
        this.id = id;
        this.currentPage = page;
        addDisposable(mApiService.getWxArticleList(id, page), new BaseObserver<BaseBean<WxArticleListBean>>() {
            @Override
            public void onSuccess(BaseBean<WxArticleListBean> bean) {
                WxArticleListBean data = bean.data;
                if(null!=data){
                    mView.getDataSuccess(data, isRefresh);
                }
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                mView.getDataFail(msg);
            }
        });
    }

}
