package com.example.lento.demotest.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;


import com.example.lento.demotest.BuildConfig;

import java.security.InvalidParameterException;

/**
 * 线程管理器
 */
public class ThreadManager {
    public static final int THREAD_UI = 0;
    /**
     * 后台功能线程
     */
    public static final int THREAD_BACKGROUND_LOCAL = 1;
    public static final int THREAD_BACKGROUND_NET = 2;
    public static final int THREAD_SIZE = 3;

    // 线程信息数组
    private static final Handler[] HANDLER_LIST = new Handler[THREAD_SIZE];
    private static final String[] THREAD_NAME_LIST = {
            "thread_ui", "thread_bg_local", "thread_bg_net"
    };

    public static void startup() {
        HANDLER_LIST[THREAD_UI] = new Handler();
    }

    /**
     * 派发任务
     *
     * @param index
     * @param r
     */
    public static void post(int index, Runnable r) {
        postDelayed(index, r, 0);
    }

    public static void postDelayed(int index, Runnable r, long delayMillis) {
        Handler handler = getHandler(index);
        handler.postDelayed(r, delayMillis);
    }

    public static void removeCallbacks(int index, Runnable r) {
        Handler handler = getHandler(index);
        handler.removeCallbacks(r);
    }

    /**
     * 获取线程Handler
     *
     * @param index
     * @return
     */
    public static Handler getHandler(int index) {
        if (index < 0 || index >= THREAD_SIZE) {
            throw new InvalidParameterException();
        }

        if (HANDLER_LIST[index] == null) {
            synchronized (HANDLER_LIST) {
                if (HANDLER_LIST[index] == null) {
                    HandlerThread thread = new HandlerThread(THREAD_NAME_LIST[index]);
                    if (index != THREAD_UI) // 我们不设置ui线程.
                        thread.setPriority(Thread.MIN_PRIORITY);
                    thread.start();
                    Handler handler = new Handler(thread.getLooper());
                    HANDLER_LIST[index] = handler;
                }
            }
        }

        return HANDLER_LIST[index];
    }

    /**
     * Returns true if the current thread is the given thread.
     */
    public static boolean runningOn(int index) {
        return getHandler(index).getLooper() == Looper.myLooper();
    }

    // 从单独的ThreadChecker挪到这里.
    public static void currentlyOn(int index) {
        if (BuildConfig.DEBUG) {
            if (!runningOn(index))
                throw new AssertionError("Running on incorrect thread!");
        }
    }
}