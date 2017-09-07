package com.example.lento.demotest.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by lento on 2017/7/31.
 */

public class ContactsAdapter extends SimpleCursorAdapter {
    public ContactsAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }
}
