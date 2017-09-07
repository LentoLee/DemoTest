package com.example.lento.demotest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.example.lento.demotest.R;

/**
 * Created by lento on 2017/9/7.
 */

public class DialogThemeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialer_layout_permission_tips_pop);
        modifyBackgroundDimAmount();
    }

    public void modifyBackgroundDimAmount() {
        WindowManager m = getWindowManager();
//        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 1.0);   //高度设置为屏幕的1.0
//        p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.8
//        p.alpha = 1.0f;      //设置 dialog 本身透明度
        p.dimAmount = 0.8f;      //设置黑暗度
        getWindow().setAttributes(p);     //设置生效
    }
}
