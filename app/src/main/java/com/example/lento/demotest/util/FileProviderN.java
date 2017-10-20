package com.example.lento.demotest.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;


import com.example.lento.demotest.BuildConfig;

import java.io.File;

/**
 * 如果共享的文件在 context.getExternalFilesDir 或者 context.getExternalCacheDir 的话，则 support v4 需要在 24 及以上；
 * <p>
 * 另升级 v4 可能会造成低版本的 com.google.android.gms:play-services-* 、com.google.firebase:firebase-*等
 * 发生 java.lang.IncompatibleClassChangeError 崩溃，所以要升级下版本。
 * <p>
 * Created by lento on 2017/10/20.
 */
public class FileProviderN {

    public static Uri getUriForFile(@NonNull Context context, @NonNull File file) {
        if (Build.VERSION.SDK_INT >= 24) {
            return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     *  FileProvider 向外提供 File，需要配置权限。
     *
     * @param intent
     */
    public static void wrapReadIntent(@NonNull Intent intent) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }
}
