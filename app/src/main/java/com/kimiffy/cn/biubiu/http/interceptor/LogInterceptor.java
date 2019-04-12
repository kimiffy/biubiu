package com.kimiffy.cn.biubiu.http.interceptor;

import android.support.annotation.NonNull;

import com.kimiffy.cn.biubiu.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description:日志拦截器
 * Created by kimiffy on 2019/3/11.
 */

public class LogInterceptor implements Interceptor{

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int byteCount = 1024 * 1024;
        ResponseBody responseBody = response.peekBody(byteCount);
        LogUtil.d(request.url());//请求
        LogUtil.json(responseBody.string());//响应
        return response;
    }

}
