package com.kimiffy.cn.biubiu.utils.imageloader;


import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.kimiffy.cn.biubiu.utils.imageloader.base.BaseOption;
import com.kimiffy.cn.biubiu.utils.imageloader.base.ICommon;
import com.kimiffy.cn.biubiu.utils.imageloader.base.IImageLoad;
import com.kimiffy.cn.biubiu.utils.imageloader.base.ImageLoaderHandler;
import com.kimiffy.cn.biubiu.utils.imageloader.glide.CommonOption;
import com.kimiffy.cn.biubiu.utils.imageloader.glide.GlideOption;
import com.kimiffy.cn.biubiu.utils.imageloader.glide.GlideSubject;
import com.kimiffy.cn.biubiu.utils.imageloader.glide.IGlide;

/**
 * Description:图片加载工具类
 * 为了方便扩展采用动态代理的方式,如果需要更换图片框架,需替换对应的被代理图片加载器
 * Created by kimiffy on 2019/2/14.
 */

public class ImageLoader implements ICommon, IGlide {

    private static volatile ImageLoader loader;
    private ImageLoaderHandler imageLoaderHandler;
    private ICommon commonInterface;
    private IGlide glideInterface;

    public static ImageLoader getInstance() {
        if (loader == null) {
            synchronized (ImageLoader.class) {
                if (loader == null) {
                    loader = new ImageLoader();
                }
            }
        }
        return loader;
    }

    private ImageLoader() {
        checkImageLoaderHandler();
    }

    private void checkImageLoaderHandler() {
        if (imageLoaderHandler == null) {
            imageLoaderHandler = new ImageLoaderHandler();
        }
    }


    /**
     * 返回当前图片加载器通用的代理
     * 如果需要更换图片加载框架 只需要更换这里的被代理类
     *
     * @return
     */
    private ICommon getCommonInterface() {
        checkImageLoaderHandler();
        if (commonInterface == null) {
            commonInterface = (ICommon) imageLoaderHandler.bind(new GlideSubject());
        }
        return commonInterface;
    }

    /**
     * 返回当前图片加载器特有特性的代理
     * 如果需要更换图片加载框架 只需要更换这里的被代理类
     *
     * @return
     */
    private IGlide getGlideInterface() {
        checkImageLoaderHandler();
        if (glideInterface == null) {
            glideInterface = (IGlide) imageLoaderHandler.bind(new GlideSubject());
        }
        return glideInterface;
    }


    /**
     * 通过区分配置信息获取对应的代理
     *
     * @param option
     * @return
     */
    private IImageLoad getSubjectByOption(BaseOption option) {
        if (option instanceof GlideOption) {
            return getGlideInterface();
        } else if (option instanceof CommonOption) {
            return getCommonInterface();
        }
        return null;
    }


    /**
     * 初始化
     *
     * @param context
     */
    @Override
    public void init(Context context) {
        getCommonInterface().init(context);
    }


    /**
     * 加载网络图片
     *
     * @param context
     * @param imageUrl
     * @param imageView
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String imageUrl, @NonNull ImageView imageView) {
        getCommonInterface().loadImage(context, imageUrl, imageView);
    }


    /**
     * 通过通用配置信息加载图片
     *
     * @param context
     * @param option  配置信息
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull CommonOption option) {
        IImageLoad iImageLoad = getSubjectByOption(option);
        if (iImageLoad != null) {
            iImageLoad.loadImage(context, option);
        }
    }

}
