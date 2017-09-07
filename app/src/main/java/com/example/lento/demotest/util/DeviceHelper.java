package com.example.lento.demotest.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.AppOpsManagerCompat;
import android.os.Process;

import static android.support.v4.content.ContextCompat.checkSelfPermission;


/**
 * Created by lento on 2017/8/1.
 */

public class DeviceHelper {

    public static boolean hasSelfPermission(Activity context, String permission) {
        // * Can we replace checking M with checking KITKAT? Now permissionToOp is in support library and `checkOpNoThrow` (and `checkOp`) is added in API level 19.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && "Xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
            return hasSelfPermissionForXiaomi(context, permission);
        }
        try {
            return checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        } catch (RuntimeException t) {
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT) // Now target api is KITKAT
    private static boolean hasSelfPermissionForXiaomi(Context context, String permission) {
        // * Can we ignore the case getSystemService returns null or return an instance that is not subclass of AppOpsManager?
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        // Use AppOpsManagerCompat instead
        String op = AppOpsManagerCompat.permissionToOp(permission);

        // Replace TextUtils with null check, since I don't want to depends on TextUtils for just checking "op" is null. empty won't happen according to the doc.
        // Use checkOpNoThrow instead
        // watch out for the combination of || and &&.
        // * Shouldn't we use PermissionChecker#checkSelfPermission, instead of using ActivityCompat#checkSelfPermission?
        return (op == null || appOpsManager.checkOpNoThrow(op, Process.myUid(), context.getPackageName()) == AppOpsManager.MODE_ALLOWED) && checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
