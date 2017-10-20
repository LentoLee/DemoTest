package com.example.lento.demotest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.lento.demotest.R;

/**
 * Created by lento on 2017/10/19.
 */

public class LocalHtmlActivity extends BaseActivity {
    private WebView mBrowser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_html);
        mBrowser = (WebView) findViewById(R.id.browser);
        WebSettings settings = mBrowser.getSettings();
        settings.setJavaScriptEnabled(false);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDefaultFontSize(18);

        mBrowser.loadUrl("file:///android_asset/docs/python/python-install.html");
    }
}
