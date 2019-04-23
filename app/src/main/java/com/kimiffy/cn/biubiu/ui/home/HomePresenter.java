package com.kimiffy.cn.biubiu.ui.home;

import com.kimiffy.cn.biubiu.base.BasePresenter;

/**
 * Description:首页控制层
 * Created by kimiffy on 2019/4/23.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{


    HomePresenter(HomeContract.View view) {
        mView = view;
    }


    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getBanner() {

    }

    @Override
    public void getArticleList(int page) {

    }
}
