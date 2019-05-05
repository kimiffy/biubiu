package com.kimiffy.cn.biubiu.ui.wechat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPFragment;
import com.kimiffy.cn.biubiu.bean.WxTitleBean;
import com.kimiffy.cn.biubiu.ui.wechat.tab.WeChatTabFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:公众号列表
 * Created by kimiffy on 2019/5/3.
 */

public class WeChatMainFragment extends BaseMVPFragment<WeChatMainPresenter> implements WeChatMainContract.View {
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private WeChatMainAdapter mAdapter;

    @Override
    protected WeChatMainPresenter createPresenter() {
        return new WeChatMainPresenter(this);
    }

    public static WeChatMainFragment newInstance() {
        return new WeChatMainFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_we_chat_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mAdapter = new WeChatMainAdapter(getChildFragmentManager(), mFragments,mTitles);
    }

    @Override
    protected void initUI() {
        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setCurrentItem(0);
        mPresenter.getWxList();
    }


    @Override
    protected void setListener() {

    }

    @Override
    public void getWxListSuccess(List<WxTitleBean> bean) {
        mFragments.clear();
        mTitles.clear();
        for (WxTitleBean wxTitleBean : bean) {
            String name = wxTitleBean.getName();
            mTitles.add(name);
            mFragments.add( WeChatTabFragment.newInstance(wxTitleBean.getId()));
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void getWxListFail(String info) {

    }



}
