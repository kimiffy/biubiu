package com.kimiffy.cn.biubiu.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.kimiffy.cn.biubiu.base.contract.IBaseFragment;
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
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
        super.onViewCreated(view, savedInstanceState);
        hasCreateView = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        if (isVisibleToUser) {
            preLazyLoad();
            isFragmentVisible = true;
        } else {
            isFragmentVisible = false;
        }

    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }


    private void preLazyLoad() {
        if (hasCreateView && isFragmentVisible) {
            lazyLoadData();
            //数据加载完毕,恢复标记,防止重复加载
            hasCreateView = false;
            isFragmentVisible = false;
        }
    }

    /**
     * 懒加载数据
     */
    protected abstract void lazyLoadData();

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
