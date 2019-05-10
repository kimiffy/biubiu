package com.kimiffy.cn.biubiu.utils;

import android.text.Html;

/**
 * Description: 字符串工具类
 * Created by kimiffy on 2019/5/7.
 */

public class StringUtil {

    private StringUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 处理一些标题含有html符号的问题
     *
     * @param content 文章标题
     * @return 文章标题
     */
    public static CharSequence formatTitle(String content) {
        return Html.fromHtml(content);
    }


}
