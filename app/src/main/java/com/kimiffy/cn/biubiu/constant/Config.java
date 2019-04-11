package com.kimiffy.cn.biubiu.constant;

import android.os.Environment;

/**
 * Description: 一些常量配置
 * Created by kimiffy on 2019/3/12.
 */

public class Config {

    public static final String SP_NAME = "sp_biubiu";//SharedPreferences 文件名

    public static final String HTTP_CACHE_NAME = "biubiu";//网络缓存文件名
    //存储文件夹路径
    private static final String STORAGE_PATH = Environment.getExternalStorageDirectory() + "/" + "biubiu" + "/";
    //下载文件夹路径
    public static final String DOWNLOAD_FILE_PATH = STORAGE_PATH + "/" + "download" + "/";

}
