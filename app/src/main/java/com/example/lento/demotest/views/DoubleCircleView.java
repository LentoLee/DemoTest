package com.example.lento.demotest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lento on 2017/4/25.
 */

public class DoubleCircleView extends View {

    private Paint mPaint;

    private int mWidth;
    private int mHeight;


    public DoubleCircleView(Context context) {
        super(context);
        init();
    }

    public DoubleCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoubleCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0, 0, 200, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(0, 0, 180, mPaint);

        mPaint.setColor(Color.RED);
        for (int i = 0; i <= 360; i += 10) {
            canvas.drawLine(180, 0, 200, 0, mPaint);
            canvas.rotate(10);
        }

    }
}
