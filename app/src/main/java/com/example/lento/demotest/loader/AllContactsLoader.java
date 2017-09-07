package com.example.lento.demotest.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContentResolverCompat;
import android.support.v4.os.CancellationSignal;

/**
 * Created by yinlongquan@conew.com on 2017/6/15.
 */
public class AllContactsLoader extends BaseCursorLoader {


    public AllContactsLoader(Context context) {
        super(context);
    }

    /* Runs on a worker thread */
    @Override
    public Cursor loadInBackground() {
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw new RuntimeException("background task is canceled");
            }
            mCancellationSignal = new CancellationSignal();
        }
        Cursor cursor;
        try {
            ContentResolver resolver = getContext().getContentResolver();
            String favoriteSelection;
            String unFavoriteSelection;

            favoriteSelection = ContactsContract.Contacts.STARRED + "=1";

//            if (mDeviceFilter.isSpecialPhone()) {
//                unFavoriteSelection = ContactsContract.Contacts.STARRED + "=0";
//            } else {
                unFavoriteSelection = null;
//            }

            Cursor favoriteCursor = ContentResolverCompat.query(resolver,
                    mUri, mProjection, favoriteSelection, null, mSortOrder,
                    mCancellationSignal);


            Cursor allContactCursor = ContentResolverCompat.query(resolver,
                    mUri, mProjection, unFavoriteSelection, mSelectionArgs, mSortOrder,
                    mCancellationSignal);

            cursor = new ExtraPartMergeCursor(new Cursor[]{favoriteCursor, allContactCursor});
            if (cursor != null) {
                try {
                    // Ensure the cursor window is filled.
                    cursor.getCount();
                    cursor.registerContentObserver(mObserver);
                } catch (RuntimeException ex) {
                    cursor.close();
                    throw ex;
                }
            }
            return cursor;
        } finally {
            synchronized (this) {
                mCancellationSignal = null;
            }
        }
    }
}
