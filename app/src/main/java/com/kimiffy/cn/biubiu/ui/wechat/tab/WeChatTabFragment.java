package com.kimiffy.cn.biubiu.ui.wechat.tab;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.LazyMVPFragment;
import com.kimiffy.cn.biubiu.bean.WxArticleListBean;
import com.kimiffy.cn.biubiu.constant.Key;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:公众号文章列表
 * Created by kimiffy on 2019/5/3.
 */

public class WeChatTabFragment extends LazyMVPFragment<WeChatTabPresenter> implements WeChatTabContract.View {


    @BindView(R.id.rlv_article)
    RecyclerView mRlvArticle;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private int id;
    private List<WxArticleListBean.DatasBean> mList;
    private WxArticleListAdapter mAdapter;

    public static WeChatTabFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(Key.ARGUMENT_ID, id);
        WeChatTabFragment fragment = new WeChatTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected WeChatTabPresenter createPresenter() {
        return new WeChatTabPresenter(this);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_we_chat_tab;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null != arguments) {
            id = arguments.getInt(Key.ARGUMENT_ID, -1);
        }
        mList =new ArrayList<>();
    }

    @Override
    protected void initUI() {
        mSrlRefresh.setColorSchemeColors(getResources().getColor(R.color.md_blue_A200),getResources().getColor(R.color.md_blue_A400));
        mAdapter = new WxArticleListAdapter(R.layout.item_rlv_wx_article, mList);
        mRlvArticle.setLayoutManager(new LinearLayoutManager(getBindActivity()));
        mRlvArticle.addItemDecoration(new DividerItemDecoration(getBindActivity(), LinearLayoutManager.VERTICAL));
        mRlvArticle.setAdapter(mAdapter);
    }


    @Override
    protected void setListener() {
        mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.reFresh();
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMore();
            }
        }, mRlvArticle);

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onLazyLoad() {
        mPresenter.getData(id,1);
    }


    @Override
    protected View getStateViewRootView() {
        return mRlvArticle;
    }

    @Override
    public void getDataSuccess(WxArticleListBean bean,boolean isRefresh) {
        List<WxArticleListBean.DatasBean> datas = bean.getDatas();
        if (isRefresh) {
            mList = datas;
            mAdapter.replaceData(mList);
            mSrlRefresh.setRefreshing(false);
        } else {
            if(!datas.isEmpty()){
                mAdapter.addData(datas);
                mAdapter.loadMoreComplete();
            }else{
                mAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    public void getDataFail(String info) {
        mSrlRefresh.setRefreshing(false);
    }

}
