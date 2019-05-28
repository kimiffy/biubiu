package com.kimiffy.cn.biubiu.ui.system;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPFragment;
import com.kimiffy.cn.biubiu.bean.SystemBean;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.ui.system.detail.SystemListActivity;
import com.kimiffy.cn.biubiu.utils.GsonUtil;
import com.kimiffy.cn.biubiu.utils.aop.annotation.SingleClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:知识体系
 * Created by kimiffy on 2019/5/20.
 */

public class SystemMainFragment extends BaseMVPFragment<SystemMainPresenter> implements SystemMainContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_sys)
    RecyclerView mRlvSys;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private List<SystemBean> mList;
    private SystemMainAdapter mAdapter;

    public static SystemMainFragment newInstance() {
        return new SystemMainFragment();
    }

    @Override
    protected SystemMainPresenter createPresenter() {
        return new SystemMainPresenter(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sys;
    }


    @Override
    protected View getStateViewRootView() {
        return mRlvSys;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList = new ArrayList<>();
    }

    @Override
    protected void initUI() {
        mSrlRefresh.setColorSchemeColors(getResources().getColor(R.color.md_blue_A200), getResources().getColor(R.color.md_blue_A400));
        mAdapter = new SystemMainAdapter(R.layout.item_rv_sys, mList);
        mRlvSys.setLayoutManager(new LinearLayoutManager(getBindActivity()));
        mRlvSys.setAdapter(mAdapter);
        getData();
    }


    @Override
    protected void setListener() {
        super.setListener();
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SystemBean data = (SystemBean) adapter.getData().get(position);
                List<SystemBean.ChildrenBean> children = data.getChildren();
                String json = GsonUtil.toJson(children);
                Bundle bundle = new Bundle();
                bundle.putString(Key.BUNDLE_TOOLBAR_TITLE,data.getName());
                bundle.putString(Key.BUNDLE_SYS_CHILD,json);
                startActivity(SystemListActivity.class,bundle);
            }
        });

        mStateView.getStateViewImpl().setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }


    private void getData() {
        mSrlRefresh.setRefreshing(true);
        mPresenter.firstFresh();
    }


    @Override
    public void getDataSuccess(List<SystemBean> dataBean) {
        mList = dataBean;
        mAdapter.replaceData(mList);
        mSrlRefresh.setRefreshing(false);
    }

    @Override
    public void getDataFail(String info) {
        mSrlRefresh.setRefreshing(false);
    }
}
