package com.kimiffy.cn.biubiu.utils.imageloader.base;

import android.content.Context;

import com.kimiffy.cn.biubiu.utils.imageloader.glide.CommonOption;

/**
 * Description:基础接口
 * 如果需要用到两个 图片加载框架 两种不同的配置信息  需要在这里新增一个接口规范
 * 在对应的被代理的图片加载器中实现该方法
 * Created by kimiffy on 2019/2/16.
 */

public interface IImageLoad {

    /**
     * 初始化
     */
    void init(Context context);

    /**
     * 通过通用配置参数加载图片
     *
     * @param context
     * @param option
     */
    void loadImage(Context context, CommonOption option );


//    /**
//     * 通过Glide 的配置参数加载图片
//     * @param context
//     * @param option
//     */
//    void loadImage(Context context, GlideOption option );



}
