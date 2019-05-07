package com.kimiffy.cn.biubiu.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.kimiffy.cn.biubiu.base.contract.IBaseFragment;
import com.kimiffy.cn.biubiu.utils.stateview.IStateView;
import com.kimiffy.cn.biubiu.utils.stateview.StateViewProxy;

/**
 * Description: mvp 懒加载 fragment
 * Created by kimiffy on 2019/3/13.
 */

public abstract class LazyMVPFragment<P extends BasePresenter> extends BaseFragment implements IBaseFragment, IStateView {

    /**
     * fragment是否可见
     */
    private boolean isFragmentVisible;


    /**
     * fragment 是否是第一次可见
     */
    private boolean isFirstVisible;


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

    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
    }

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
        if (getUserVisibleHint()) {
            if (isFirstVisible) {
                onLazyLoad();
                isFirstVisible = false;
            }
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onLazyLoad();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }


    /**
     * 设置状态view 根布局
     * 如果需要使用状态布局,必须重写该方法 返回需要展示状态布局的view
     *
     * @return 状态view根布局
     */
    protected View getStateViewRootView() {
        return null;//默认不使用状态视图
    }


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
        View rootView = getStateViewRootView();
        if (null != rootView) {
            mStateView = createStateView();
            mStateView.getStateViewImpl().setRootView(rootView);
        }
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
        if (null != mStateView) {
            mStateView.getStateViewImpl().clean();
            mStateView = null;
        }
        initVariable();
    }


    /**
     * 保证只有当fragment可见状态发生变化时回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     *
     * 可在该回调方法里进行一些ui显示与隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 在fragment首次可见时回调，可用于加载数据，防止每次进入都重复加载数据
     */
    protected abstract void onLazyLoad();


    /**
     * fragment 是否可见
     *
     * @return
     */
    protected boolean isFragmentVisible() {
        return isFragmentVisible;
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
