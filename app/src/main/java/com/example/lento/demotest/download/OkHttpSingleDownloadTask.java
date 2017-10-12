package com.example.lento.demotest.download;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lento.demotest.util.ThreadManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * OkHttp 单任务下载
 * Created by lento on 2017/10/12.
 */

public class OkHttpSingleDownloadTask {
    private static final String TAG = "OkHttpSingleDownload";

    private static final int ERROR_CODE_EQUEUE_FAILURE = 1001;
    private static final int ERROR_CODE_NULL_RESPONSEBODY = 1002;

    private String mFilePath;
    private String mDownloadUrl;
    private DownloadListener mListener;

    private OkHttpClient mOkHttpClient;
    private Call mCall;

    public OkHttpSingleDownloadTask(String filePath, String downloadUrl, DownloadListener listener) {
        mFilePath = filePath;
        mDownloadUrl = downloadUrl;
        mOkHttpClient = new OkHttpClient();
        mListener = listener;
    }


    public void execute() {
        Log.d(TAG, "downloadUrl = " + mDownloadUrl + "\n"
                + "destFilePath = " + mFilePath);
        downLoadFile(mFilePath, mDownloadUrl, mListener);
    }

    public void cancel() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    /**
     * 下载文件，回调到 UI 线程。
     *
     * @param destFilePath    文件存储地址(包含扩展名)
     * @param fileDownloadUrl 文件url
     * @param listener        下载监听
     */
    private void downLoadFile(final String destFilePath, String fileDownloadUrl, final DownloadListener listener) {

        onDownloadStart(listener);

        final File file = new File(destFilePath);
        if (file.exists()) {
            onSuccess(file, listener);
            return;
        }
        final Request request = new Request.Builder().url(fileDownloadUrl).build();
        mCall = mOkHttpClient.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                call.cancel();
                onFail(ERROR_CODE_EQUEUE_FAILURE, "onFailure : " + e.getMessage(), listener);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    ResponseBody body = response.body();
                    if (body == null) {
                        onFail(ERROR_CODE_NULL_RESPONSEBODY, "ResponseBody is null", listener);
                        return;
                    }
                    long total = body.contentLength();
                    long current = 0;
                    is = body.byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        onProgress(total, current, listener);
                    }
                    fos.flush();
                    onSuccess(file, listener);
                } catch (Exception e) {
                    boolean delete = file.delete();
                    onFail(response.code(), "download fail : " + e.getMessage() + ", file delete = " + delete, listener);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        });
    }


    private void onDownloadStart(final DownloadListener listener) {
        ThreadManager.post(ThreadManager.THREAD_UI, new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onStart();
                }
            }
        });
    }

    private void onFail(final int code, final String msg, final DownloadListener listener) {
        ThreadManager.post(ThreadManager.THREAD_UI, new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onFail(code, msg);
                }
            }
        });
    }

    private void onSuccess(final File file, final DownloadListener listener) {
        ThreadManager.post(ThreadManager.THREAD_UI, new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onSuccess(file);
                }
            }
        });
    }

    private void onProgress(final long total, final long current, final DownloadListener listener) {
        ThreadManager.post(ThreadManager.THREAD_UI, new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onProgress(total, current);
                }
            }
        });
    }
}
