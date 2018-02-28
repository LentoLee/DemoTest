package com.example.lento.demotest.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.lento.demotest.R;

/**
 * Created by lento on 2018/2/26.
 */

public class ShaderView extends View {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public ShaderView(Context context) {
        super(context);
        init();
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public ShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        Shader shader;
        //1.RadialGradient
//        shader = new RadialGradient(500, 500, 200, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);
        //2.SweepGradient
//        shader = new SweepGradient(500, 500, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"));
        //3.BitmapShader
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.love_avatar);
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //4. ComposeShader 混合Shader
        mPaint.setShader(shader);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawCircle(300, 300, 200, mPaint);
//        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
    }
}
