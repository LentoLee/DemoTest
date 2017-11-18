package com.example.lento.demotest.util;

/**
 * Created by lento on 2017/11/3.
 */


import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;

/**
 * 查表取得给定汉字串的首字母
 */
public final class FirstCharChinese {

    public static String getAllFirstLetters(Context context, String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        final StringBuilder builder = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            builder.append(getFirstLetter(context, str.substring(i, i + 1)));
        }
        return builder.toString();
    }

    public static char getFirstLetter(Context context, String chineseChar) {
        if (chineseChar == null || chineseChar.length() == 0)
            return 0;
        return getFirstLetter(context, chineseChar.charAt(0));
    }

    public static char getFirstLetter(Context context, char chineseChar) {
        final int index = chineseChar - FIRST_CHINESE;
        if (index >= 0 && index < CHINESE_COUNT)
            return (char) getTable(context)[index];
        return chineseChar;
    }

    public static final int FIRST_CHINESE = 19968;
    public static final int CHINESE_COUNT = 20902;

    private static byte[] mTable = null;

    private static byte[] getTable(Context context) {
        if (mTable == null) {
            synchronized (FirstCharChinese.class) {
                try {
                    final AssetManager assets = context.getAssets();
                    final InputStream is = assets.open("chinese_letters.bin");
                    final byte[] data = new byte[CHINESE_COUNT];
                    if (is.read(data, 0, CHINESE_COUNT) != CHINESE_COUNT)
                        throw new RuntimeException("table count incorrect!");
                    is.close();
                    mTable = data;
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return mTable;
    }
}
