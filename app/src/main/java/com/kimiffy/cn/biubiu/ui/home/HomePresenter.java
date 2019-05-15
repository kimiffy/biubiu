package com.kimiffy.cn.biubiu.ui.home;

import android.os.Handler;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.ArticleBean;
import com.kimiffy.cn.biubiu.bean.UserBean;
import com.kimiffy.cn.biubiu.constant.Config;
import com.kimiffy.cn.biubiu.database.model.LoginInfo;
import com.kimiffy.cn.biubiu.http.callback.BaseObserver;
import com.kimiffy.cn.biubiu.http.exception.ErrorType;
import com.kimiffy.cn.biubiu.utils.UserUtil;

import java.util.List;

/**
 * Description:首页控制层
 * Created by kimiffy on 2019/4/23.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private boolean isRefresh;
    private int currentPage;

    HomePresenter(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void firstFresh() {
        LoginInfo loginInfo = UserUtil.getLoginInfo();
        if (null != loginInfo) {
            String username = loginInfo.username;
            String password = loginInfo.password;
            autoLogin(username, password);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refresh();
                }
            }, Config.LOAD_DELAY_TIME);
        }
    }


    @Override
    public void refresh() {
        isRefresh = true;
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
    public void getBanner() {

    }

    @Override
    public void getArticleList(int page) {

        addDisposable(mApiService.getArticleList(page), new BaseObserver<BaseBean<ArticleBean>>() {
            @Override
            public void onSuccess(BaseBean<ArticleBean> bean) {
                List<ArticleBean.DatasBean> datas = bean.data.getDatas();
                if (!datas.isEmpty()) {
                    mView.getArticleListSuccess(bean.data, isRefresh);
                }
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                mView.getArticleListFail(msg);
            }
        });

    }


    /**
     * 自动登录
     *
     * @param username 账号
     * @param password 密码
     */
    private void autoLogin(String username, final String password) {
        addDisposable(mApiService.login(username, password), new BaseObserver<BaseBean<UserBean>>() {
            @Override
            public void onSuccess(BaseBean<UserBean> bean) {
                UserBean userBean = bean.data;
                UserUtil.handleLoginInfo(userBean, password);
                refresh();
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                refresh();
            }
        });
    }


}
