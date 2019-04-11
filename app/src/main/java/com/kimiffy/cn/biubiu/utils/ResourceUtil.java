package com.kimiffy.cn.biubiu.utils;

import android.content.Context;

/**
 * Description:通过资源名获取资源ID工具类
 * Created by kimiffy on 2019/3/6.
 */

public class ResourceUtil {

    /**
     * 获取id 资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "id");
    }

    /**
     * 获取布局文件资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getLayoutId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "layout");
    }

    /**
     * 获取字符串资源ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getStringId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "string");
    }

    /**
     * 获取图片资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getDrawableId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "drawable");
    }
    /**
     * 获取Mipmap资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getMipmapId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "mipmap");
    }

    /**
     * 获取Color资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getColorId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "color");
    }

    /**
     * 获取Dimen资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getDimenId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "dimen");
    }

    /**
     * 获取Attr资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getAttrId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "attr");
    }

    /**
     * 获取Style资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getStyleId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "style");
    }

    /**
     * 获取Anim资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getAnimId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "anim");
    }

    /**
     * 获取Array资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getArrayId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "array");
    }

    /**
     * 获取Integer资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getIntegerId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "integer");
    }

    /**
     * 获取Bool资源的ID
     * @param context
     * @param resourceName
     * @return
     */
    public static int getBoolId(Context context, String resourceName) {
        return getIdentifierByType(context, resourceName, "bool");
    }

    /**
     * 通过文件名拿到对应的资源ID
     * @param context
     * @param resourceName 要查找资源ID的资源名
     * @param defType 资源所在文件夹类型
     * @return
     */
    private static int getIdentifierByType(Context context, String resourceName, String defType) {
        return context.getResources().getIdentifier(resourceName,defType,context.getPackageName());
    }

}
