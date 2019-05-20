package com.kimiffy.cn.biubiu.ui.project;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPFragment;
import com.kimiffy.cn.biubiu.bean.ProjectTitleBean;
import com.kimiffy.cn.biubiu.ui.project.tab.ProjectTabFragment;
import com.kimiffy.cn.biubiu.utils.StringUtil;
import com.kimiffy.cn.biubiu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:项目
 * Created by kimiffy on 2019/5/17.
 */

public class ProjectMainFragment extends BaseMVPFragment<ProjectMainPresenter> implements ProjectMainContract.View {

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private ProjectMainAdapter mAdapter;

    @Override
    protected ProjectMainPresenter createPresenter() {
        return new ProjectMainPresenter(this);
    }

    public static ProjectMainFragment newInstance() {
        return new ProjectMainFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mAdapter = new ProjectMainAdapter(getChildFragmentManager(), mFragments, mTitles);
    }

    @Override
    protected void initUI() {
        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setCurrentItem(0);
        mPresenter.getProjectTitleList();
    }

    @Override
    public void getTitleListSuccess(List<ProjectTitleBean> data) {
        mFragments.clear();
        mTitles.clear();
        for (ProjectTitleBean titleBean : data) {
            String name = StringUtil.formatTitle(titleBean.getName()).toString();
            mTitles.add(name);
            mFragments.add(ProjectTabFragment.newInstance(titleBean.getId(),name));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getTitleListFail(String info) {
        ToastUtil.showToast(info);
    }
}
