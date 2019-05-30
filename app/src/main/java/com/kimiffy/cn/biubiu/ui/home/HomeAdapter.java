package com.kimiffy.cn.biubiu.ui.home;

import android.app.Activity;
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
    private Activity mActivity;

    public HomeAdapter(Activity activity, int layoutResId, @Nullable List<ArticleBean.DatasBean> data) {
        super(layoutResId, data);
        this.mActivity = activity;
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
            CharSequence format = StringUtil.formatTitle( item.getChapterName());
            helper.setText(R.id.tv_type, item.getSuperChapterName() + "/" + format);
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_date, item.getNiceDate());
        }

        if (item.isFresh()) {
            helper.getView(R.id.iv_fresh).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_fresh).setVisibility(View.INVISIBLE);
        }


        boolean collect = item.isCollect();
        if (collect) {
            helper.setImageDrawable(R.id.iv_collect, mActivity.getResources().getDrawable(R.drawable.ic_collect));
        } else {
            helper.setImageDrawable(R.id.iv_collect, mActivity.getResources().getDrawable(R.drawable.ic_collect_normal));
        }
        helper.addOnClickListener(R.id.iv_collect);

    }
}
