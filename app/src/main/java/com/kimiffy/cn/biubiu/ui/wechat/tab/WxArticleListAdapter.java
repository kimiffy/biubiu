package com.kimiffy.cn.biubiu.ui.wechat.tab;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.bean.WxArticleListBean;
import com.kimiffy.cn.biubiu.utils.StringUtil;

import java.util.List;

/**
 * Description:公众号文章列表adapter
 * Created by kimiffy on 2019/5/5.
 */

public class WxArticleListAdapter extends BaseQuickAdapter<WxArticleListBean.DatasBean, BaseViewHolder> {

    private Activity mActivity;

    WxArticleListAdapter(Activity activity, int layoutResId, @Nullable List<WxArticleListBean.DatasBean> data) {
        super(layoutResId, data);
        this.mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, WxArticleListBean.DatasBean item) {
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_date, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            CharSequence format = StringUtil.formatTitle(item.getTitle());
            helper.setText(R.id.tv_title, format);
        }
        if (!TextUtils.isEmpty(item.getChapterName()) && !TextUtils.isEmpty(item.getSuperChapterName())) {

            helper.setText(R.id.tv_chapter, item.getSuperChapterName() + " / " + item.getChapterName());
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
