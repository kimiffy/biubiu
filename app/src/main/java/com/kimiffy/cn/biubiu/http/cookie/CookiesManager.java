package com.kimiffy.cn.biubiu.http.cookie;

import android.support.annotation.NonNull;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Description:cookie处理
 * Created by kimiffy on 2019/5/10.
 */

public class CookiesManager implements CookieJar {

    private static final PersistentCookieStore cookieStore = new PersistentCookieStore();

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        return cookieStore.get(url);
    }

    /**
     * 清除所有cookie
     */
    public static void clearAllCookies() {
        cookieStore.removeAll();
    }

    /**
     * 清除指定cookie
     *
     * @param url
     * @param cookie
     * @return
     */
    public static boolean clearCookies(HttpUrl url, Cookie cookie) {
        return cookieStore.remove(url, cookie);
    }

    /**
     * 获取cookies
     *
     * @return
     */
    public static List<Cookie> getCookies() {
        return cookieStore.getCookies();
    }
}
