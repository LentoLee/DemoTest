package com.example.lento.demotest.download;

import java.io.File;

/**
 * Created by lento on 2017/10/12.
 */

public interface DownloadListener {
    void onStart();

    void onProgress(long total, long current);

    void onSuccess(File file);

    void onFail(int errorCode, String errorMsg);
}
