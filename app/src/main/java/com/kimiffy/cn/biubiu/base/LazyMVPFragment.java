package com.kimiffy.cn.biubiu.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.kimiffy.cn.biubiu.base.contract.IBaseFragment;
import com.kimiffy.cn.biubiu.utils.LogUtil;
import com.kimiffy.cn.biubiu.utils.stateview.IStateView;
import com.kimiffy.cn.biubiu.utils.stateview.StateViewProxy;

/**
 * Description: mvp 懒加载 fragment
 * Created by kimiffy on 2019/3/13.
 */

public abstract class LazyMVPFragment<P extends BasePresenter> extends BaseFragment implements IBaseFragment , IStateView {
    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 是否已经加载过数据
     */
    private boolean isDataLoaded;

    /**
     * 具体的presenter由子类确定
     */
    protected P mPresenter;

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();

    /**
     * 状态布局
     */
    protected StateViewProxy mStateView;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hasCreateView = true;
        preLazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        if (getUserVisibleHint()) {
            preLazyLoad();
        }
    }

    private void preLazyLoad() {
        if (hasCreateView && getUserVisibleHint()&&!isDataLoaded) {
            lazyLoadData();
            isDataLoaded=true;
        }
    }

    /**
     * 懒加载数据
     */
    protected abstract void lazyLoadData();

    /**
     * 获取当前绑定的activity
     *
     * @return 当前绑定的activity
     */
    @Override
    public Activity getBindActivity() {
        return mActivity;
    }


    /**
     * 创建状态布局
     *
     * @return 状态view
     */
    @Override
    public StateViewProxy getStateView() {
        if (mStateView == null) {
            initStateView();
        }
        return mStateView;
    }

    private void initStateView() {
        mStateView = createStateView();
        mStateView.getStateViewImpl().setRootView(mActivity);
    }

    /**
     * 创建状态布局
     *
     * @return 状态view代理
     */
    protected StateViewProxy createStateView() {
        return new StateViewProxy();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }


    @Override
    public void showContent() {
        getStateView().showContent();
    }

    @Override
    public void showDataError() {
        getStateView().showDataError();
    }

    @Override
    public void showDataEmpty() {
        getStateView().showDataEmpty();
    }

    @Override
    public void showNetError() {
        getStateView().showNetError();
    }

    @Override
    public void showLoading() {
        getStateView().showLoading();
    }

}
