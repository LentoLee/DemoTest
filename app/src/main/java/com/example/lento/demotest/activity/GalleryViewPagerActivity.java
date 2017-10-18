package com.example.lento.demotest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.lento.demotest.R;
import com.example.lento.demotest.transformer.DepthPageTransformer;

/**
 * Created by lento on 2017/10/18.
 */

public class GalleryViewPagerActivity extends BaseActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_viewpager);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View item = View.inflate(GalleryViewPagerActivity.this, R.layout.item_viewpager, null);
//                ImageView item = new ImageView(container.getContext());
//                item.setImageResource(R.drawable.a);
                container.addView(item);
                return item;
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageMargin(20);
        mViewPager.setPageTransformer(false, new DepthPageTransformer());

        //fix only current item can scroll.
        ((ViewGroup) mViewPager.getParent()).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
    }

}
