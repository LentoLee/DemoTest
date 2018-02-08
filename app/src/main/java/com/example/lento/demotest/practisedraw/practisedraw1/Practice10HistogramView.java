package com.example.lento.demotest.practisedraw.practisedraw1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Practice10HistogramView extends View {
    private Paint mPaint;
    private static final int TABLE_WIDTH = 90;
    private static final int TABLE_WIDTH_OFFSET = 20;

    static class Model {
        public Model(String name, int value) {
            this.name = name;
            this.value = value;
        }

        String name;
        int value;
    }

    private static List<Model> sModels;

    static {
        List<Model> models = new ArrayList<>();
        models.add(new Model("Froyo", 5));
        models.add(new Model("GB", 15));
        models.add(new Model("ICS", 15));
        models.add(new Model("JB", 45));
        models.add(new Model("KitKat", 160));
        models.add(new Model("L", 200));
        models.add(new Model("M", 140));
        sModels = Collections.unmodifiableList(models);
    }


    public Practice10HistogramView(Context context) {
        super(context);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        //1.坐标系
        float[] coords = new float[]{
                100, 10, 100, 500,
                100, 500, 1000, 500
        };
        canvas.drawLines(coords, mPaint);
        //2.遍历内容
        final int size = sModels.size();
        for (int i = 0; i < size; i++) {
            final float left = 100 + TABLE_WIDTH_OFFSET * (i + 1) + i * TABLE_WIDTH;
            final Model model = sModels.get(i);
            //直方图
            mPaint.setColor(Color.GREEN);
            canvas.drawRect(left, 500 - model.value, left + TABLE_WIDTH, 500, mPaint);
            //文字
            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize(25);
            canvas.drawText(model.name, left + (TABLE_WIDTH - mPaint.measureText(model.name)) / 2, 520, mPaint);
        }

        mPaint.setTextSize(50);
        canvas.drawText("直方图", (100 + 1000) / 2 - mPaint.measureText("直方图") / 2, 650, mPaint);

    }
}
