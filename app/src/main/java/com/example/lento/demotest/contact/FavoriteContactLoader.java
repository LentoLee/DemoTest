package com.example.lento.demotest.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.content.ContentResolverCompat;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lento on 2017/8/31.
 */

public class FavoriteContactLoader {

    private final Context mContext;
    private AtomicBoolean mIsQuery;
    static final String[] COLUMNS;

    private static final String[] sProjection = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
    };

    static {
        List<String> projection = Lists.newArrayList(sProjection);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            projection.remove(0);
//            projection.add(0, ContactsContract.Contacts.NAME_RAW_CONTACT_ID);
//        }
        COLUMNS = projection.toArray(new String[projection.size()]);
    }

    public FavoriteContactLoader(Context context) {
        mContext = context;
        mIsQuery = new AtomicBoolean(false);
    }

    public interface OnLoadListener {
        void onLoad(List<ContactItem> favorContactList);
    }

    public void loadFavorContacts(final OnLoadListener listener) {
        if (listener == null) return;
        if (mIsQuery.get()) {
            return;
        }
        mIsQuery.set(true);
        final List<ContactItem> data = new ArrayList<>();
        final String favoriteSelection = ContactsContract.Contacts.STARRED + "=1";
        Cursor cursor = null;
        try {
            final ContentResolver contentResolver = mContext.getContentResolver();
            cursor = ContentResolverCompat.query(contentResolver,
                    ContactsContract.Contacts.CONTENT_URI,
                    COLUMNS,
                    favoriteSelection,
                    null,
                    ContactsContract.Contacts.SORT_KEY_PRIMARY,
                    null);

            while (cursor.moveToNext()) {
                long rawContactId = cursor.getLong(cursor.getColumnIndex(COLUMNS[0]));
                String name = cursor.getString(cursor.getColumnIndex(COLUMNS[1]));
                String picUri = cursor.getString(cursor.getColumnIndex(COLUMNS[2]));
                String number = queryPhoneNumberByContactId(contentResolver, rawContactId);
                Log.d("xabc", "rawContactId = " + rawContactId + ", number = " + number + ", name = " + name + ", pic = " + picUri);
                ContactItem item = new ContactItem(rawContactId, number, name, picUri);
                data.add(item);
            }
            listener.onLoad(data);
            mIsQuery.set(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private String queryPhoneNumberByContactId(ContentResolver contentResolver, long rawContactId) {
        Cursor cursor = null;
        try {
            cursor = ContentResolverCompat.query(contentResolver,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",//// TODO: 2017/8/31
                    new String[]{String.valueOf(rawContactId)},
                    null,
                    null);

            if (cursor == null || cursor.getCount() == 0) {
                Log.i("xabc", "phones is null");
                return "";
            }
            if (cursor.moveToNext()) {
                final String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.i("xabc", "....number = " + number);
                return number;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
