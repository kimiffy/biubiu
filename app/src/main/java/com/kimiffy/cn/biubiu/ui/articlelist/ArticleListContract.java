package com.kimiffy.cn.biubiu.ui.articlelist;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.ArticleBean;

/**
 * Description:文章列表契约
 * Created by kimiffy on 2019/3/13.
 */

public class ArticleListContract {

    public interface Presenter extends IPresenter<View> {
        void refresh();

        void loadMore();

        void getArticleList(int page);

    }

    public interface View extends IBaseView {

        void getArticleListSuccess(ArticleBean dataBean, boolean isRefresh);

        void getArticleListFail(String info);
    }
}
