package com.kimiffy.cn.biubiu.http;

import com.kimiffy.cn.biubiu.constant.Config;
import com.kimiffy.cn.biubiu.http.cookie.CookiesManager;
import com.kimiffy.cn.biubiu.http.interceptor.HeaderInterceptor;
import com.kimiffy.cn.biubiu.http.interceptor.LogInterceptor;
import com.kimiffy.cn.biubiu.http.interceptor.NetCacheInterceptor;
import com.kimiffy.cn.biubiu.utils.AppUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:
 * Created by kimiffy on 2019/3/11.
 */

public class ApiManager {
    private OkHttpClient mOkHttpClient;
    private static ApiService mApiService;

    private ApiManager() {
        initOkHttp();
        initRetrofit();
    }

    public static synchronized ApiService getApiService() {
        if (mApiService == null) {
            ApiManager mApiManager = new ApiManager();
        }
        return mApiService;
    }

    /**
     * 初始化OkHttp
     */
    private void initOkHttp() {

        int TIME_OUT = 10;//链接超时时间

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)  //连接超时设置
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)  //写入缓存超时
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)  //读取缓存超时
                .retryOnConnectionFailure(true)  //失败重连
                .addInterceptor(new HeaderInterceptor())  //添加header
                .cookieJar(new CookiesManager());

        addCacheInterceptor(builder);
        addLogInterceptor(builder);  //日志拦截器
        mOkHttpClient = builder.build();
    }

    /**
     * 添加网络缓存
     *
     * @param builder
     */
    private void addCacheInterceptor(OkHttpClient.Builder builder) {
        NetCacheInterceptor netCacheInterceptor = new NetCacheInterceptor();
        builder.addInterceptor(netCacheInterceptor)
                .addNetworkInterceptor(netCacheInterceptor);
        //文件缓存路径
        File file = new File(Config.NET_CACHE_PATH);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if(mkdirs){
                Cache cache = new Cache(file, 1024 * 1024 * 50);
                builder.cache(cache);
            }
        }else{
            Cache cache = new Cache(file, 1024 * 1024 * 50);
            builder.cache(cache);
        }
    }

    /**
     * 初始化retrofit
     */
    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.SERVER_ADDRESS)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();
        mApiService = retrofit.create(ApiService.class);
    }


    /**
     * 调试模式下加入日志拦截器
     *
     * @param builder
     */
    private void addLogInterceptor(OkHttpClient.Builder builder) {
        if (AppUtils.isDebug()) {
            builder.addInterceptor(new LogInterceptor());
        }
    }
}
