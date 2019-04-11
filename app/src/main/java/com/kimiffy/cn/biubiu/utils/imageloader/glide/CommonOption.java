package com.kimiffy.cn.biubiu.utils.imageloader.glide;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.widget.ImageView;

import com.kimiffy.cn.biubiu.utils.imageloader.base.BaseOption;
import com.kimiffy.cn.biubiu.utils.imageloader.base.ScaleType;

import java.io.File;

/**
 * Description:通用的配置信息  包含一些主流的图片加载框架都有的配置
 * Created by kimiffy on 2019/2/16.
 */

public class CommonOption extends BaseOption {

    // TODO: 2019/2/18 这里可以添加更多的通用配置信息
    private int roundRadius;//图片每个圆角的大小
    private int blurValue;//高斯模糊值, 值越大模糊效果越大
    private int errorImage;//错误图
    private boolean isCrossFade;//是否使用淡入淡出过渡动画  最好不要在ListView,GridView,RecycleView中使用过渡动画
    private boolean isCircle;//是否加载为圆形图片
    private ScaleType scaleType;//剪裁类型 CenterInside  CenterCrop  fitCenter

    public int getRoundRadius() {
        return roundRadius;
    }

    public int getBlurValue() {
        return blurValue;
    }

    public int getErrorImage() {
        return errorImage;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public ScaleType scaleType() {
        return scaleType;
    }

    public boolean isCircleImage() {
        return isCircle;
    }


    //通过建造者模式来构建配置信息
    private CommonOption(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeHolder = builder.placeHolder;
        this.roundRadius = builder.roundRadius;
        this.blurValue = builder.blurValue;
        this.errorImage = builder.errorImage;
        this.scaleType = builder.scaleType;
        this.isCircle = builder.isCircle;
        this.isCrossFade = builder.isCrossFade;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 构造器
     */
    public static final class Builder {
        // TODO: 2019/2/18 这里可以添加更多的通用配置信息
        private Object url;
        private ImageView imageView;
        private int roundRadius;//图片每个圆角的大小
        private int blurValue;//高斯模糊值, 值越大模糊效果越大
        private int placeHolder;//过渡图
        private int errorImage;//错误图
        private boolean isCrossFade;//是否使用淡入淡出过渡动画
        private ScaleType scaleType;//剪裁类型 CenterInside  CenterCrop  fitCenter
        private boolean isCircle;//是否将图片剪切为圆形

        private Builder() {
        }

        public CommonOption build() {
            return new CommonOption(this);
        }

        public Builder load(@Nullable Object object) {
            this.url = object;
            return this;
        }

        public Builder load(@Nullable String url) {
            this.url = url;
            return this;
        }

        public Builder load(@Nullable File url) {
            this.url = url;
            return this;
        }

        public Builder load(@Nullable Uri uri) {
            this.url = uri;
            return this;
        }

        public Builder load(@Nullable @RawRes @DrawableRes Integer url) {
            this.url = url;
            return this;
        }

        public Builder placeholder(@DrawableRes int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder into(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder roundRadius(int radius) {
            this.roundRadius = radius;
            return this;
        }

        public Builder errorImage(@DrawableRes int errorImage) {
            this.errorImage = errorImage;
            return this;
        }


        public Builder isCrossFade(boolean isCrossFade) {
            this.isCrossFade = isCrossFade;
            return this;
        }


        public Builder scaleType(ScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }

        public Builder isCircleImage(boolean isCircle) {
            this.isCircle = isCircle;
            return this;
        }


        public Builder blurValue(int blurValue) {
            this.blurValue = blurValue;
            return this;
        }


    }
}
