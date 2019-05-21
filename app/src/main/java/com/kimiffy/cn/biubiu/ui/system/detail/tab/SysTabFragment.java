package com.kimiffy.cn.biubiu.ui.system.detail.tab;

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
import com.kimiffy.cn.biubiu.base.LazyMVPFragment;
import com.kimiffy.cn.biubiu.bean.SystemDetailBean;
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
 * Description:知识体系文章列表tab
 * Created by kimiffy on 2019/5/20.
 */
@BindEventBus
public class SysTabFragment extends LazyMVPFragment<SysTabPresenter> implements SysTabContract.View {


    @BindView(R.id.rlv_article)
    RecyclerView mRlvArticle;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private int id;
    private List<SystemDetailBean.DatasBean> mList;
    private SysArticleListAdapter mAdapter;

    public static SysTabFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(Key.ARGUMENT_ID, id);
        SysTabFragment fragment = new SysTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected SysTabPresenter createPresenter() {
        return new SysTabPresenter(this);
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
        mList = new ArrayList<>();
    }

    @Override
    protected void initUI() {
        mSrlRefresh.setColorSchemeColors(getResources().getColor(R.color.md_blue_A200), getResources().getColor(R.color.md_blue_A400));
        mAdapter = new SysArticleListAdapter(mActivity, R.layout.item_rlv_wx_article, mList);
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

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SystemDetailBean.DatasBean item = (SystemDetailBean.DatasBean) baseQuickAdapter.getData().get(i);
                switch (view.getId()) {
                    case R.id.iv_collect:
                        collectClick((ImageView) view, item, i);
                        break;
                    default:
                        break;
                }
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SystemDetailBean.DatasBean item = (SystemDetailBean.DatasBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putString(Key.BUNDLE_LINK, item.getLink());
                bundle.putString(Key.BUNDLE_TOOLBAR_TITLE, item.getAuthor());
                bundle.putString(Key.BUNDLE_TITLE, item.getTitle());
                bundle.putBoolean(Key.BUNDLE_COLLECT, item.isCollect());
                bundle.putInt(Key.BUNDLE_ID, item.getId());
                startActivity(ArticleDetailActivity.class, bundle);
            }
        });


        mStateView.getStateViewImpl().setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/5/10 重试
            }
        });
    }

    /**
     * 收藏/取消收藏
     */
    @SingleClick
    @LoginFilter(FilterType.JUMP)
    private void collectClick(ImageView view, SystemDetailBean.DatasBean item, int position) {
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
    protected void onVisibleStateChange(boolean isVisible) {
        if (!isVisible) {
            mSrlRefresh.setRefreshing(false);
        }
    }

    @Override
    protected void onLazyLoad() {
        mSrlRefresh.setRefreshing(true);
        mPresenter.firstFresh(id);
    }


    @Override
    protected View getStateViewRootView() {
        return mRlvArticle;
    }

    @Override
    public void getDataSuccess(List<SystemDetailBean.DatasBean> bean, boolean isRefresh) {
        if (isRefresh) {
            mList = bean;
            mAdapter.replaceData(mList);
        } else {
            mAdapter.addData(bean);
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void getDataFail(String info) {

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
        List<SystemDetailBean.DatasBean> data = mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            SystemDetailBean.DatasBean datasBean = data.get(i);
            if (datasBean.getId() == id) {
                mAdapter.getData().get(i).setCollect(isCollect);
                mAdapter.notifyItemChanged(i);
            }
        }
    }
}
