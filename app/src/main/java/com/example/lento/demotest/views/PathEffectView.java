package com.example.lento.demotest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lento on 2018/2/26.
 */

public class PathEffectView extends View {
    private Paint mPaint;

    private Path mPath;


    public PathEffectView(Context context) {
        super(context);
        init();
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPath.lineTo(40,200);
        mPath.lineTo(150,80);
        mPath.lineTo(173,300);
        mPath.lineTo(240,50);
        mPath.lineTo(440,100);


        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        PathEffect pathEffect;
        //1.CornerPathEffect
//        pathEffect = new CornerPathEffect(60);
        //2.DashPathEffect
        pathEffect = new DashPathEffect(new float[]{10, 5, 20, 5}, 0);
        //3.DiscretePathEffect
//        pathEffect = new DiscretePathEffect(20, 5);







        mPaint.setPathEffect(pathEffect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(500,500,200, mPaint);
        canvas.drawRect(200, 200, 500,500,mPaint);

        //setShadowLayer
        mPaint.setShadowLayer(5, 5, 5, Color.RED);
        mPaint.setTextSize(50);
        canvas.drawText("Hello AHAHA", 200, 900, mPaint);
        mPaint.clearShadowLayer();
        canvas.drawText("Hello AHAHAHA", 200, 1000, mPaint);

    }
}
