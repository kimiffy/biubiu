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
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
}
