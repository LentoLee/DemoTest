package com.example.lento.demotest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lento.demotest.activity.BaseActivity;
import com.example.lento.demotest.activity.ContactsActivity;
import com.example.lento.demotest.activity.CustomViewListActivity;
import com.example.lento.demotest.activity.DialogThemeActivity;
import com.example.lento.demotest.activity.LooperActivity;
import com.example.lento.demotest.activity.SelectAlbumActivity;
import com.example.lento.demotest.activity.SelectCameraActivity;
import com.example.lento.demotest.adapter.SimpleAdapter;
import com.example.lento.demotest.contact.ContactItem;
import com.example.lento.demotest.contact.FavoriteContactLoader;
import com.example.lento.demotest.samsungdemo.activity.CallLogsDemoActivity;
import com.example.lento.demotest.util.SearchMatchRuleCompat;
import com.example.lento.demotest.views.DoubleCircleView;
import com.example.lento.demotest.views.LeafLoadingView;
import com.example.lento.demotest.views.RectCircleView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements SimpleAdapter.OnItemClickListener {
    private static final String TAG = "MainActivity";
    private final Map<String, Object> mViews = new HashMap<>();
    private RecyclerView mRecyclerView;
    private String[] mFuncs = new String[]{
            "Custom Views",
            "Contacts Permission",
            "vivo permission",
            "oppo permission",
            "select pic from Album",
            "Looper",
            "应用数据访问权限",
            "select pic from Camera",
            "dialog activity",
            "sumsung demo calllog"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMapViews();
        mRecyclerView = (RecyclerView) findViewById(R.id.rcv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SimpleAdapter simpleAdapter = new SimpleAdapter(mFuncs);
        mRecyclerView.setAdapter(simpleAdapter);
        simpleAdapter.setOnItemClickListener(this);

//        matchNumber();

//        onItemClick(1);

//        parseArray();

        getFavorContacts();
    }

    private void getFavorContacts() {
        FavoriteContactLoader loader = new FavoriteContactLoader(this);
        loader.loadFavorContacts(new FavoriteContactLoader.OnLoadListener() {
            @Override
            public void onLoad(List<ContactItem> favorContactList) {
                Log.d("xabc", "onLoadFinish : " + favorContactList.toString());
            }
        });
    }

    private void parseArray() {
        String a = "[18,23]";
        try {
            JSONArray array = new JSONArray(a);
            int o = (int) array.get(0);
            int o1 = (int) array.get(1);
            Log.i("xxx", "o = " + o + ", o1 = " + o1);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void matchNumber() {
        String phoneNumber = "156 1356 8978";
        String query = "68";
        int[] matchedIndex = SearchMatchRuleCompat.getMatchedIndex(phoneNumber, query);
        Log.i(TAG, "matchedIndex = " + Arrays.toString(matchedIndex));
    }

    private void initMapViews() {
        mViews.put("DoubleCircleView", DoubleCircleView.class);
        mViews.put("RectCircleView", RectCircleView.class);
        mViews.put("LeafLoadingView", LeafLoadingView.class);
    }

    @Override
    public void onItemClick(int pos) {
        switch (pos) {
            case 0:
                CustomViewListActivity.start(this, CustomViewListActivity.class);
                break;
            case 1:
                ContactsActivity.start(this);
                break;
            case 2:
                gotoVivoPermission();
                break;
            case 3:
                gotoOppoPermission();
                break;
            case 4:
                gotoSelectAlbum();
                break;
            case 5:
                gotoLooperActivity();
                break;
            case 6:
                gotoAppInfo();
                break;
            case 7:
                gotoSelectCameraActivity();
                break;
            case 8:
                gotoDialogThemeActivity();
                break;
            case 9:
                Intent intent = new Intent(MainActivity.this, CallLogsDemoActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void gotoDialogThemeActivity() {
        Intent intent = new Intent(this, DialogThemeActivity.class);
        startActivity(intent);
    }

    private void gotoSelectCameraActivity() {
        Intent intent = new Intent(this, SelectCameraActivity.class);
        startActivity(intent);
    }

    private void gotoAppInfo() {
        Intent it = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
        startActivity(it);

    }

    private void gotoLooperActivity() {
        Intent intent = new Intent(this, LooperActivity.class);
        startActivity(intent);
    }

    private void gotoSelectAlbum() {
        Intent intent = new Intent(this, SelectAlbumActivity.class);
        startActivity(intent);
    }

    private void gotoOppoPermission() {
        Intent appIntent = getPackageManager().getLaunchIntentForPackage("com.oppo.safe");
        if(appIntent != null){
            startActivity(appIntent);
//            floatingView = new SettingFloatingView(this, "SETTING", getApplication(), 1);
//            floatingView.createFloatingView();
            return;
        }
    }


    private void gotoVivoPermission() {
        // vivo 点击设置图标>加速白名单>我的app
        //      点击软件管理>软件管理权限>软件>我的app>信任该软件
        Intent appIntent = getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");

        if(appIntent != null){
            startActivity(appIntent);
//            floatingView = new SettingFloatingView(this, "SETTING", getApplication(), 0);
//            floatingView.createFloatingView();
            return;
        }
    }
}
