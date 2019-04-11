package com.kimiffy.cn.biubiu.utils.imageloader.base;

import android.content.Context;
import android.widget.ImageView;

/**
 * Description: 图片加载器 通用特性功能接口
 * Created by kimiffy on 2019/2/14.
 */

public interface ICommon extends IImageLoad {

    /**
     * 加载网络图片
     * @param context
     * @param imageUrl
     * @param imageView
     */
    void loadImage(Context context,  String imageUrl, ImageView imageView);

}
