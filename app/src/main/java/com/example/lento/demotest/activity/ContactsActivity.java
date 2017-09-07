package com.example.lento.demotest.activity;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Process;

import com.example.lento.demotest.R;
import com.example.lento.demotest.util.ContactHelper;
import com.example.lento.demotest.util.DeviceHelper;
import com.example.lento.demotest.util.PermissionsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lento on 2017/7/31.
 */

public class ContactsActivity extends BaseActivity implements View.OnClickListener {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 10010;
    private static final int PERMISSIONS_REQUEST_READ_CALLLOG = 10086;

    public static void start(Context context) {
        Intent intent = new Intent(context, ContactsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    // Define global mutable variables
    // Define a ListView object
    // The contact's _ID value
    long mContactId;
    // The contact's LOOKUP_KEY
    String mContactKey;
    // A content URI for the selected contact
    Uri mContactUri;
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;

    private ListView mContactsLv;

    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initViews();
//        checkSbXiaoMi();
        Log.i("xabc", "brand = " + Build.BRAND + ", board = " + Build.BOARD + ", device = " + Build.DEVICE + ", MANUFACTURER = " + Build.MANUFACTURER + ", model = " + Build.MODEL);
    }

//    private void checkSbXiaoMi() {
//        //适配小米机型
//        AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
////        int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Process.myUid(), getPackageName());
//        int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Process.myUid(), getPackageName());
//        if (checkOp == AppOpsManager.MODE_IGNORED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 101);
//            return;
//        }
////        AppOpsManagerCompat appOpsManagerCompat = getSystemService(Context.APP_OPS_SERVICE);
////        int callLog = AppOpsManagerCompat.checkOpNoThrow(AppOpsManager.OPSTR_READ_CALL_LOG, Process.myUid(), getPackageName());
////        appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_READ_CONTACTS, Process.myUid(), getPackageName());
//
////        boolean hasSelfPermission = DeviceHelper.hasSelfPermission(this, Manifest.permission.READ_CONTACTS);
//        boolean hasSelfPermission = DeviceHelper.hasSelfPermission(this, AppOpsManager.OPSTR_READ_CONTACTS);
//        Log.i("xabc", "DeviceHelper has permission = " + hasSelfPermission);
//
//    }

    private void initViews() {
        findViewById(R.id.btn_contacts_permission).setOnClickListener(this);
        findViewById(R.id.btn_phone_permission).setOnClickListener(this);
        mContactsLv = (ListView) findViewById(R.id.all_contacts);
    }


    private void checkContactsPermission() {
        final TextView tvContactPermission = (TextView) findViewById(R.id.contact_permission);
        final boolean hasContactsPermission = PermissionsUtil.hasContactsPermissions(this);


        getLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(ContactsActivity.this, ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                Log.i("xxx", "load finish count = " + data.getCount());
                mCursorAdapter = new SimpleCursorAdapter(
                        ContactsActivity.this,
                        R.layout.contacts_list_item,
                        data,
                        FROM_COLUMNS, TO_IDS,
                        0);
                mContactsLv.setAdapter(mCursorAdapter);
                String permission = tvContactPermission.getHint().toString() + String.valueOf(hasContactsPermission);
                tvContactPermission.setText(permission + ", count = " + mCursorAdapter.getCount());
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });
    }

    private void checkPhonePermission() {
        TextView tvPhonePermission = (TextView) findViewById(R.id.phone_permission);

        boolean hasPhonePermission = PermissionsUtil.hasPhonePermissions(this);
        String permission = tvPhonePermission.getHint().toString() + String.valueOf(hasPhonePermission);
        tvPhonePermission.setText(permission);

        TextView tvCallLog = (TextView) findViewById(R.id.tv_calllog);
        String callDetails = ContactHelper.getCallDetails(this);
        tvCallLog.setText(callDetails);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contacts_permission:
//                checkContactsPermission();
//                showContacts();
                showFavorContacts();
                break;
            case R.id.btn_phone_permission:
                showCallLogs();
//                checkPhonePermission();
                break;
        }
    }

    private void showCallLogs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSIONS_REQUEST_READ_CALLLOG);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            TextView tvPhonePermission = (TextView) findViewById(R.id.phone_permission);

            boolean hasPhonePermission = PermissionsUtil.hasPhonePermissions(this);
            String permission = tvPhonePermission.getHint().toString() + String.valueOf(hasPhonePermission);
            tvPhonePermission.setText(permission);

            TextView tvCallLog = (TextView) findViewById(R.id.tv_calllog);
            String callDetails = ContactHelper.getCallDetails(this);
            tvCallLog.setText(callDetails);
        }
    }


    /**
     * Show the contacts in the ListView.
     */
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            mContactsLv.setAdapter(adapter);

            String sameContact = getSameContact();
            Log.i("xxx", "same contacts:\n " + sameContact);

            //---------text
            final TextView tvContactPermission = (TextView) findViewById(R.id.contact_permission);
            final boolean hasContactsPermission = PermissionsUtil.hasContactsPermissions(this);
            String permission = tvContactPermission.getHint().toString() + String.valueOf(hasContactsPermission);
            tvContactPermission.setText(permission + ", count = " + adapter.getCount());
        }
    }


    /**
     * Show the Favor contacts in the ListView.
     */
    private void showFavorContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<String> contacts = getFavorContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            mContactsLv.setAdapter(adapter);

            //---------text
            final TextView tvContactPermission = (TextView) findViewById(R.id.contact_permission);
            final boolean hasContactsPermission = PermissionsUtil.hasContactsPermissions(this);
            String permission = tvContactPermission.getHint().toString() + String.valueOf(hasContactsPermission);
            tvContactPermission.setText(permission + ", count = " + adapter.getCount());
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        Log.i("xxx", "onRequestPermissionsResult : requestCode = " + requestCode + ", permission = " + Arrays.toString(permissions) + ", grantResults = " + Arrays.toString(grantResults));

        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
//                showContacts();
                showFavorContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSIONS_REQUEST_READ_CALLLOG) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showCallLogs();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the call log", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return contacts;
    }

    /**
     * Read the name of favor contacts.
     *
     * @return a list of names.
     */
    private List<String> getFavorContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        final String favoriteSelection = ContactsContract.Contacts.STARRED + "=1";

        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, favoriteSelection, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String uri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                long contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
                Log.i("xabc", "name = " + name + ", uri = " + uri + ", contactId = " + contactId);

                Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " = " + contactId, null, null);
                if (phones == null || phones.getCount() == 0) {
                    Log.i("xabc", "phones is null");
                }
                while (phones != null && phones.moveToNext()) {
                    String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.i("xabc", "....number = " + number);
                    int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    switch (type) {
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                            // do something with the Home number here...
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                            // do something with the Mobile number here...
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                            // do something with the Work number here...
                            break;
                    }
                }
                phones.close();


                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return contacts;
    }

    private String getSameContact() {
        StringBuffer sb = new StringBuffer();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts.DISPLAY_NAME + " = ?", new String[]{"AAA"}, null);
        Log.i("xxx", "AAA 's count = " + cursor.getCount());
        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                sb.append("name = ").append(name).append(" id = ").append(contactId).append("\n");
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return sb.toString();
    }

}
