package com.example.lento.demotest.util;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

/**
 * Created by lento on 2017/7/26.
 */

public class SearchMatchRuleCompat {
    /**
     * 电话号码是否可以模糊搜索
     * @param phoneNumber
     * @param query
     * @return
     */
    public static boolean matchesNumber(String phoneNumber, String query) {
        return PhoneNumberUtils.stripSeparators(phoneNumber).contains(PhoneNumberUtils.stripSeparators(query));
    }

    /**
     * 从电话中找到匹配的数字串的前后位置
     * <p>
     * 如: 从 "156 1356 8978" 中 寻找 "68" 在上面电话中的 start index 和 end index，
     * 用于搜索时，高亮显示。
     * <p>
     * //算法待优化。。-_-！
     *
     * @param phoneNumber
     * @param query
     * @return
     */
    public static int[] getMatchedIndex(String phoneNumber, String query) {
        int[] index = new int[2];
        if (phoneNumber.contains(query)) {
            index[0] = phoneNumber.indexOf(query);
            index[1] = index[0] + query.length();
            return index;
        } else {
            char firstQueryChar = query.charAt(0);
            char lastQueryChar = query.charAt(query.length() - 1);

            int start = phoneNumber.indexOf(firstQueryChar);
            int end = phoneNumber.lastIndexOf(lastQueryChar);

            final String stripQuery = PhoneNumberUtils.stripSeparators(query);

            for (int i = start; i < phoneNumber.length(); i++) {
                if (phoneNumber.charAt(i) == firstQueryChar) {
                    for (int j = end; j >= i; j--) {
                        if (phoneNumber.charAt(j) == lastQueryChar) {
                            String substring = phoneNumber.substring(i, j + 1);
                            if (TextUtils.equals(PhoneNumberUtils.stripSeparators(substring), stripQuery)) {
                                index[0] = i;
                                index[1] = j + 1;
                                return index;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
