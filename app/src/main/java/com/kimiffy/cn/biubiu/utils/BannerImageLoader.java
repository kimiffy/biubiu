package com.kimiffy.cn.biubiu.utils;

import android.content.Context;
import android.widget.ImageView;

import com.kimiffy.cn.biubiu.utils.imageloader.base.ScaleType;
import com.kimiffy.cn.biubiu.utils.imageloader.glide.CommonOption;
import com.youth.banner.loader.ImageLoader;

/**
 * Description:广告图片加载器
 * Created by kimiffy on 2019/5/22.
 */

public class BannerImageLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        CommonOption commonOption = CommonOption.builder()
                .load(path)
                .scaleType(ScaleType.FIT_CENTER)
                .into(imageView)
                .build();
        com.kimiffy.cn.biubiu.utils.imageloader.ImageLoader.getInstance().loadImage(context.getApplicationContext(), commonOption);
    }
}
