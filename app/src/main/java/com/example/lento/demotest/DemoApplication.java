package com.example.lento.demotest;

import android.app.Application;

import com.example.lento.demotest.util.ThreadManager;

/**
 * Created by lento on 2017/9/26.
 */

public class DemoApplication extends Application {
    private static DemoApplication sIns;

    public DemoApplication() {
        sIns = this;
    }

    public static DemoApplication get() {
        return sIns;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ThreadManager.startup();
    }
}
