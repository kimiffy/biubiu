package com.kimiffy.cn.biubiu.utils;

/**
 * Description: 字符串工具类
 * Created by kimiffy on 2019/5/7.
 */

public class StringUtil {


    /**
     * 处理一些标题含有html符号的问题
     *
     * @param content 文章标题
     * @return 文章标题
     */
    public static String format(String content) {
        content = content.replace("&ldquo;", "“")
                .replace("&rdquo;", "”")
                .replace("&mdash;", "—");
        return content;
    }


}
