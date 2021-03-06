package com.example.lento.demotest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.example.lento.demotest.activity.AddWidgetActivity;
import com.example.lento.demotest.activity.BaseActivity;
import com.example.lento.demotest.activity.ContactsActivity;
import com.example.lento.demotest.activity.CustomViewListActivity;
import com.example.lento.demotest.activity.DialogThemeActivity;
import com.example.lento.demotest.activity.DownloadActivity;
import com.example.lento.demotest.activity.GalleryViewPagerActivity;
import com.example.lento.demotest.activity.LocalHtmlActivity;
import com.example.lento.demotest.activity.LooperActivity;
import com.example.lento.demotest.activity.ParseHtmlActivity;
import com.example.lento.demotest.activity.SelectAlbumActivity;
import com.example.lento.demotest.activity.SelectCameraActivity;
import com.example.lento.demotest.adapter.SimpleAdapter;
import com.example.lento.demotest.contact.ContactItem;
import com.example.lento.demotest.contact.FavoriteContactLoader;
import com.example.lento.demotest.practisedraw.Practise1DrawActivity;
import com.example.lento.demotest.practisedraw.Practise2DrawActivity;
import com.example.lento.demotest.samsungdemo.activity.CallLogsDemoActivity;
import com.example.lento.demotest.util.FirstCharChinese;
import com.example.lento.demotest.util.SearchMatchRuleCompat;
import com.example.lento.demotest.util.ThreadManager;
import com.example.lento.demotest.views.DoubleCircleView;
import com.example.lento.demotest.views.LeafLoadingView;
import com.example.lento.demotest.views.RectCircleView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
            "sumsung demo calllog",
            "parse html",
            "install assets inner apk",
            "download",
            "GalleryViewPager",
            "Local HTML",
            "add widget",
            "PractiseDraw1",
            "PractiseDraw2"
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

//        getFavorContacts();

        getFirstChineseLetter();
    }

    private void getFirstChineseLetter() {
        char firstLetter = FirstCharChinese.getFirstLetter(DemoApplication.get(), "王");
        String s = FirstCharChinese.getAllFirstLetters(DemoApplication.get(), "水");
        Log.d(TAG, "first  = " + firstLetter + ", s = " + s);
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
//                gotoVivoPermission();
//                gotoVivoFloatPermission();
//                gotoVivoFingerPrint();
                gotoS5();
                break;
            case 3:
//                gotoOppoPermission();
//                gotoMIUI89Permission();
//                gotoOppoFingerPrint();
                gotoS6();
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
            case 10:
                gotoParseHtml();
                break;
            case 11:
                installAssetsApk();
                break;
            case 12:
                openDownloadPage();
                break;
            case 13:
                openGalleryViewPagerActivity();
                break;
            case 14:
                openLocalHtmlPage();
                break;
            case 15:
                startAddWidgetActivity();
                break;
            case 16:
                startPractise1Draw();
                break;
            case 17:
                startPractise2Draw();
                break;
        }
    }

    private void startPractise1Draw() {
        Intent intent = new Intent(this, Practise1DrawActivity.class);
        startActivity(intent);
    }
    private void startPractise2Draw() {
        Intent intent = new Intent(this, Practise2DrawActivity.class);
        startActivity(intent);
    }

    private void startAddWidgetActivity() {
        Intent intent = new Intent(this, AddWidgetActivity.class);
        startActivity(intent);
    }

    private void gotoS6() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName("com.android.settings",
                "com.android.settings.LockscreenMenuSettings"));
        startActivity(intent);
    }

    private void gotoS5() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName("com.android.settings",
                "com.android.settings.fingerprint.FingerPrintSettingsUseFingerprint"));
        startActivity(intent);
    }

    private void gotoVivoFloatPermission() {
        Intent intent = new Intent();
        intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.settings.systemsecurity.SystemSecuritySettings");
//        intent.setAction("action.coloros.safecenter.FloatWindowListActivity");
        startActivity(intent);
    }


    private void gotoVivoFingerPrint() {
        Intent intent = new Intent();
        intent.setClassName("com.vivo.fingerprint", "com.vivo.fingerprint.FingerprintActivity");
        startActivity(intent);
    }

    private void gotoOppoFingerPrint() {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.oppo.settings.fingerprint.FingerprintSettings");
        startActivity(intent);
    }

    private void gotoMIUI89Permission() {
        Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        localIntent.putExtra("extra_pkgname", getPackageName());
        startActivity(localIntent);
    }

    private void openLocalHtmlPage() {
        Intent intent = new Intent(this, LocalHtmlActivity.class);
        startActivity(intent);
    }

    private void openGalleryViewPagerActivity() {
        Intent intent = new Intent(this, GalleryViewPagerActivity.class);
        startActivity(intent);
    }

    private void openDownloadPage() {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }

    private boolean mIsInstall;
    private void installAssetsApk() {
        if (mIsInstall) {
            Log.d(TAG, "is installing....");
            return;
        }
        mIsInstall = true;
        ThreadManager.post(ThreadManager.THREAD_BACKGROUND_LOCAL, new Runnable() {
            @Override
            public void run() {
                final String filePath = getExternalCacheDir() + "/Depth.apk";//Environment.getExternalStorageDirectory().getAbsolutePath() + "/mmmTest.apk";
                long start = System.currentTimeMillis();
                if (copyAssetsFile(filePath, "Depth.apk")) {
                    Log.d(TAG, "copy assets cost time = " + (System.currentTimeMillis() - start) + " ms");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(filePath)),
                            "application/vnd.android.package-archive");
                    startActivity(intent);
                }
                mIsInstall = false;
            }
        });
    }

    /**
     * copy assets file to dst file path.
     *
     * @param dstFilePath
     * @param assetsName
     * @return isSuccess
     */
    private boolean copyAssetsFile(String dstFilePath, String assetsName) {
        Log.d(TAG, "dstFilePath = " + dstFilePath + ", assets file name = " + assetsName);

        if (TextUtils.isEmpty(dstFilePath) || TextUtils.isEmpty(assetsName)) {
            return false;
        }
        final AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(assetsName);
            out = new FileOutputStream(dstFilePath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            in.close();
            in = null;

            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.d(TAG, "exception = " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void gotoParseHtml() {
        Intent intent = new Intent(this, ParseHtmlActivity.class);
        startActivity(intent);
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
        if (appIntent != null) {
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

        if (appIntent != null) {
            startActivity(appIntent);
//            floatingView = new SettingFloatingView(this, "SETTING", getApplication(), 0);
//            floatingView.createFloatingView();
            return;
        }
    }
}
