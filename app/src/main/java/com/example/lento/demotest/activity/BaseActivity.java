package com.example.lento.demotest.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lento on 2017/7/31.
 */

public class BaseActivity extends AppCompatActivity {

    public static void start(Context context, Class<? extends BaseActivity> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }
}
