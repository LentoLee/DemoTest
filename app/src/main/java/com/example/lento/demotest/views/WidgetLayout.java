package com.example.lento.demotest.views;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class WidgetLayout extends ViewGroup {

    // 存放touch的坐标
    private int[] cellInfo = new int[2];
    private OnLongClickListener mClickListener;

    public WidgetLayout(Context context) {
        super(context);

        mClickListener = new OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {

                return false;
            }
        };

    }

    public void addInScreen(View child, int width, int height) {
        LayoutParams lp = new LayoutParams(width, height);
        lp.x = cellInfo[0];
        lp.y = cellInfo[1];
        child.setOnLongClickListener(mClickListener);
        addView(child, lp);
    }

    @Override
    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
        LayoutParams lParams;
        for (int i = 0; i < getChildCount(); i++) {
            lParams = (LayoutParams) getChildAt(i).getLayoutParams();
            getChildAt(i).layout(lParams.x, lParams.y,
                    lParams.x + lParams.width, lParams.y + lParams.height);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        LayoutParams lParams;
        for (int i = 0; i < getChildCount(); i++) {
            lParams = (LayoutParams) getChildAt(i).getLayoutParams();
            getChildAt(i).measure(
                    MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,lParams.width),
                    MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,lParams.height));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        cellInfo[0] = (int) ev.getX();
        cellInfo[1] = (int) ev.getY();
        return super.dispatchTouchEvent(ev);
    }

    private class LayoutParams extends ViewGroup.LayoutParams {

        int x;
        int y;

        public LayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

    }

}
