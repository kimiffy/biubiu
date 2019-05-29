package com.kimiffy.cn.biubiu.ui.search;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.bean.SearchHistoryBean;

import java.util.List;

/**
 * Description:搜索历史adapter
 * Created by kimiffy on 2019/5/29.
 */

public class SearchAdapter extends BaseQuickAdapter<SearchHistoryBean, BaseViewHolder> {

    SearchAdapter(int layoutResId, @Nullable List<SearchHistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistoryBean item) {
        if(!item.getKeyWord().isEmpty()){
            helper.setText(R.id.tv_search_history_word, item.getKeyWord());
        }
    }
}
