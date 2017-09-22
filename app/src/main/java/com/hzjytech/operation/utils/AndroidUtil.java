package com.hzjytech.operation.utils;

import android.content.Context;
import android.provider.Settings;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by hehongcan on 2017/4/6.
 */
public class AndroidUtil {
    private static UUID uuid;

    public static String getDeviceUuid(Context context){
        if (uuid == null){
            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                if (!"9774d56d682e549c".equals(androidId)){
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("UTF-8"));
                }else {
                    // final String deviceId =  getDeviceId(context);
                    // uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("UTF-8")) : UUID.randomUUID();
                    uuid = UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return String.valueOf(uuid);
    }
}
