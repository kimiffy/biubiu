package com.kimiffy.cn.biubiu.ui.home;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.ArticleBean;
import com.kimiffy.cn.biubiu.bean.BannerBean;

import java.util.List;

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

        void doCollect(int id,int position);

        void unCollect(int id,int position);

        void getHotWord();

    }

    public interface View extends IBaseView {
        void getArticleListSuccess(List<ArticleBean.DatasBean> dataBean, boolean isRefresh);

        void getArticleListFail(String info);

        void stopRefresh();

        void noMoreData();

        void getBannerSuccess(List<BannerBean> data);

        void getBannerFail(String msg);

        void collectSuccess(int position);

        void collectFail(int position, String msg);

        void unCollectSuccess(int position);

        void unCollectFail(int position, String msg);

        void getHotWordSuccess(String data);

    }
}
