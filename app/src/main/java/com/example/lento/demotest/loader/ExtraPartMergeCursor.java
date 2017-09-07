package com.example.lento.demotest.loader;

import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Bundle;

public class ExtraPartMergeCursor extends MergeCursor {

    private Cursor[] mCursors;

    public ExtraPartMergeCursor(Cursor[] cursors) {
        super(cursors);
        mCursors = cursors;
    }

    @Override
    public Bundle getExtras() {
        if (mCursors.length > 1 && mCursors[1] != null) {
            return mCursors[1].getExtras();
        } else {
            return super.getExtras();
        }
    }

    public int getExtraCursorCount() {
        if (mCursors.length > 1 && mCursors[0] != null) {
            return mCursors[0].getCount();
        }
        return 0;
    }
}
