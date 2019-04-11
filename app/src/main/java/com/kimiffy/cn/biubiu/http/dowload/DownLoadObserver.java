package com.kimiffy.cn.biubiu.http.dowload;

import android.text.TextUtils;

import io.reactivex.observers.DisposableObserver;

/**
 * Description:
 * Created by kimiffy on 2019/3/15.
 */

public abstract class DownLoadObserver extends DisposableObserver<String> {

    @Override
    public void onNext(String path) {
        if(!TextUtils.isEmpty(path)){
            onSuccess(path);
        }else{
            onError("下载失败!");
        }
    }

    @Override
    public void onError(Throwable e) {
        onError(e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    /**
     * 下载成功
     * @param path 下载文件的路径
     */
    public abstract void onSuccess(String path);

    /**
     * 下载失败
     * @param msg
     */
    public abstract void onError(String msg);
}
