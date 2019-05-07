package com.kimiffy.cn.biubiu.ui.articlelist;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.bean.ArticleBean;
import com.kimiffy.cn.biubiu.utils.StringUtil;

import java.util.List;

/**
 * Description:
 * Created by kimiffy on 2019/3/13.
 */

public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder> {

    public ArticleListAdapter(int layoutResId, @Nullable List<ArticleBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean.DatasBean item) {
        if (!TextUtils.isEmpty(item.getTitle())) {
            String format = StringUtil.format(item.getTitle());
            helper.setText(R.id.tv_title, format);
        }
        if (!TextUtils.isEmpty(item.getSuperChapterName())) {
            helper.setText(R.id.tv_parent_type, item.getSuperChapterName() + "/");
        }
        if (!TextUtils.isEmpty(item.getChapterName())) {
            helper.setText(R.id.tv_type, item.getChapterName());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate());
        }
    }
}
