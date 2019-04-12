package com.kimiffy.cn.biubiu.ui.articlelist;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPActivity;
import com.kimiffy.cn.biubiu.bean.ArticleBean;
import com.kimiffy.cn.biubiu.ui.articledetail.ArticleDetailActivity;
import com.kimiffy.cn.biubiu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:文章列表
 * 展示列表刷新 加载更多 的用法 详见: https://www.jianshu.com/p/b343fcff51b0
 * Created by kimiffy on 2019/3/13.
 */

public class ArticleListActivity extends BaseMVPActivity<ArticleListPresenter> implements ArticleListContract.View {
    @BindView(R.id.rlv_article)
    RecyclerView rlvArticle;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.root_view)
    ConstraintLayout rootView;
    private List<ArticleBean.DatasBean> articleList;
    private ArticleListAdapter mAdapter;

    @Override
    protected ArticleListPresenter createPresenter() {
        return new ArticleListPresenter(this);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_article_list;
    }

    @Override
    protected void initData() {
        articleList = new ArrayList<>();

    }

    @Override
    protected View getStateViewRootView() {
        return rlvArticle;//状态视图
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        mAdapter = new ArticleListAdapter(R.layout.item_rlv_article, articleList);
        rlvArticle.setLayoutManager(new LinearLayoutManager(this));//布局管理器
        rlvArticle.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));//分割线
        rlvArticle.setAdapter(mAdapter);
        mPresenter.refresh();
    }

    @Override
    protected void setListener() {
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        }, rlvArticle);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean.DatasBean bean = mAdapter.getData().get(position);
                String link = bean.getLink();
                Bundle bundle = new Bundle();
                bundle.putString("link", link);
                startActivity(ArticleDetailActivity.class, bundle);
            }
        });
        mStateView.getStateViewImpl().setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.refresh();
            }
        });

    }


    @Override
    public void getArticleListSuccess(ArticleBean dataBean, boolean isRefresh) {
        if (isRefresh) {
            articleList = dataBean.getDatas();
            mAdapter.replaceData(articleList);
            srlRefresh.setRefreshing(false);
        } else {
//            articleList.addAll(dataBean.getDatas());
            mAdapter.addData(dataBean.getDatas());
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void getArticleListFail(String info) {
        srlRefresh.setRefreshing(false);
        ToastUtil.showToast(info);
    }

}
