package com.example.lento.demotest.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.lento.demotest.R;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by lento on 2017/8/17.
 */

public class SelectAlbumActivity extends BaseActivity {

    private static final String TAG = "HAHHAHAH";

    private static final int REQUEST_PICK_IMAGE = 1000;
    private static final int REQUEST_CROP = 1001;
    private ImageView mAvatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_album);
        initView();
    }

    private void initView() {
        mAvatar = (ImageView) findViewById(R.id.iv_avatar);

        findViewById(R.id.btn_select_from_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectAlbum();
                launcherSelectAlbum();
            }
        });
    }


    /**
     * method : 1
     */
    private void selectAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    /**
     * metho : 2
     * @param uri
     */
    private void crop(Uri uri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(uri, "image/*");
        // set crop properties
        cropIntent.putExtra("crop", "true");
        // indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        // indicate output X and Y
        cropIntent.putExtra("outputX", 280);
        cropIntent.putExtra("outputY", 280);

        // retrieve data on return
        cropIntent.putExtra("return-data", true);

        // start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, REQUEST_CROP);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode = " + requestCode + ", \n resultCode = " + resultCode + ",\n  data = " + data + (data == null ? "" : ",\n uri = " + data.getData()));
        printData(data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_PICK_IMAGE) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
//            crop(uri);
            launcherCropImage(uri);
        }

        if (requestCode == REQUEST_CROP) {
//            Bundle extras = data.getExtras();
//            Bitmap selectedBitmap = extras.getParcelable("data");
//            mAvatar.setImageBitmap(selectedBitmap);
            mAvatar.setImageURI(data.getData());
        }
    }

    private void printData(Intent data) {
        if (data == null || data.getExtras() == null) return;
        final Bundle extras = data.getExtras();
        Set<String> keySet = extras.keySet();
        Log.i("xabc", "keySet = " + keySet.size() + ", uri = " + data.getData());
        for (String key : keySet) {
            Object o = extras.get(key);
            Log.i("xabc", "key = " + key + ", value = " + o);
        }
    }


    //===========
    private void launcherSelectAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        wrapIntent(albumIntent);
        startActivityForResult(albumIntent, REQUEST_PICK_IMAGE);
    }

    private void launcherCropImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        intent.putExtra("crop", "true");

        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        Log.d(TAG, "launcher: cropImage file path = " + file.getAbsolutePath() + ", uri = " + Uri.fromFile(file));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        wrapIntent(intent);
        startActivityForResult(intent, REQUEST_CROP);
    }



    private void wrapIntent(Intent intent) {
        Log.d(TAG, "wrapIntent : action = " + intent.getAction());
        List<ResolveInfo> resolveInfos = null;
        try {
            resolveInfos = getPackageManager().queryIntentActivities(intent, 0);
        }catch (Exception e) {
        }
        if (resolveInfos != null && !resolveInfos.isEmpty()) {
            final ResolveInfo resolveInfo = resolveInfos.get(0);
            intent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            Log.d(TAG, "wrapIntent : pkg = " + intent.getComponent().getPackageName() + ", clas = " + intent.getComponent().getClassName());
        }
    }
}
