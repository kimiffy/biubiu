package com.kimiffy.cn.biubiu.ui.search.result;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.ArticleBean;

import java.util.List;

/**
 * Description:搜索结果页契约类
 * Created by kimiffy on 2019/5/29.
 */

public class SearchResultContract {

    public interface Presenter extends IPresenter<SearchResultContract.View> {

        void firstFresh(String key);

        void refresh();

        void loadMore();

        void getArticleList(int page);
        void doCollect(int id,int position);

        void unCollect(int id,int position);
    }


    public interface View extends IBaseView {
        void getArticleListSuccess(List<ArticleBean.DatasBean> dataBean, boolean isRefresh);

        void getArticleListFail(String info);

        void stopRefresh();

        void noMoreData();

        void collectSuccess(int position);

        void collectFail(int position, String msg);

        void unCollectSuccess(int position);

        void unCollectFail(int position, String msg);
    }


}
