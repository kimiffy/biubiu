package com.kimiffy.cn.biubiu.ui.home;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.ArticleBean;

/**
 * Description:首页契约类
 * Created by kimiffy on 2019/4/23.
 */

public class HomeContract {

    public interface Presenter extends IPresenter<HomeContract.View> {

        void firstFresh();

        void refresh();

        void loadMore();

        void getBanner();

        void getArticleList(int page);
    }

    public interface View extends IBaseView {
        void getArticleListSuccess(ArticleBean dataBean, boolean isRefresh);

        void getArticleListFail(String info);

        void getBannerSuccess();

        void getBannerFail();
    }
}
