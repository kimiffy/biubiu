package com.kimiffy.cn.biubiu.http.dowload;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Description:下载响应体  带进度回调
 * Created by kimiffy on 2019/3/15.
 */

public class ProgressResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private ProgressListener progressListener;

    ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
        progressListener.onStart(responseBody.contentLength());
    }


    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead;
                progressListener.onProgress(responseBody.contentLength(), totalBytesRead);

                return bytesRead;
            }
        };
    }

    public interface ProgressListener {

        /**
         * 开始下载
         * @param totalSize
         */
        void onStart(long totalSize);


        /**
         * 下载进度
         * @param totalSize
         * @param downSize
         */
        void onProgress(long totalSize, long downSize);


    }
}
