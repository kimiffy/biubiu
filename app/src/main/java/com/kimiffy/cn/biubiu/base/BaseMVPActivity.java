package com.kimiffy.cn.biubiu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kimiffy.cn.biubiu.base.contract.IBaseActivity;
import com.kimiffy.cn.biubiu.utils.stateview.IStateView;
import com.kimiffy.cn.biubiu.utils.stateview.StateViewProxy;

/**
 * Description: mvp Activity 基类
 * Created by kimiffy on 2019/3/11.
 */

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity implements IBaseActivity, IStateView {

    /**
     * 具体的presenter由子类确定
     */
    protected P mPresenter;
    /**
     * 状态布局
     */
    protected StateViewProxy mStateView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (null != mPresenter) {
            mPresenter.attach(this);
        }
        super.onCreate(savedInstanceState);
    }

    /**
     * 创建Presenter
     *
     * @return
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
     * 获取状态布局
     *
     * @return
     */
    @Override
    public StateViewProxy getStateView() {
        if (null == mStateView) {
            initStateView();
        }
        return mStateView;
    }

    @Override
    protected void initStateView() {
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
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detach();
        }
        if(null!=mStateView){
            mStateView.getStateViewImpl().clean();
            mStateView = null;
        }

    }

    @Override
    public void showContent() {
        StateViewProxy stateView = getStateView();
        if (null != stateView) {
            stateView.showContent();
        }
    }

    @Override
    public void showDataError() {
        StateViewProxy stateView = getStateView();
        if (null != stateView) {
            stateView.showDataError();
        }

    }

    @Override
    public void showDataEmpty() {
        StateViewProxy stateView = getStateView();
        if (null != stateView) {
            stateView.showDataEmpty();
        }

    }

    @Override
    public void showNetError() {
        StateViewProxy stateView = getStateView();
        if (null != stateView) {
            stateView.showNetError();
        }
    }

    @Override
    public void showLoading() {
        StateViewProxy stateView = getStateView();
        if (null != stateView) {
            stateView.showLoading();
        }
    }


}
