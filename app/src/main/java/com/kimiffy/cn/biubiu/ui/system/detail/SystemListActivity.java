package com.kimiffy.cn.biubiu.ui.system.detail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseActivity;
import com.kimiffy.cn.biubiu.bean.SystemBean;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.ui.system.detail.tab.SysTabFragment;
import com.kimiffy.cn.biubiu.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:体系分类列表activity
 * Created by kimiffy on 2019/5/20.
 */

public class SystemListActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private SystemListAdapter mAdapter;
    private String mToolBarTitle;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sys_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mToolBarTitle = getIntent().getStringExtra(Key.BUNDLE_TOOLBAR_TITLE);
        String json = getIntent().getStringExtra(Key.BUNDLE_SYS_CHILD);
        mAdapter = new SystemListAdapter(getSupportFragmentManager(), mFragments, mTitles);
        List<SystemBean.ChildrenBean> childrenBeans = GsonUtil.toList(json, SystemBean.ChildrenBean.class);
        getData(childrenBeans);
    }

    @Override
    protected void initUI() {
        initToolBar();
        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setCurrentItem(0);

    }


    private void initToolBar() {
        mToolbar.setTitle(mToolBarTitle);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getData(List<SystemBean.ChildrenBean> childrenBeans) {
        mTitles.clear();
        mFragments.clear();
        for (SystemBean.ChildrenBean childrenBean : childrenBeans) {
            mTitles.add(childrenBean.getName());
            mFragments.add(SysTabFragment.newInstance(childrenBean.getId()));
        }
        mAdapter.notifyDataSetChanged();

    }
}
