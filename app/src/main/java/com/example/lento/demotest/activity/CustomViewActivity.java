package com.example.lento.demotest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.lento.demotest.views.DoubleCircleView;
import com.example.lento.demotest.views.GranzortView;
import com.example.lento.demotest.views.HeartView;
import com.example.lento.demotest.views.LeafLoadingView;
import com.example.lento.demotest.views.PathEffectView;
import com.example.lento.demotest.views.RectCircleView;
import com.example.lento.demotest.views.RectDotView;
import com.example.lento.demotest.views.ShaderView;

/**
 * Created by lento on 2017/9/8.
 */

public class CustomViewActivity extends BaseActivity {
    private static final String EXTRA_VIEW_NAME = "extra_view_name";

    public static final String VIEW_DOUBLE_CIRCLE = "DoubleCircleView";
    public static final String VIEW_RECT_CIRCLE = "RectCircleView";
    public static final String VIEW_LEAF_LOADING = "LeafLoadingView";
    public static final String VIEW_GRANZORT = "GranzortView";

    public static final String VIEW_RECT_DOT = "RectDotView";
    public static final String VIEW_HEART = "HeartView";
    public static final String VIEW_SHADER = "ShaderView";
    public static final String VIEW_PATH_EFFECT = "PathEffectView";

    public static void start(Context context, String viewName) {
        Intent intent = new Intent(context, CustomViewActivity.class);
        intent.putExtra(EXTRA_VIEW_NAME, viewName);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            final String viewName = getIntent().getStringExtra(EXTRA_VIEW_NAME);
            View contentView = null;

            switch (viewName) {
                case VIEW_DOUBLE_CIRCLE:
                    contentView = new DoubleCircleView(this);
                    break;
                case VIEW_RECT_CIRCLE:
                    contentView = new RectCircleView(this);
                    break;
                case VIEW_LEAF_LOADING:
                    contentView = new LeafLoadingView(this);
                    break;
                case VIEW_GRANZORT:
                    contentView = new GranzortView(this);
                    break;
                case VIEW_RECT_DOT:
                    contentView = new RectDotView(this);
                    break;
                case VIEW_HEART:
                    contentView = new HeartView(this);
                    break;
                case VIEW_SHADER:
                    contentView = new ShaderView(this);
                    break;
                case VIEW_PATH_EFFECT:
                    contentView = new PathEffectView(this);
                    break;
            }
            if (contentView != null) {
                setContentView(contentView);
            }
        }
    }
}
