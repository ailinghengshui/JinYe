package com.hzjytech.qiniutoken.utils;

import android.util.Base64;

/**
 * Created by Hades on 2016/2/24.
 */
public final class UrlSafeBase64 {
    public static String encodeToString(byte[] data) {
        return Base64.encodeToString(data,Base64.URL_SAFE|Base64.NO_WRAP);
    }
}
