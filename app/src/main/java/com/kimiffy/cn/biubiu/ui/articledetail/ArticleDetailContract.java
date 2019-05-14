package com.kimiffy.cn.biubiu.ui.articledetail;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.ui.home.HomeContract;

/**
 * Description:文章详情契约
 * Created by kimiffy on 2019/5/13.
 */

public class ArticleDetailContract {


    public interface Presenter extends IPresenter<ArticleDetailContract.View> {
        void collectClick(Boolean isCollected,int id);

        void doCollect(int id);

        void unCollect(int id);
    }

    public interface View extends IBaseView {
        void collectSuccess();

        void collectFail(String msg);

        void unCollectSuccess();

        void unCollectFail(String msg);
    }

}
