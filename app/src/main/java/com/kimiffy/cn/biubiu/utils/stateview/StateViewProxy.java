package com.kimiffy.cn.biubiu.utils.stateview;

/**
 * Description:状态布局代理类
 * Created by kimiffy on 2019/3/21.
 */

public class StateViewProxy implements IStateView {

    private IStateViewInterface mStateViewImpl;


    public StateViewProxy() {
        if (null == mStateViewImpl) {
            mStateViewImpl = new StateViewImpl();
        }
    }

    /**
     * 返回被代理状态布局实例,可以调用状态布局提供的方法,修改自己的状态布局
     *
     * @return
     */
    public IStateViewInterface getStateViewImpl() {
        return mStateViewImpl;
    }

    @Override
    public void showContent() {
        mStateViewImpl.showContent();
    }

    @Override
    public void showDataError() {
        mStateViewImpl.showDataError();
    }

    @Override
    public void showDataEmpty() {
        mStateViewImpl.showDataEmpty();
    }

    @Override
    public void showNetError() {
        mStateViewImpl.showNetError();
    }

    @Override
    public void showLoading() {
        mStateViewImpl.showLoading();
    }




}
