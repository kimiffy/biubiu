package com.kimiffy.cn.biubiu.ui.search.result;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPActivity;
import com.kimiffy.cn.biubiu.bean.ArticleBean;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.ui.articledetail.ArticleDetailActivity;
import com.kimiffy.cn.biubiu.ui.home.HomeAdapter;
import com.kimiffy.cn.biubiu.utils.ToastUtil;
import com.kimiffy.cn.biubiu.utils.aop.FilterType;
import com.kimiffy.cn.biubiu.utils.aop.annotation.LoginFilter;
import com.kimiffy.cn.biubiu.utils.aop.annotation.SingleClick;
import com.kimiffy.cn.biubiu.utils.event.BindEventBus;
import com.kimiffy.cn.biubiu.utils.event.Event;
import com.kimiffy.cn.biubiu.utils.event.EventCode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:搜索结果列表
 * Created by kimiffy on 2019/5/29.
 */
@BindEventBus
public class SearchResultActivity extends BaseMVPActivity<SearchResultPresenter> implements SearchResultContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_article)
    RecyclerView mRvArticle;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private String mKeyWord;
    private List<ArticleBean.DatasBean> articleList;
    private HomeAdapter mAdapter;

    @Override
    protected SearchResultPresenter createPresenter() {
        return new SearchResultPresenter(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected View getStateViewRootView() {
        return mRvArticle;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mKeyWord = getIntent().getStringExtra(Key.BUNDLE_SEARCH_KEY_WORD);
        articleList = new ArrayList<>();
    }

    @Override
    protected void initUI() {
        mToolbar.setTitle(mKeyWord);
        mSrlRefresh.setColorSchemeColors(getResources().getColor(R.color.md_blue_A200), getResources().getColor(R.color.md_blue_A400));
        mAdapter = new HomeAdapter(this, R.layout.item_rv_article, articleList);
        mRvArticle.setLayoutManager(new LinearLayoutManager(this));
        ((DefaultItemAnimator) mRvArticle.getItemAnimator()).setSupportsChangeAnimations(false);
        mRvArticle.setAdapter(mAdapter);
        mSrlRefresh.setRefreshing(true);
        mPresenter.firstFresh(mKeyWord);
    }


    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        }, mRvArticle);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean.DatasBean bean = mAdapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putString(Key.BUNDLE_LINK, bean.getLink());
                bundle.putString(Key.BUNDLE_TOOLBAR_TITLE, bean.getAuthor());
                bundle.putString(Key.BUNDLE_TITLE, bean.getTitle());
                bundle.putBoolean(Key.BUNDLE_COLLECT, bean.isCollect());
                bundle.putInt(Key.BUNDLE_ID, bean.getId());
                startActivity(ArticleDetailActivity.class, bundle);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ArticleBean.DatasBean item = (ArticleBean.DatasBean) baseQuickAdapter.getData().get(i);
                switch (view.getId()) {
                    case R.id.iv_collect:
                        collectClick((ImageView) view, item, i);
                        break;
                    default:
                        break;
                }
            }
        });

        mStateView.getStateViewImpl().setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSrlRefresh.setRefreshing(true);
                mPresenter.firstFresh(mKeyWord);
            }
        });


    }

    @Override
    public void getArticleListSuccess(List<ArticleBean.DatasBean> dataBean, boolean isRefresh) {
        if (isRefresh) {
            articleList = dataBean;
            mAdapter.replaceData(articleList);
        } else {
            mAdapter.addData(dataBean);
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void getArticleListFail(String info) {
        ToastUtil.showToast(info);
    }

    @Override
    public void stopRefresh() {
        mSrlRefresh.setRefreshing(false);
    }

    @Override
    public void noMoreData() {
        mAdapter.loadMoreEnd();
    }


    /**
     * 收藏/取消收藏
     */
    @SingleClick
    @LoginFilter(FilterType.JUMP)
    private void collectClick(ImageView view, ArticleBean.DatasBean item, int position) {
        boolean collect = item.isCollect();
        if (collect) {
            view.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect_normal));
            mPresenter.unCollect(item.getId(), position);
        } else {
            view.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect));
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.collect));
            mPresenter.doCollect(item.getId(), position);
        }
    }

    @Override
    public void collectSuccess(int position) {
        mAdapter.getData().get(position).setCollect(true);
        mAdapter.notifyItemChanged(position);
        ToastUtil.showToast(getString(R.string.collect_success));
    }

    @Override
    public void collectFail(int position, String msg) {
        mAdapter.getData().get(position).setCollect(false);
        mAdapter.notifyItemChanged(position );
        ToastUtil.showToast(msg);
    }

    @Override
    public void unCollectSuccess(int position) {
        mAdapter.getData().get(position).setCollect(false);
        mAdapter.notifyItemChanged(position );
        ToastUtil.showToast(getString(R.string.cancel_collect_success));
    }

    @Override
    public void unCollectFail(int position, String msg) {
        mAdapter.getData().get(position).setCollect(true);
        mAdapter.notifyItemChanged(position);
        ToastUtil.showToast(msg);
    }


    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case EventCode.COLLECT_ARTICLE_SUCCESS:
                handleCollectEvent(event, true);
                break;
            case EventCode.CANCEL_COLLECT_ARTICLE_SUCCESS:
                handleCollectEvent(event, false);
                break;
            default:
                break;
        }
    }

    /**
     * 处理收藏/取消收藏事件
     *
     * @param event     事件
     * @param isCollect 是否是收藏
     */
    private void handleCollectEvent(Event event, boolean isCollect) {
        int id = (int) event.getEvent();
        List<ArticleBean.DatasBean> data = mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            ArticleBean.DatasBean datasBean = data.get(i);
            if (datasBean.getId() == id) {
                mAdapter.getData().get(i).setCollect(isCollect);
                int headerCount = mAdapter.getHeaderLayoutCount();
                mAdapter.notifyItemChanged(i + headerCount);
            }
        }
    }
}
