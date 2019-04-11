package com.kimiffy.cn.biubiu.http.dowload;

/**
 * Description: 下载监听
 * Created by kimiffy on 2019/3/15.
 */

public interface DownLoadListener {


    /**
     * 开始下载
     * 注意: 是在子线程中回调的 UI操作需要在主线程中进行
     * 这里已经在downloadManager中处理了,不用自己处理
     *
     * @param totalSize 文件总大小
     */
    void onStart(long totalSize);


    /**
     * 下载进度
     * 注意: 下载进度是在子线程中回调的 UI操作需要在主线程中进行
     * 这里已经在downloadManager中处理了,不用自己处理
     *
     * @param totalSize 文件总大小
     * @param downSize  已下载大小
     */
    void onProgress(long totalSize, long downSize);


    /**
     * 下载成功
     *
     * @param path
     */
    void onSuccess(String path);


    /**
     * 下载失败
     *
     * @param errorInfo
     */
    void onFail(String errorInfo);


    /**
     * 下载结束,不管成功或是失败都会执行
     */
    void onComplete();

}
