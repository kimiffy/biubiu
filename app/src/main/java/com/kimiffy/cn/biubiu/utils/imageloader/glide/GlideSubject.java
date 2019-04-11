package com.kimiffy.cn.biubiu.utils.imageloader.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.kimiffy.cn.biubiu.utils.imageloader.base.ICommon;
import com.kimiffy.cn.biubiu.utils.imageloader.base.ScaleType;


/**
 * Description: glide 图片加载器  真正完成功能的类
 *
 * ICommon :定义通用操作的方法,如果需要切换图片加载框架其他的加载器也可以实现该接口
 * IGlide: 定义特有的操作方法 只能让本加载器实现
 * Created by kimiffy on 2019/2/14.
 */

public class GlideSubject implements ICommon, IGlide {
    private static final String TAG = "GlideSubject";


    @Override
    public void init(Context context) {

    }

    @Override
    public void loadImage(Context context, String imageUrl, ImageView imageView) {
        GlideApp.with(context).load(imageUrl).into(imageView);
    }

    /**
     * 通过通用配置信息加载图片
     * @param context
     * @param option 通用配置信息
     */
    @SuppressLint("CheckResult")
    @Override
    public void loadImage(Context context, CommonOption option) {
        RequestBuilder<Drawable> builder = GlideApp.with(context).load(option.getUrl());
        RequestOptions requestOptions = new RequestOptions();

        // TODO: 2019/2/18 将各项配置设置给glide

        if (option.getPlaceHolder() != 0) {//过渡图
            requestOptions.placeholder(option.getPlaceHolder());
        }

        if (option.getErrorImage() != 0) {//错误图
            requestOptions.error(option.getErrorImage());
        }

        if (option.isCircleImage()) {//圆形图片
            requestOptions.transform(new CircleCrop());
        }

        if (option.getRoundRadius() != 0) {//圆角
            requestOptions.transform(new RoundedCorners(option.getRoundRadius()));
        }

        if (option.isCrossFade()) {//淡入淡出动画
            builder.transition(DrawableTransitionOptions.withCrossFade());
        }

        if (option.getBlurValue() != 0) {//高斯模糊
            requestOptions.transform(new BlurTransformation(option.getBlurValue()));
        }

        if (option.scaleType() != null) {//剪裁类型
            if (option.scaleType() == ScaleType.CENTER_CROP) {
                requestOptions.centerCrop();
            }
            if (option.scaleType() == ScaleType.CENTER_INSIDE) {
                requestOptions.centerInside();
            }
            if (option.scaleType() == ScaleType.FIT_CENTER) {
                requestOptions.fitCenter();
            }
        }
        builder.apply(requestOptions).into(option.getImageView());

    }

}
