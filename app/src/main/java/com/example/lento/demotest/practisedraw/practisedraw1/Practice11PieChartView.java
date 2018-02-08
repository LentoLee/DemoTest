package com.example.lento.demotest.practisedraw.practisedraw1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Practice11PieChartView extends View {
    private Paint mPaint;

    static class SDKModel {
        public SDKModel(String name, float value, int color) {
            this.name = name;
            this.value = value;
            this.color = color;
        }

        String name;
        float value;
        int color;
    }

    private static List<SDKModel> sModels;

    static {
        List<SDKModel> models = new ArrayList<>();
        models.add(new SDKModel("Lollipop", 120f / 360, Color.RED));
        models.add(new SDKModel("Marshmallow", 50f / 360, Color.YELLOW));
        models.add(new SDKModel("Froyo", 5f / 360, Color.LTGRAY));
        models.add(new SDKModel("Gingerbread", 8f / 360, Color.MAGENTA));
        models.add(new SDKModel("Ice Cream Sandwich", 7f / 360, Color.LTGRAY));
        models.add(new SDKModel("Jelly Bean", 50f / 360, Color.GREEN));
        models.add(new SDKModel("KitKat", 120f / 360, Color.BLUE));
        sModels = Collections.unmodifiableList(models);
    }


    public Practice11PieChartView(Context context) {
        super(context);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int mWidth;
    private int mHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图


        canvas.translate(mWidth / 2, mHeight / 2);

//        mPaint.setColor(Color.RED);
//        canvas.drawArc(-100, -100, 100, 100, -180, 120, true, mPaint);
        final int size = sModels.size();
        float startAngle = -180;
        float sweepAngle = 0;
        for (int i = 0; i < size; i++) {
            final SDKModel sdkModel = sModels.get(i);
            mPaint.setColor(sdkModel.color);
            sweepAngle = 360 * sdkModel.value;
            Log.d("xxx", "startAngle = " + startAngle + ", sweepAngle = " + sweepAngle);
            canvas.drawArc(-200, -200, 200, 200, startAngle, sweepAngle, true, mPaint);
            startAngle += sweepAngle;
            if (TextUtils.equals(sdkModel.name, "Lollipop")) {
                canvas.translate(20, 20);
            }
        }

        mPaint.setTextSize(50);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("饼图", -mPaint.measureText("饼图") / 2, 300, mPaint);
    }
}
