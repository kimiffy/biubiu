package com.kimiffy.cn.biubiu.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kimiffy.cn.biubiu.constant.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * SharedPreferences工具类
 */
public class SpUtil {

    private static SharedPreferences sp;

    private SpUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static void init() {
        if (sp == null) {
            sp = AppUtils.getContext().getSharedPreferences(Config.SP_NAME, Context.MODE_PRIVATE);
        }
    }

    /**
     * 写入boolean变量至sp中
     *
     * @param key     存储节点名称
     * @param value   存储节点的值
     */
    public static void putBoolean(String key, boolean value) {
        init();
        sp.edit().putBoolean(key, value).apply();
    }

    /**
     * 读取boolean标示从sp中
     *
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static boolean getBoolean( String key, boolean defValue) {
        init();
        return sp.getBoolean(key, defValue);
    }

    /**
     * 写入String变量至sp中
     *
     * @param key     存储节点名称
     * @param value   存储节点的值
     */
    public static void putString( String key, String value) {
        init();
        sp.edit().putString(key, value).apply();
    }

    /**
     * 读取String标示从sp中
     *
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static String getString( String key, String defValue) {
        init();
        return sp.getString(key, defValue);
    }


    /**
     * 写入int变量至sp中
     *
     * @param key     存储节点名称
     * @param value   存储节点的值
     */
    public static void putInt( String key, int value) {
        init();
        sp.edit().putInt(key, value).apply();
    }

    /**
     * 读取int标示从sp中
     *
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static int getInt( String key, int defValue) {
        init();
        return sp.getInt(key, defValue);
    }


    /**
     * 从sp中移除指定节点
     *
     * @param key     需要移除节点的名称
     */
    public static void remove( String key) {
        init();
        sp.edit().remove(key).apply();
    }

    /**
     * 保存List
     *
     * @param key      sp key值
     * @param datalist list
     * @param <T>      item 类型
     */
    public static <T> void setDataList(String key, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;
        Gson gson = new Gson();
        String strJson = gson.toJson(datalist);
        SpUtil.putString( key, strJson);
    }

    /**
     * 获取List
     *
     * @param key sp key值
     * @param <T> item 类型
     * @return list
     */
    public static <T> List<T> getDataList(String key, Class<T> cls) {
        List<T> datalist = new ArrayList<>();
        String strJson = SpUtil.getString( key, null);
        if (null == strJson) {
            return datalist;
        }
        try {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
            for (final JsonElement elem : array) {
                datalist.add(gson.fromJson(elem, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datalist;
    }

}
