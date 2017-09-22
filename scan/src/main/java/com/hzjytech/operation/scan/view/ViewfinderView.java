package com.hzjytech.operation.scan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.hzjytech.operation.scan.R;
import com.hzjytech.operation.scan.camera.CameraManager;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Hades on 2016/3/3.
 */
public class ViewfinderView extends View {

    private static final int[] SCANNER_ALPHA={0,64,128,192,255,192,128,64};
    private static final long ANIMATION_DELAY=100L;
    private static final int OPAQUE=0xff;


    private final Paint paint;
    private final int maskColor;
    private final int resultColor;
    private final int frameColor;
    private final int laserColor;
    private final int resultPointColor;
    private int scannerAlpha;
    private Bitmap scanBitmap;
    private Bitmap resultBitmap;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;

    boolean isFirst;
    /**
     * 四个绿色边角对应的长度
     */
    private int ScreenRate=40;

    /**
     * 四个绿色边角对应的宽度
     */
    private static final int CORNER_WIDTH = 10;
    /**
     * 扫描框中的中间线的宽度
     */
    private static final int MIDDLE_LINE_WIDTH = 5;

    /**
     * 扫描框中的中间线的与扫描框左右的间隙
     */
    private static final int MIDDLE_LINE_PADDING = 50;
    /**
     * 中间滑动线的最顶端位置
     */
    private int slideTop;

    /**
     * 中间滑动线的最底端位置
     */
    private int slideBottom;
    private static final int SPEEN_DISTANCE = 10;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context,attrs);

        paint=new Paint();

        maskColor= ContextCompat.getColor(context, R.color.viewfinder_mask);
        resultColor= ContextCompat.getColor(context,R.color.result_view);
        frameColor= ContextCompat.getColor(context,R.color.colorPrimary);
        laserColor = ContextCompat.getColor(context,R.color.colorPrimary);
        resultPointColor= ContextCompat.getColor(context,R.color.possible_result_points);

        scannerAlpha=0;

        possibleResultPoints=new HashSet<ResultPoint>(5);
        Drawable drawable= ContextCompat.getDrawable(context,R.drawable.icon_scan_big);
        BitmapDrawable bitmapDrawable= (BitmapDrawable) drawable;
        scanBitmap=bitmapDrawable.getBitmap();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Rect frame= CameraManager.get().getFramingRect();

        Matrix matrix=new Matrix();
        float scalePercent=(frame.right-frame.left)*1.0f/(scanBitmap.getWidth());
        matrix.postScale(scalePercent,scalePercent);
        scanBitmap= Bitmap.createBitmap(scanBitmap,0,0,scanBitmap.getWidth(),scanBitmap.getHeight(),matrix,true);

        if(frame==null){
            return;
        }
        //初始化中间线滑动的最上边和最下边
        if(!isFirst){
            isFirst = true;
            slideTop = frame.top+200;
            slideBottom = frame.bottom-200;
        }

        int width=canvas.getWidth();
        int height=canvas.getHeight();

        paint.setColor(resultBitmap!=null?resultColor:maskColor);
        canvas.drawRect(0,0,width,frame.top,paint);
        canvas.drawRect(0,frame.top,frame.left,frame.bottom+1,paint);
        canvas.drawRect(frame.right+1,frame.top,width,frame.bottom+1,paint);
        canvas.drawRect(0,frame.bottom+1,width,height,paint);

        if(resultBitmap!=null){
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap,frame.left,frame.top,paint);
        }else{
           /* paint.setColor(frameColor);
            canvas.drawBitmap(scanBitmap,frame.left,frame.top,paint);

            paint.setColor(laserColor);
            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            scannerAlpha=(scannerAlpha+1)%SCANNER_ALPHA.length;*/
            //画扫描框边上的角，总共8个部分
            paint.setColor(frameColor);
            canvas.drawRect(frame.left, frame.top- CORNER_WIDTH, frame.left + ScreenRate,
                    frame.top , paint);
            canvas.drawRect(frame.left- CORNER_WIDTH, frame.top- CORNER_WIDTH, frame.left , frame.top
                    + ScreenRate, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.top- CORNER_WIDTH, frame.right,
                    frame.top, paint);
            canvas.drawRect(frame.right, frame.top- CORNER_WIDTH, frame.right+ CORNER_WIDTH, frame.top
                    + ScreenRate, paint);
            canvas.drawRect(frame.left, frame.bottom , frame.left
                    + ScreenRate, frame.bottom+ CORNER_WIDTH, paint);
            canvas.drawRect(frame.left- CORNER_WIDTH, frame.bottom-  ScreenRate ,
                    frame.left, frame.bottom+ CORNER_WIDTH, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.bottom ,
                    frame.right, frame.bottom+ CORNER_WIDTH, paint);
            canvas.drawRect(frame.right , frame.bottom - ScreenRate,
                    frame.right+ CORNER_WIDTH, frame.bottom+ CORNER_WIDTH, paint);


            int middle=frame.height()/2+frame.top;
            //canvas.drawRect(frame.left+2,middle-1,frame.right-1,middle+2,paint);
            slideTop += SPEEN_DISTANCE;
            if(slideTop >= frame.bottom){
                slideTop = frame.top;
            }
            canvas.drawRect(frame.left + MIDDLE_LINE_PADDING, slideTop - MIDDLE_LINE_WIDTH/2, frame.right - MIDDLE_LINE_PADDING,slideTop + MIDDLE_LINE_WIDTH/2, paint);

            float marginTop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    30,
                    getResources().getDisplayMetrics());
            float marginLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    12,
                    getResources().getDisplayMetrics());
            float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    12,
                    getResources()
                            .getDisplayMetrics());
            paint.setColor(Color.WHITE);
            paint.setTextSize(textSize);
           canvas.drawText(getResources().getString(R.string.scan),frame.left+marginLeft,frame.bottom+marginTop,paint);
            Collection<ResultPoint> currentPossible=possibleResultPoints;
            Collection<ResultPoint> currentLast=lastPossibleResultPoints;
            if(currentPossible.isEmpty()){
                lastPossibleResultPoints=null;
            }else{
                possibleResultPoints=new HashSet<ResultPoint>(5);
                lastPossibleResultPoints=currentPossible;
                paint.setAlpha(OPAQUE);
                paint.setColor(resultPointColor);
                for(ResultPoint point:currentPossible){
                    canvas.drawCircle(frame.left+point.getX(),frame.top+point.getY(),6.0f,paint);
                }
            }
            if(currentLast!=null){
                paint.setAlpha(OPAQUE/2);
                paint.setColor(resultPointColor);
                for(ResultPoint point:currentLast){
                    canvas.drawCircle(frame.left+point.getY(),frame.top+point.getY(),3.0f,paint);
                }
            }
            postInvalidateDelayed(ANIMATION_DELAY,frame.left,frame.top,frame.right,frame.bottom);
        }
    }

    public void drawViewfinder(){
        resultBitmap=null;
        invalidate();
    }

    public void drawResultBitmap(Bitmap barcode){
        resultBitmap=barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point){
        possibleResultPoints.add(point);
    }
}
