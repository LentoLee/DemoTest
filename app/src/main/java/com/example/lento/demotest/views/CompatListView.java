package com.example.lento.demotest.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lento on 2017/7/31.
 */

public class CompatListView extends ListView {
    public CompatListView(Context context) {
        super(context);
    }

    public CompatListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompatListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
