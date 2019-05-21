package com.kimiffy.cn.biubiu.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPFragment;
import com.kimiffy.cn.biubiu.bean.ArticleBean;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.ui.articledetail.ArticleDetailActivity;
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
 * Description:首页
 * Created by kimiffy on 2019/4/23.
 */
@BindEventBus
public class HomeFragment extends BaseMVPFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.rlv_article)
    RecyclerView mRlvArticle;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private List<ArticleBean.DatasBean> articleList;
    private HomeAdapter mAdapter;


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
    protected View getStateViewRootView() {
        return mRlvArticle;
    }

    @Override
    protected void initUI() {
        mSrlRefresh.setColorSchemeColors(getResources().getColor(R.color.md_blue_A200), getResources().getColor(R.color.md_blue_A400));
        mAdapter = new HomeAdapter(mActivity,R.layout.item_rlv_article, articleList);
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


    }

    /**
     * 第一次进入加载数据
     */
    private void firstFresh() {
        mSrlRefresh.setRefreshing(true);
        mPresenter.firstFresh();
    }


    /**
     * 收藏/取消收藏
     */
    @SingleClick
    @LoginFilter(FilterType.JUMP)
    private void collectClick(ImageView view, ArticleBean.DatasBean item, int position) {
        boolean collect = item.isCollect();
        if (collect) {
            view.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_collect_normal));
            mPresenter.unCollect(item.getId(), position);
        } else {
            view.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_collect));
            view.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.collect));
            mPresenter.doCollect(item.getId(), position);
        }
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


    @Override
    public void getBannerSuccess() {

    }

    @Override
    public void getBannerFail() {

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
        mAdapter.notifyItemChanged(position);
        ToastUtil.showToast(msg);
    }

    @Override
    public void unCollectSuccess(int position) {
        mAdapter.getData().get(position).setCollect(false);
        mAdapter.notifyItemChanged(position);
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
                handleCollectEvent(event,true);
                break;
            case EventCode.CANCEL_COLLECT_ARTICLE_SUCCESS:
                handleCollectEvent(event,false);
                break;
            default:
                break;
        }
    }

    /**
     * 处理收藏/取消收藏事件
     * @param event 事件
     * @param isCollect 是否是收藏
     */
    private void handleCollectEvent(Event event, boolean isCollect) {
        int id = (int) event.getEvent();
        List<ArticleBean.DatasBean> data = mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            ArticleBean.DatasBean datasBean = data.get(i);
            if(datasBean.getId()==id){
                mAdapter.getData().get(i).setCollect(isCollect);
                mAdapter.notifyItemChanged(i);
            }
        }
    }
}
