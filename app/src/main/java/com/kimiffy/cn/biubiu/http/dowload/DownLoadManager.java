package com.kimiffy.cn.biubiu.http.dowload;

import android.support.annotation.NonNull;

import com.kimiffy.cn.biubiu.constant.Config;
import com.kimiffy.cn.biubiu.http.ApiService;
import com.kimiffy.cn.biubiu.utils.aop.annotation.MainThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Description:下载工具
 * Created by kimiffy on 2019/3/15.
 */

public class DownLoadManager {

    private final HashMap<Object, DownLoadObserver> mHashMap;
    private final DownLoadService mService;
    private static DownLoadManager instance;
    private DownLoadListener mDownLoadListener;

    public static DownLoadManager getInstance() {
        synchronized (DownLoadManager.class) {
            if (instance == null) {
                instance = new DownLoadManager();
            }
        }
        return instance;
    }

    private DownLoadManager() {

        mHashMap = new HashMap<>();
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        return response.newBuilder().body(new ProgressResponseBody(response.body(),
                                new ProgressResponseBody.ProgressListener() {
                                    @MainThread//指定在主线程中执行
                                    @Override
                                    public void onStart(long totalSize) {
                                        if (null != mDownLoadListener) {
                                            mDownLoadListener.onStart(totalSize);
                                        }
                                    }

                                    @MainThread//指定在主线程中执行
                                    @Override
                                    public void onProgress(long totalSize, long downSize) {
                                        if (null != mDownLoadListener) {
                                            mDownLoadListener.onProgress(totalSize, downSize);
                                        }
                                    }
                                })).build();
                    }
                }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiService.SERVER_ADDRESS)
                .build();
        mService = retrofit.create(DownLoadService.class);

    }


    /**
     * 下载单个文件
     *
     * @param url
     * @param downLoadListener
     */
    public void downFile(final String url, DownLoadListener downLoadListener) {
        //如果正在下载则不处理
        if (isDownLoad(url)) {
            //pause(url);
            return;
        }
        this.mDownLoadListener = downLoadListener;
        //存储的文件路径
        final String path = getTempPath(url);

        DownLoadObserver observer = mService.downloadFile(url).map(new Function<ResponseBody, String>() {
            @Override
            public String apply(ResponseBody body) throws Exception {
                File file = saveFile(path, body);
                if (null != file && file.exists()) {
                    return file.getPath();
                }
                return "";
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DownLoadObserver() {
                    @Override
                    public void onSuccess(String path) {
                        if (null != mDownLoadListener) {
                            mDownLoadListener.onSuccess(path);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (null != mDownLoadListener) {
                            mDownLoadListener.onFail(msg);
                        }
                    }
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mHashMap.remove(url);
                        if (null != mDownLoadListener) {
                            mDownLoadListener.onComplete();
                        }
                    }
                });
        //保存
        mHashMap.put(url, observer);
    }


    /**
     * 是否在下载
     *
     * @param url
     * @return
     */
    private boolean isDownLoad(String url) {
        return mHashMap.containsKey(url);
    }


    /**
     * 暂停/取消任务
     *
     * @param url 完整url
     */
    private void pause(String url) {
        if (mHashMap.containsKey(url)) {
            DownLoadObserver observer = mHashMap.get(url);
            if (observer != null) {
                observer.dispose();
                mHashMap.remove(url);
            }
        }
    }

    /**
     * 获取临时文件名
     *
     * @param url
     * @return
     */
    private String getTempPath(String url) {
        String type = "";
        if (url.contains(".")) {
            type = url.substring(url.lastIndexOf("."));
        }
        String dirName = Config.DOWNLOAD_FILE_PATH;
        File f = new File(dirName);
        if (!f.exists()) {
            f.mkdirs();
        }
        // TODO: 2019/3/24 这里的临时文件名是不是需要修改
        return dirName + System.currentTimeMillis() + type;
    }

    /**
     * 保存文件
     *
     * @param filePath
     * @param body
     * @return
     */
    private File saveFile(String filePath, ResponseBody body) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = null;
        try {
            if (filePath == null) {
                return null;
            }
            file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] fileReader = new byte[4096];

            inputStream = body.byteStream();
            outputStream = new FileOutputStream(file);

            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }
}
