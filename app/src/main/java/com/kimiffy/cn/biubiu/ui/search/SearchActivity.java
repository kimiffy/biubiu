package com.kimiffy.cn.biubiu.ui.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.base.BaseMVPActivity;
import com.kimiffy.cn.biubiu.bean.HotWordBean;
import com.kimiffy.cn.biubiu.bean.SearchHistoryBean;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.ui.search.result.SearchResultActivity;
import com.kimiffy.cn.biubiu.utils.aop.annotation.SingleClick;
import com.kimiffy.cn.biubiu.widget.FlowLayout;
import com.kimiffy.cn.biubiu.widget.TagAdapter;
import com.kimiffy.cn.biubiu.widget.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:搜索
 * Created by kimiffy on 2019/5/28.
 */

public class SearchActivity extends BaseMVPActivity<SearchPresenter> implements SearchContract.View {
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_clean)
    TextView mTvClean;
    @BindView(R.id.rv_search_history)
    RecyclerView mRvSearchHistory;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;
    private SearchView.SearchAutoComplete mSearchAutoComplete;
    private List<HotWordBean> mHotWordBeans;
    private List<SearchHistoryBean> mHistoryBeanList;
    private SearchAdapter mAdapter;

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHistoryBeanList = new ArrayList<>();
    }

    @Override
    protected void initUI() {
        initSearchView();
        mAdapter = new SearchAdapter(R.layout.item_rv_search_history, mHistoryBeanList);
        mRvSearchHistory.setLayoutManager(new LinearLayoutManager(this));
//        mRvSearchHistory.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter.setEmptyView(R.layout.layout_search_empty, mRvSearchHistory);
        mRvSearchHistory.setAdapter(mAdapter);
        mPresenter.getHotWord();
        mPresenter.getSearchHistory();
    }


    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchAutoComplete.hasFocus()) {//todo 这里最好是判断键盘有没有打开
                    mSearchView.clearFocus();
                } else {
                    finish();
                }
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String name = mHotWordBeans.get(position).getName();
                SearchHistoryBean searchHistoryBean = new SearchHistoryBean();
                searchHistoryBean.setKeyWord(name);
                if (!mHistoryBeanList.contains(searchHistoryBean)) {
                    mHistoryBeanList.add(0, searchHistoryBean);
                    mPresenter.saveHistory(mHistoryBeanList);
                }
                goSearchDetail(name);
                return true;
            }
        });

        mTvClean.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                mPresenter.cleanHistory();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    return false;
                }
                SearchHistoryBean searchHistoryBean = new SearchHistoryBean();
                searchHistoryBean.setKeyWord(query);
                if (!mHistoryBeanList.contains(searchHistoryBean)) {
                    mHistoryBeanList.add(0, searchHistoryBean);
                    mPresenter.saveHistory(mHistoryBeanList);
                }
                goSearchDetail(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String keyWord = mAdapter.getData().get(position).getKeyWord();
                goSearchDetail(keyWord);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        mHistoryBeanList.remove(position);
                        mPresenter.saveHistory(mHistoryBeanList);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    /**
     * 跳转搜索详情页
     *
     * @param keyWord 搜索关键词
     */
    private void goSearchDetail(String keyWord) {
        Bundle bundle = new Bundle();
        bundle.putString(Key.BUNDLE_SEARCH_KEY_WORD, keyWord);
        startActivity(SearchResultActivity.class, bundle);
    }


    private void initSearchView() {
        //去掉searchView 下划线
        mSearchView.findViewById(R.id.search_plate).setBackground(null);
        mSearchView.findViewById(R.id.submit_area).setBackground(null);
        mSearchView.onActionViewExpanded();
        mSearchView.setQueryHint(getString(R.string.search_hint));
        mSearchAutoComplete = mSearchView.findViewById(R.id.search_src_text);
        mSearchAutoComplete.setHintTextColor(getResources().getColor(R.color.md_grey_400));
        mSearchAutoComplete.setTextColor(getResources().getColor(R.color.md_grey_600));
        mSearchAutoComplete.setTextSize(16);
        mSearchView.clearFocus();
    }


    @Override
    public void getHotWordSuccess(List<HotWordBean> hotWordBeans) {
        mHotWordBeans = hotWordBeans;
        TagAdapter<HotWordBean> tagAdapter = new TagAdapter<HotWordBean>(hotWordBeans) {
            @Override
            public View getView(FlowLayout parent, int position, HotWordBean searchHot) {
                TextView item = (TextView) getLayoutInflater().inflate(R.layout.item_hot_wrod, null);
                item.setText(searchHot.getName());
                return item;
            }
        };
        mFlowLayout.setAdapter(tagAdapter);
    }

    @Override
    public void getSearchHistorySuccess(List<SearchHistoryBean> historyBeans) {
        mHistoryBeanList = historyBeans;
        mAdapter.replaceData(mHistoryBeanList);
    }

    @Override
    public void cleanHistorySuccess() {
        mHistoryBeanList.clear();
        mAdapter.replaceData(mHistoryBeanList);
    }

    @Override
    public void saveHistorySuccess(List<SearchHistoryBean> historyBeans) {
        mHistoryBeanList = historyBeans;
        mAdapter.replaceData(mHistoryBeanList);
    }
}
