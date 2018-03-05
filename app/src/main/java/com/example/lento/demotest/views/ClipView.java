package com.example.lento.demotest.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.lento.demotest.R;

/**
 * Created by lento on 2018/2/28.
 */

public class ClipView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;

    public ClipView(Context context) {
        super(context);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    Path circlePath;

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.love_avatar);
        circlePath = new Path();
        circlePath.addCircle(5 + mBitmap.getWidth() / 2, (mBitmap.getHeight() * 1.5f + 20), 200, Path.Direction.CW);
    }


    private int mWidth;
    private int mHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.clipRect(20, 20, mBitmap.getWidth() - 40, mBitmap.getHeight() - 40);
        canvas.drawBitmap(mBitmap, 10, 10, mPaint);
        canvas.restore();

        canvas.save();
        canvas.clipPath(circlePath);
        canvas.drawBitmap(mBitmap, 10, 20 + mBitmap.getHeight(), mPaint);
        canvas.restore();
    }
}
