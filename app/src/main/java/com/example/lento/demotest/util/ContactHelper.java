package com.example.lento.demotest.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import java.util.Date;

/**
 * Created by lento on 2017/7/31.
 */

public class ContactHelper {

    public static String getCallDetails(Context context) {

        final StringBuffer sb = new StringBuffer();
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return "No Permission";
//        }
        Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (managedCursor != null) {
            int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            int id = managedCursor.getColumnIndex(CallLog.Calls._ID);

            sb.append("Call Details :");
            while (managedCursor.moveToNext()) {
                String displayName = managedCursor.getString(name);
                String phNumber = managedCursor.getString(number);
                checkIfNeedRefreshName(context, displayName, phNumber);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                int callLogId = managedCursor.getInt(id);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                sb.append("\nPhone Number:--- " + phNumber
                        + " \nCall displayName:--- " + displayName
                        + " \nCall id:--- " + callLogId
                        + " \nCall Type:--- " + dir
                        + " \nCall Date:--- " + callDayTime
                        + " \nCall duration in sec :--- " + callDuration);
                sb.append("\n----------------------------------");
            }
            managedCursor.close();
        }
        return sb.toString();

    }

    private static void checkIfNeedRefreshName(Context context, String displayName, String phNumber) {
        if (TextUtils.isEmpty(displayName)) {
            Log.i("xxx", "displayName is empty, so try to get name from db. number = " + phNumber);
            getContactIDFromNumber(phNumber, context);
        }
    }


    public static int getContactIDFromNumber(String contactNumber, Context context) {
        long startTime = System.currentTimeMillis();

        contactNumber = Uri.encode(contactNumber);
        int phoneContactID = -1;
        final Cursor contactLookupCursor = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, contactNumber),
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.LOOKUP_KEY}, null, null, null);
        if (contactLookupCursor != null) {
            Log.i("xxx", "contactNumber = " + contactNumber + ", contact cursor count = " + contactLookupCursor.getCount());
            while (contactLookupCursor.moveToNext()) {
                phoneContactID = contactLookupCursor.getInt(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
                String phoneDisplayName = contactLookupCursor.getString(contactLookupCursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                Log.d("xxx", "phone display name = " + phoneDisplayName + ", contact id = " + phoneContactID);
            }
            contactLookupCursor.close();
        }
        Log.i("xxx", "cost time = " + (System.currentTimeMillis() - startTime) + " ms");
        return phoneContactID;
    }

}
