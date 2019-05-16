package com.kimiffy.cn.biubiu.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:Gson工具类
 * Created by kimiffy on 2019/5/5.
 */

public class GsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }


    private GsonUtil() {
    }


    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        String string = null;
        if (gson != null) {
            string = gson.toJson(object);
        }
        return string;
    }


    /**
     * 将gsonString转成泛型bean
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(json, cls);
        }
        return t;
    }


    /**
     * 转成list
     * 解决泛型在编译期类型被擦除导致报错
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        if(TextUtils.isEmpty(json)){
            return list;
        }
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> toListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }


    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> toMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

}
