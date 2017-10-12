package com.example.lento.demotest.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.lento.demotest.R;
import com.example.lento.demotest.download.DownloadListener;
import com.example.lento.demotest.download.OkHttpSingleDownloadTask;
import com.example.lento.demotest.util.MD5Util;


import java.io.File;


/**
 * Created by lento on 2017/10/12.
 */

public class DownloadActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "DownloadActivity";

    private Button mDownloadBtn;
    private ProgressBar mProgressBar;
    private static final String APK_DOWNLOAD_URL = "http://img-download.pchome.net/download/1k1/pt/4u/osvcg1-1w5l.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initView();
    }

    private void initView() {
        mDownloadBtn = (Button) findViewById(R.id.okhttp_download);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mDownloadBtn.setOnClickListener(this);
    }

    private OkHttpSingleDownloadTask mTask;

    @Override
    public void onClick(View v) {
        if (mTask == null) {
            mTask = new OkHttpSingleDownloadTask(getApkDownloadCacheDir() + File.separator + MD5Util.getMD5String(APK_DOWNLOAD_URL) + ".jpg", APK_DOWNLOAD_URL, new DownloadListener() {
                @Override
                public void onStart() {
                    Log.d(TAG, "onStart");
                    mDownloadBtn.setClickable(false);
                }

                @Override
                public void onProgress(long total, long current) {
                    Log.d(TAG, "onProgress : total " + total + ", current = " + current + ", progress = " + ((float) current / total));
                    mProgressBar.setProgress((int) (current * 100 / total));
                }

                @Override
                public void onSuccess(File file) {
                    Log.d(TAG, "onSuccess : file =" + file);
                    mDownloadBtn.setClickable(true);

                }

                @Override
                public void onFail(int errorCode, String errorMsg) {
                    Log.d(TAG, "onFail: error code = " + errorCode + ", errorMsg = " + errorMsg);
                    mDownloadBtn.setClickable(true);
                }
            });
        }
        mTask.execute();
    }

    /**
     * 优先写到 cache 目录，不存在再写到其他目录
     *
     * @return
     */
    private String getApkDownloadCacheDir() {
        String path = "";
        if (getExternalCacheDir() != null) {
            path = getExternalCacheDir().getAbsolutePath();
        }
        if (TextUtils.isEmpty(path)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return path;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTask != null) {
            mTask.cancel();
        }
    }
}
