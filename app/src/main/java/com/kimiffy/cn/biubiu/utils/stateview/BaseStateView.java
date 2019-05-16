package com.kimiffy.cn.biubiu.utils.stateview;

import android.app.Activity;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * Description:状态布局 基类
 * Created by kimiffy on 2019/3/21.
 */

public abstract class BaseStateView implements IStateViewInterface {

    static final int STATUS_LOADING = 1;
    static final int STATUS_LOAD_SUCCESS = 2;
    static final int STATUS_LOAD_FAILED = 3;
    static final int STATUS_EMPTY_DATA = 4;
    static final int STATUS_NET_ERROR = 5;
    ViewGroup mRootView;//状态view 的根布局
    int curState;//当前状态
    private SparseIntArray stateIDs;//布局ResId的数组
    private SparseArray<View> stateViews;//状态View 的数组

    BaseStateView() {
        init();
    }

    private void init() {
        curState = STATUS_LOAD_SUCCESS;
        initArray();
        initData();
    }

    private void initArray() {
        if (null == stateIDs) {
            stateIDs = new SparseIntArray(5);
        }
        if (null == stateViews) {
            stateViews = new SparseArray<>(5);
        }
    }

    protected abstract void initData();


    /**
     * 设置activity加载状态View 的根布局
     *
     * @param activity 在activity中使用
     */
    @Override
    public void setRootView(Activity activity) {
        mRootView = activity.findViewById(android.R.id.content);
    }

    /**
     * 设置目标 View 加载状态View 的根布局
     *
     * @param view 在任意view中使用
     */
    @Override
    public void setRootView(View view) {
        FrameLayout wrapper = new FrameLayout(view.getContext());
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null) {
            wrapper.setLayoutParams(lp);
        }
        if (view.getParent() != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            int index = parent.indexOfChild(view);
            parent.removeView(view);
            parent.addView(wrapper, index);
        }
        LayoutParams newLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        wrapper.addView(view, newLp);
        mRootView = wrapper;
    }


    /**
     * 获取当前状态码
     *
     * @return 状态码
     */
    @Override
    public int getCurrentCode() {
        return curState;
    }


    /**
     * 设置当前状态码
     *
     * @param stateViewCode 状态码
     */
    @Override
    public void setCurrentCode(int stateViewCode) {
        curState = stateViewCode;
    }

    /**
     * 获取状态id数组
     *
     * @return 状态id数组
     */
    SparseIntArray getStateIDArray() {
        if (null == stateIDs) {
            initArray();
        }
        return stateIDs;
    }

    /**
     * 获取状态布局数组
     *
     * @return  状态布局数组
     */
    SparseArray<View> getStateViewArray() {
        if (null == stateViews) {
            initArray();
        }
        return stateViews;
    }


    /**
     * 绘制view
     *
     * @param stateViewCode 状态码
     * @return 布局
     */
    View inflateView(int stateViewCode) {

        Integer integer = getStateIDArray().get(stateViewCode);
        View view = LayoutInflater.from(mRootView.getContext()).inflate(integer, mRootView, false);
        if (view == null) {
            return null;
        }
        getStateViewArray().put(stateViewCode, view);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return view;
    }


    /**
     * 删除当前状态码对应的状态布局,并重新设置状态布局
     *
     * @param stateViewCode 状态码
     * @param stateViewRes  状态view对应的布局资源
     */
    void setResIdAndCleanStateView(int stateViewCode, int stateViewRes) {
        getStateIDArray().delete(stateViewCode);
        getStateViewArray().remove(stateViewCode);
        getStateIDArray().put(stateViewCode, stateViewRes);
    }


}
