package com.kimiffy.cn.biubiu.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.kimiffy.cn.biubiu.base.contract.IBaseFragment;
import com.kimiffy.cn.biubiu.utils.stateview.StateViewProxy;

/**
 * Description:
 * Created by kimiffy on 2019/3/12.
 */

public abstract class BaseMVPFragment<P extends BasePresenter> extends BaseFragment implements IBaseFragment {

    /**
     * 具体的presenter由子类确定
     */
    protected P mPresenter;
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

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();

    /**
     * 设置状态view 根布局
     * 如果需要使用状态布局,必须重写该方法 返回需要展示状态布局的view
     *
     * @return
     */
    protected View getStateViewRootView() {
        return null;//默认不使用状态视图
    }

    /**
     * 创建状态布局
     *
     * @return
     */
    @Override
    public StateViewProxy getStateView() {
        if (mStateView == null) {
            initStateView();
        }
        return mStateView;
    }

    private void initStateView() {
        View rootView = getStateViewRootView();
        if (null != rootView) {
            mStateView = createStateView();
            mStateView.getStateViewImpl().setRootView(rootView);
        }
    }

    /**
     * 创建状态布局
     *
     * @return
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


    /**
     * 获取当前绑定的activity
     *
     * @return
     */
    @Override
    public Activity getBindActivity() {
        return mActivity;
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
