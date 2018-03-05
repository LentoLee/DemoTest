package com.example.lento.demotest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lento.demotest.R;
import com.example.lento.demotest.adapter.SimpleAdapter;

/**
 * Created by lento on 2017/9/8.
 */

public class CustomViewListActivity extends BaseActivity implements SimpleAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;

    private String[] mCustomViews = new String[]{
            CustomViewActivity.VIEW_DOUBLE_CIRCLE,
            CustomViewActivity.VIEW_RECT_CIRCLE,
            CustomViewActivity.VIEW_LEAF_LOADING,
            CustomViewActivity.VIEW_GRANZORT,
            CustomViewActivity.VIEW_RECT_DOT,
            CustomViewActivity.VIEW_HEART,
            CustomViewActivity.VIEW_SHADER,
            CustomViewActivity.VIEW_PATH_EFFECT,
            CustomViewActivity.VIEW_CLIP,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SimpleAdapter simpleAdapter = new SimpleAdapter(mCustomViews);
        mRecyclerView.setAdapter(simpleAdapter);
        simpleAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int pos) {
        CustomViewActivity.start(this, mCustomViews[pos]);
    }
}
