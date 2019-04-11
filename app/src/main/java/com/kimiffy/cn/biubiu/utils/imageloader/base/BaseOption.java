package com.kimiffy.cn.biubiu.utils.imageloader.base;

import android.widget.ImageView;

/**
 * Description:图片配置基类
 * 不同图片加载框架 可能有不同的配置信息,需要继承该类增加自己的配置
 * Created by kimiffy on 2019/2/15.
 */

public  class BaseOption {

    protected Object url;//图片url
    protected ImageView imageView;//目标imageView
    protected int placeHolder;//占位图

    public Object getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

}
