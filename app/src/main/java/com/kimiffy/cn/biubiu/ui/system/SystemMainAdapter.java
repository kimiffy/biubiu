package com.kimiffy.cn.biubiu.ui.system;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kimiffy.cn.biubiu.R;
import com.kimiffy.cn.biubiu.bean.SystemBean;
import com.kimiffy.cn.biubiu.utils.StringUtil;

import java.util.List;

/**
 * Description:知识体系adapter
 * Created by kimiffy on 2019/5/20.
 */

public class SystemMainAdapter extends BaseQuickAdapter<SystemBean, BaseViewHolder> {


    SystemMainAdapter(int layoutResId, @Nullable List<SystemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemBean item) {

        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_title, item.getName());
        }

        if (!item.getChildren().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (SystemBean.ChildrenBean childrenBean : item.getChildren()) {
                String name = StringUtil.formatTitle(childrenBean.getName()).toString();
                sb.append(name).append("    ");
            }
            helper.setText(R.id.tv_content, sb.toString());
        }
    }
}
