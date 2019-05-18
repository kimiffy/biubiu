package com.kimiffy.cn.biubiu.ui.home;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

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

public class HomeAdapter extends BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder> {

    public HomeAdapter(int layoutResId, @Nullable List<ArticleBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean.DatasBean item) {

        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }

        if (!TextUtils.isEmpty(item.getTitle())) {
            CharSequence format = StringUtil.formatTitle(item.getTitle());
            helper.setText(R.id.tv_title, format);
        }
        if (!TextUtils.isEmpty(item.getChapterName()) && !TextUtils.isEmpty(item.getSuperChapterName())) {
            helper.setText(R.id.tv_type, item.getSuperChapterName() + "/" + item.getChapterName());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_date, item.getNiceDate());
        }

        if (item.isFresh()) {
            helper.getView(R.id.iv_fresh).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_fresh).setVisibility(View.INVISIBLE);
        }
    }
}
