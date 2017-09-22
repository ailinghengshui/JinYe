package com.hzjytech.operation.scan.decoding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.hzjytech.operation.scan.R;
import com.hzjytech.operation.scan.activity.CaptureActivity;
import com.hzjytech.operation.scan.camera.CameraManager;
import com.hzjytech.operation.scan.camera.PlanarYUVLuminanceSource;


import java.util.Hashtable;

/**
 * Created by Hades on 2016/3/3.
 */
public class DecodeHandler extends Handler {
    private static final String TAG = DecodeHandler.class.getSimpleName();
    private final MultiFormatReader multiFormatReader;
    private final CaptureActivity activity;

    public DecodeHandler(CaptureActivity activity, Hashtable<DecodeHintType, Object> hints) {
        multiFormatReader =new MultiFormatReader();
        multiFormatReader.setHints(hints);
        this.activity=activity;
    }

    @Override
    public void handleMessage(Message message) {
        if (message.what== R.id.decode){
            decode((byte[])message.obj,message.arg1,message.arg2);
        }else if(message.what==R.id.quit){
            Looper.myLooper().quit();
        }
    }

    private void decode(byte[] data,int width,int height){
        long start= System.currentTimeMillis();
        Result rawResult=null;

        byte[] rotatedData=new byte[data.length];
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                rotatedData[x*height+height-y-1]=data[x+y*width];
            }
        }
        int tmp=width;
        width=height;
        height=tmp;

        PlanarYUVLuminanceSource source= CameraManager.get().buildLuminanceSource(rotatedData,width,height);
        BinaryBitmap bitmap=new BinaryBitmap(new HybridBinarizer(source));
        try{
            rawResult=multiFormatReader.decodeWithState(bitmap);
        } catch (ReaderException e) {
            e.printStackTrace();
        }finally {
            multiFormatReader.reset();
        }

        if(rawResult!=null){
            long end= System.currentTimeMillis();
            Log.d(TAG,"Found barcode ("+(end-start)+" ms):\n"+rawResult.toString());
            Message message= Message.obtain(activity.getHandler(),R.id.decode_succeeded,rawResult);
            Bundle bundle=new Bundle();
            bundle.putParcelable(DecodeThread.BARCODE_BITMAP,source.renderCroppedGreyscaleBitmap());
            message.setData(bundle);
            message.sendToTarget();
        }else{
            Message message= Message.obtain(activity.getHandler(),R.id.decode_failed);
            message.sendToTarget();
        }

    }
}
