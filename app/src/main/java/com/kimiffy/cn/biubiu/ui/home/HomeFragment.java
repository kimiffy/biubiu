package com.kimiffy.cn.biubiu.ui.home;

import android.os.Bundle;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPFragment;
import com.kimiffy.cn.biubiu.bean.ArticleBean;

/**
 * Description:首页
 * Created by kimiffy on 2019/4/23.
 */

public class HomeFragment extends BaseMVPFragment<HomePresenter> implements HomeContract.View {
    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initUI() {

    }

    @Override
    public void getArticleListSuccess(ArticleBean dataBean, boolean isRefresh) {

    }

    @Override
    public void getArticleListFail(String info) {

    }

    @Override
    public void getBannerSuccess() {

    }

    @Override
    public void getBannerFail() {

    }
}
