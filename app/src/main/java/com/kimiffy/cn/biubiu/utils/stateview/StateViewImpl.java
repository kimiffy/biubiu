package com.kimiffy.cn.biubiu.utils.stateview;

import android.view.View;

import com.kimiffy.cn.biubiu.R;

/**
 * Description:状态布局
 * Created by kimiffy on 2019/3/21.
 */

public class StateViewImpl extends BaseStateView implements IStateView {


    @Override
    protected void initData() {
        getStateIDArray().put(STATUS_LOADING, R.layout.layout_stateview_loading);
        getStateIDArray().put(STATUS_LOAD_FAILED, R.layout.layout_stateview_dataerror);
        getStateIDArray().put(STATUS_EMPTY_DATA, R.layout.layout_stateview_dataempty);
        getStateIDArray().put(STATUS_NET_ERROR, R.layout.layout_stateview_neterror);
    }

    /**
     * 设置数据错误布局
     *
     * @param dataErrorViewRes
     */
    @Override
    public void setDataErrorResId(int dataErrorViewRes) {
        setResIdAndCleanStateView(STATUS_LOAD_FAILED, dataErrorViewRes);
    }

    /**
     * 设置空数据布局
     *
     * @param dataEmptyViewRes
     */
    @Override
    public void setDataEmptyResId(int dataEmptyViewRes) {
        setResIdAndCleanStateView(STATUS_EMPTY_DATA, dataEmptyViewRes);
    }

    /**
     * 设置网络错误布局
     *
     * @param netErrorViewRes
     */
    @Override
    public void setNetErrorResId(int netErrorViewRes) {
        setResIdAndCleanStateView(STATUS_NET_ERROR, netErrorViewRes);
    }

    /**
     * 设置正在加载布局
     *
     * @param loadingViewRes
     */
    @Override
    public void setLoadingResId(int loadingViewRes) {
        setResIdAndCleanStateView(STATUS_LOADING, loadingViewRes);
    }

    @Override
    public View getCurrentStateView() {
        return getStateViewArray().get(curState);
    }


    /**
     * 通过状态码获取获取对应状态layout
     *
     * @param stateViewCode
     * @return
     */
    @Override
    public View getStateViewByCode(int stateViewCode) {
        View view = getStateViewArray().get(stateViewCode);
        if (view == null) {
            view = inflateView(stateViewCode);
        }
        return view;
    }


    @Override
    public void showContent() {
        if (STATUS_LOAD_SUCCESS != getCurrentCode()) {
            cleanCurrentStateView();
        }
    }

    @Override
    public void showDataError() {
        if (STATUS_LOAD_FAILED != getCurrentCode()) {
            showLoadingStatus(STATUS_LOAD_FAILED);
        }
    }

    @Override
    public void showDataEmpty() {
        if (STATUS_EMPTY_DATA != getCurrentCode()) {
            showLoadingStatus(STATUS_EMPTY_DATA);
        }
    }

    @Override
    public void showNetError() {
        if (STATUS_NET_ERROR != getCurrentCode()) {
            showLoadingStatus(STATUS_NET_ERROR);
        }
    }

    @Override
    public void showLoading() {
        if (STATUS_LOADING != getCurrentCode()) {
            showLoadingStatus(STATUS_LOADING);
        }
    }

    /**
     * 清除当前状态view
     */
    private void cleanCurrentStateView() {
        if (mRootView == null) {
            return;
        }
        View currentStateView = getCurrentStateView();
        if (currentStateView == null) {
            return;
        }
        mRootView.removeView(currentStateView);
        setCurrentCode(STATUS_LOAD_SUCCESS);
    }


    /**
     * 显示状态
     *
     * @param status
     */
    private void showLoadingStatus(int status) {
        if( mRootView == null){
            return;
        }
        cleanCurrentStateView();
        View view = getStateViewByCode(status);
        if (view == null ) {
            return;
        }
        mRootView.addView(view);
        setCurrentCode(status);
    }


    /**
     * 设置重试监听
     * @param listener
     */
    @Override
    public void setRetryListener(final View.OnClickListener listener) {
        View view = getStateViewByCode(STATUS_LOAD_FAILED);
        view.findViewById(R.id.tv_state_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });

        View error = getStateViewByCode(STATUS_NET_ERROR);
        error.findViewById(R.id.tv_net_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });


    }

    @Override
    public void clean() {
        getStateViewArray().clear();
        getStateIDArray().clear();
    }


}
