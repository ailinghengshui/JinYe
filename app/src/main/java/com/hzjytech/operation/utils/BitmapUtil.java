package com.hzjytech.operation.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Hades on 2016/6/4.
 */
public class BitmapUtil {

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle){
        ByteArrayOutputStream output=new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,output);
        if(needRecycle){
            bmp.recycle();
        }

        byte[] result=output.toByteArray();
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}