package com.kimiffy.cn.biubiu.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Description:懒加载Fragment
 * Created by kimiffy on 2019/2/13.
 */

public abstract class LazyFragment extends BaseFragment {

    /**
     * rootView是否初始化标志
     */
    private boolean hasCreateView;

    /**
     * 是否已经加载过数据
     */
    private boolean isDataLoaded;

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
        if (isVisibleToUser) {
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
}
