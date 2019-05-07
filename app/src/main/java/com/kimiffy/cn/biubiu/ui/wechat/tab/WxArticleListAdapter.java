package com.kimiffy.cn.biubiu.ui.wechat.tab;

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


    WxArticleListAdapter(int layoutResId, @Nullable List<WxArticleListBean.DatasBean> data) {
        super(layoutResId, data);
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
            String format = StringUtil.format(item.getTitle());
            helper.setText(R.id.tv_title, format);
        }
        if (!TextUtils.isEmpty(item.getChapterName()) && !TextUtils.isEmpty(item.getSuperChapterName())) {
            helper.setText(R.id.tv_chapter, item.getSuperChapterName() + " / " + item.getChapterName());
        }


    }
}
