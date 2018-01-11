package com.example.lento.demotest.activity;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.lento.demotest.R;
import com.example.lento.demotest.views.WidgetLayout;

import java.util.List;

/**
 * Created by lento on 2018/1/9.
 */

public class AddWidgetActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "AddWidgetActivity";
    private static final int APPWIDGET_HOST_ID = 1024;
    private static final int REQUEST_PICK_APPWIDGET = 10086;
    private static final int REQUEST_CREATE_APPWIDGET = 1;
    private static final String EXTRA_CUSTOM_WIDGET = "custom_widget";

    private AppWidgetHost mAppWidgetHost;
    private AppWidgetManager mAppWidgetManager;
    private WidgetLayout mWidgetLayout;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_widget);
//        findViewById(R.id.btn_add_widget).setOnClickListener(this);
//        mAppWidgetHost = new AppWidgetHost(this, APPWIDGET_HOST_ID);

        mContext = getApplicationContext();

        mAppWidgetManager = AppWidgetManager.getInstance(mContext);
        mAppWidgetHost = new AppWidgetHost(mContext, APPWIDGET_HOST_ID);
        mAppWidgetHost.startListening();

        mWidgetLayout = new WidgetLayout(this);

        mWidgetLayout.setOnLongClickListener(this);
        setContentView(mWidgetLayout);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.btn_add_widget:
                startWidget();
                break;
        }
    }

    /**
     * 跳列表页
     */
    private void startWidget() {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
        int appWidgetId = mAppWidgetHost.allocateAppWidgetId();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        startActivityForResult(intent, REQUEST_PICK_APPWIDGET);
    }

    private void addAppWidget(Intent data) {
        int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        String customWidget = data.getStringExtra(EXTRA_CUSTOM_WIDGET);
        if ("search_widget".equals(customWidget)) {
            mAppWidgetHost.deleteAppWidgetId(appWidgetId);
        } else {
            AppWidgetProviderInfo appWidget = mAppWidgetManager
                    .getAppWidgetInfo(appWidgetId);

            Log.d("addAppWidget", "configure:" + appWidget.configure);
            if (appWidget.configure != null) {
                // 弹出配置界面
                Intent intent = new Intent(
                        AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
                intent.setComponent(appWidget.configure);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        appWidgetId);

                startActivityForResult(intent, REQUEST_CREATE_APPWIDGET);
            } else {
                // 直接添加到界面
                onActivityResult(REQUEST_CREATE_APPWIDGET, Activity.RESULT_OK,
                        data);
            }
        }
    }


    /**
     * 添加widget
     *
     * @param data
     */
    private void completeAddAppWidget(Intent data) {
        Bundle extras = data.getExtras();
        int appWidgetId = extras
                .getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

        AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager
                .getAppWidgetInfo(appWidgetId);

        View hostView = mAppWidgetHost.createView(this, appWidgetId,
                appWidgetInfo);

        mWidgetLayout.addInScreen(hostView, appWidgetInfo.minWidth,
                appWidgetInfo.minHeight);
    }

    @Override
    protected void onDestroy() {
        try {
            mAppWidgetHost.stopListening();
        } catch (NullPointerException ex) {
            Log.i(TAG, "problem while stopping AppWidgetHost during Launcher destruction", ex);
        }
        super.onDestroy();
    }

    @Override
    public boolean onLongClick(View v) {
//        Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
//        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetHost.allocateAppWidgetId());
//         start the pick activity
//        startActivityForResult(pickIntent, REQUEST_PICK_APPWIDGET);

        //添加单个的widget
        List<AppWidgetProviderInfo> installedProviders = mAppWidgetManager.getInstalledProviders();
        AppWidgetProviderInfo providerInfo = installedProviders.get(4);
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_BIND);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetHost.allocateAppWidgetId());
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER, providerInfo.provider);
        startActivityForResult(intent, 10010);

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode = " + requestCode + ", resultCode = " + resultCode + ", data = " + data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_APPWIDGET:
                    addAppWidget(data);
                    break;
                case REQUEST_CREATE_APPWIDGET:
                case 10010:
                    completeAddAppWidget(data);
                    break;
            }
        } else if (requestCode == REQUEST_PICK_APPWIDGET
                && resultCode == RESULT_CANCELED && data != null) {
            int appWidgetId = data.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            if (appWidgetId != -1) {
                mAppWidgetHost.deleteAppWidgetId(appWidgetId);
            }
        }
    }
}
