package com.kimiffy.cn.biubiu.ui.project.tab;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.bean.ProjectListBean;
import com.kimiffy.cn.biubiu.utils.StringUtil;
import com.kimiffy.cn.biubiu.utils.imageloader.ImageLoader;
import com.kimiffy.cn.biubiu.utils.imageloader.base.ScaleType;
import com.kimiffy.cn.biubiu.utils.imageloader.glide.CommonOption;

import java.util.List;

/**
 * Description:项目列表adapter
 * Created by kimiffy on 2019/5/17.
 */

public class ProjectListAdapter extends BaseQuickAdapter<ProjectListBean.DatasBean, BaseViewHolder> {

    private Activity mActivity;

    ProjectListAdapter(Activity activity, int layoutResId, @Nullable List<ProjectListBean.DatasBean> data) {
        super(layoutResId, data);
        this.mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListBean.DatasBean item) {

        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_date, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            CharSequence format = StringUtil.formatTitle(item.getTitle());
            helper.setText(R.id.tv_title, format);
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }

        if (!TextUtils.isEmpty(item.getDesc())) {
            helper.setText(R.id.tv_des, item.getDesc());
        }

        ImageView imageView = helper.getView(R.id.iv_project);
        if(!TextUtils.isEmpty(item.getEnvelopePic())){
            CommonOption commonOption = CommonOption.builder()
                    .load(item.getEnvelopePic())
                    .scaleType(ScaleType.FIT_CENTER)
                    .into(imageView)
                    .build();
            ImageLoader.getInstance().loadImage(mActivity, commonOption);
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
