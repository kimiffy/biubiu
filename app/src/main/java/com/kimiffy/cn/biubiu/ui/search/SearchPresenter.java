package com.kimiffy.cn.biubiu.ui.search;

import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.HotWordBean;
import com.kimiffy.cn.biubiu.bean.SearchHistoryBean;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.utils.GsonUtil;
import com.kimiffy.cn.biubiu.utils.SpUtil;

import java.util.List;

/**
 * Description:搜索页控制层
 * Created by kimiffy on 2019/5/28.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    SearchPresenter(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void getHotWord() {
        String hotJson = SpUtil.getString(Key.PREF_HOT_WORD_LIST, "");
        List<HotWordBean> hotWordBeans = GsonUtil.toList(hotJson, HotWordBean.class);
        if (!hotWordBeans.isEmpty()) {
            mView.getHotWordSuccess(hotWordBeans);
        }
    }

    @Override
    public void getSearchHistory() {
        String history = SpUtil.getString(Key.PREF_SEARCH_HISTORY_LIST, "");
        List<SearchHistoryBean> historyBeans = GsonUtil.toList(history, SearchHistoryBean.class);
        if (!historyBeans.isEmpty()) {
            mView.getSearchHistorySuccess(historyBeans);
        }

    }

    @Override
    public void saveHistory(List<SearchHistoryBean> historyBeanList) {
        String json = GsonUtil.toJson(historyBeanList);
        SpUtil.putString(Key.PREF_SEARCH_HISTORY_LIST, json);
        mView.saveHistorySuccess(historyBeanList);
    }

    @Override
    public void cleanHistory() {
        SpUtil.putString(Key.PREF_SEARCH_HISTORY_LIST, "");
        mView.cleanHistorySuccess();
    }
}
