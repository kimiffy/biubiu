package com.kimiffy.cn.biubiu.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPFragment;
import com.kimiffy.cn.biubiu.bean.ArticleBean;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.ui.articledetail.ArticleDetailActivity;
import com.kimiffy.cn.biubiu.ui.articlelist.ArticleListAdapter;
import com.kimiffy.cn.biubiu.utils.ToastUtil;
import com.kimiffy.cn.biubiu.utils.aop.annotation.SingleClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:首页
 * Created by kimiffy on 2019/4/23.
 */

public class HomeFragment extends BaseMVPFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.rlv_article)
    RecyclerView mRlvArticle;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private List<ArticleBean.DatasBean> articleList;
    private ArticleListAdapter mAdapter;


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        articleList = new ArrayList<>();
    }

    @Override
    protected void initUI() {
        mSrlRefresh.setColorSchemeColors(getResources().getColor(R.color.md_blue_A200),getResources().getColor(R.color.md_blue_A400));
        mAdapter = new ArticleListAdapter(R.layout.item_rlv_article, articleList);
        mRlvArticle.setLayoutManager(new LinearLayoutManager(getBindActivity()));
        mRlvArticle.addItemDecoration(new DividerItemDecoration(getBindActivity(), LinearLayoutManager.VERTICAL));
        mRlvArticle.setAdapter(mAdapter);
        firstFresh();
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

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMore();
            }
        }, mRlvArticle);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean.DatasBean bean = mAdapter.getData().get(position);
                String link = bean.getLink();
                Bundle bundle = new Bundle();
                bundle.putString(Key.BUNDLE_LINK, link);
                startActivity(ArticleDetailActivity.class, bundle);
            }
        });

    }

    /**
     * 第一次进入显示刷新
     */
    private void firstFresh() {
        mSrlRefresh.setRefreshing(true);
        mPresenter.firstFresh();
    }


    @Override
    public void getArticleListSuccess(ArticleBean dataBean, boolean isRefresh) {
        if (isRefresh) {
            articleList = dataBean.getDatas();
            mAdapter.replaceData(articleList);
            mSrlRefresh.setRefreshing(false);
        } else {
            mAdapter.addData(dataBean.getDatas());
            mAdapter.loadMoreComplete();
        }
    }


    @Override
    public void getArticleListFail(String info) {
        mSrlRefresh.setRefreshing(false);
        ToastUtil.showToast(info);
    }


    @Override
    public void getBannerSuccess() {

    }

    @Override
    public void getBannerFail() {

    }

}
