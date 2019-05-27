package com.kimiffy.cn.biubiu.constant;

import android.os.Environment;

/**
 * Description: 一些常量配置
 * Created by kimiffy on 2019/3/12.
 */

public class Config {

    private static final String APP_NAME ="biubiu";

    public static final String SP_NAME = APP_NAME+"_sp";//SharedPreferences 文件名

    //存储文件夹路径
    private static final String STORAGE_PATH = Environment.getExternalStorageDirectory() + "/" + APP_NAME + "/";
    //下载文件夹路径
    public static final String DOWNLOAD_FILE_PATH = STORAGE_PATH + "/" + "download" + "/";
    //网络缓存路径
    public static final String NET_CACHE_PATH = STORAGE_PATH + "/" + "cache" + "/"+"net_cache";

    //为了刷新动画过度自然,第一次进入某些页面请求数据默认一定的时间后再请求数据
    public static int LOAD_DELAY_TIME =500;
    public static int LOAD_DELAY_TIME_2 =300;
}
