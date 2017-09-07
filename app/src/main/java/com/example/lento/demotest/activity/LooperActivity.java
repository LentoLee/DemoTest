package com.example.lento.demotest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lento.demotest.R;

/**
 * Created by lento on 2017/8/24.
 */

public class LooperActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LooperActivityx";

    private static final int WHAT_UPDATE_TEXT_BY_BTN = 0x001;
    private static final int WHAT_UPDATE_TEXT_BY_BTN2 = 0x002;

    private TextView mTextView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_UPDATE_TEXT_BY_BTN) {
                mTextView.setText("Update By btn1");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        initView();
    }

    private void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                mHandler.sendEmptyMessage(WHAT_UPDATE_TEXT_BY_BTN);
                break;
            case R.id.button2:
                updateByBtn2Method1();
                break;
            case R.id.button3:
//                updateByBtn3Method1();
//                updateByBtn3Method2();
                updateByBtn3Method3();
//                updateByBtn3Method4();
                break;
        }
    }

    private void updateByBtn2Method1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                log("btn2: run...");

                Handler btn2Handler = new Handler(Looper.getMainLooper());//差异点

                log("btn2Handler looper = " + Looper.myLooper() + ", mHandler looper = " + mHandler.getLooper() + ", MainLooper = " + Looper.getMainLooper());
                btn2Handler.post(new Runnable() {
                    @Override
                    public void run() {
                        log("btn2： btn2Handler post runnable run...");
                        mTextView.setText("Update By btn2");
                    }
                });
            }
        }).start();
    }

    /**
     * 错误方式1：子线程更新 ui
     */
    private void updateByBtn3Method1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                log("btn3: run... method 1");

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mTextView.setText("Update By btn3");//ViewRootImpl$CalledFromWrongThreadException
            }
        }).start();
    }

    /**
     * 错误方式2：子线程创建 handler
     */
    private void updateByBtn3Method2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                log("btn3: run... method 2");

                Handler btn3Handler = new Handler();// RuntimeException： Can't create handler inside thread that has not called Looper.prepare()

                log("btn3Handler looper = " + Looper.myLooper() + ", mHandler looper = " + mHandler.getLooper() + ", MainLooper = " + Looper.getMainLooper());
                btn3Handler.post(new Runnable() {
                    @Override
                    public void run() {
                        log("btn2： btn3Handler post runnable run...");
                        mTextView.setText("Update By btn2");
                    }
                });
            }
        }).start();
    }

    /**
     * 错误方式3：子线程 handler，没有调用 loop() 方法，或者提前调用loop()，都不会执行到 Runnable run 方法。
     */
    private void updateByBtn3Method3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                log("btn3: run... method 3");
                Looper.prepare();
                Handler btn3Handler = new Handler(/*Looper.myLooper()*/);//差异点
                log("btn3Handler looper = " + Looper.myLooper() + ", mHandler looper = " + mHandler.getLooper() + ", MainLooper = " + Looper.getMainLooper());
                Looper.loop();//这行加不加，都一样。
                btn3Handler.post(new Runnable() {
                    @Override
                    public void run() {
                        log("btn2： btn3Handler post runnable run...");//btn3Handler 没有调用 loop() 或者提前调用loop() 方法，无法接收到消息。
                        mTextView.setText("Update By btn2");
                    }
                });

            }
        }).start();
    }


    /**
     * 错误方式4：子线程 handler 里更新 ui
     */
    private void updateByBtn3Method4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                log("btn3: run... method 4");
                Looper.prepare();
                Handler btn3Handler = new Handler(/*Looper.myLooper()*/);//差异点
                log("btn3Handler looper = " + Looper.myLooper() + ", mHandler looper = " + mHandler.getLooper() + ", MainLooper = " + Looper.getMainLooper());
                btn3Handler.post(new Runnable() {
                    @Override
                    public void run() {
                        log("btn2： btn3Handler post runnable run...");// 可以接收到消息，但 因为不是在主线程，所以下面的操作还是会崩溃。
                        mTextView.setText("Update By btn2");//ViewRootImpl$CalledFromWrongThreadException
                    }
                });
                Looper.loop();

            }
        }).start();
    }

    private static void log(String s) {
        Log.i(TAG, s);
    }

}
