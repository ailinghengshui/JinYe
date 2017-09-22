package com.hzjytech.operation.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hzjytech.operation.R;
import com.hzjytech.operation.utils.DensityUtil;

/**
 * Created by hehongcan on 2017/5/22.
 * 两种情况 设置日期和单数蓝色  设置杯数红色
 * 一个都放不下，全部放在右侧
 * 放得下一个日期居左 杯数居右 单数放在外面
 * 两个都放得下 日期在左 单数在右
 */
public class OrderBarView extends View {

    private Context context;
    private Paint mPaint;
    private int measuredWidth;
    private int useFulWidth;
    private float rectWidth;
    private float barHeight;
    private float marginLeft;
    private String firstText="";
    private String secondText="";
    private float radio;
    private int firstWidth;
    private int firstHeight;
    private int secondWidth;
    private int secondHeight;

    public OrderBarView(Context context) {
        this(context,null);
    }

    public OrderBarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OrderBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
         float scale = context.getResources().getDisplayMetrics().density;
        barHeight= (20 * scale + 0.5f);
        mPaint.setTextSize(12*scale+0.5f);
        marginLeft =9.5f*scale+0.5f;
    }
    public void setTwoData(String date,int data,int max,String unit){
        firstText=date;
        secondText=data+unit;
        radio=(float)data/(float)max;
    }
    public void setTwoData(String date,float data,float max,String unit){
        firstText=date;
        secondText=data+unit;
        radio=data/max;
    }
    public void setOneData(int data,int max,String unit){
        secondText=data+unit;
        radio=(float)data/(float)max;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        initData();
        canvas.drawColor(context.getResources().getColor(R.color.standard_white));

            Path path = new Path();
            path.moveTo(0,0);
            path.lineTo(rectWidth,0);
            RectF rectF = new RectF(rectWidth-barHeight, 0, rectWidth + barHeight/2, barHeight);
            path.arcTo(rectF,-90,180,false);
            path.lineTo(rectWidth,barHeight);
            path.lineTo(0,barHeight);
            path.lineTo(0,0);
            if(firstText!=""&&secondText!=""){
                mPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
            }else if(secondText!=""){
                mPaint.setColor(context.getResources().getColor(R.color.color_red));
            }

            canvas.drawPath(path,mPaint);

        mPaint.setColor(Color.WHITE);
        if(firstText!=""&&secondText!=""){
            if(rectWidth<firstWidth+marginLeft){
                mPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
                canvas.drawText(firstText,rectWidth+barHeight,barHeight/2f+firstHeight/2f,mPaint);
                canvas.drawText(secondText,rectWidth+2*barHeight+firstWidth,barHeight/2f+firstHeight/2f,mPaint);
            }else if(rectWidth>firstWidth+marginLeft&&rectWidth<(firstWidth+secondWidth+barHeight+marginLeft)){
                canvas.drawText(firstText,marginLeft,barHeight/2f+firstHeight/2f,mPaint);
                mPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
                canvas.drawText(secondText,rectWidth+barHeight,barHeight/2f+firstHeight/2f,mPaint);
            }else{
                canvas.drawText(firstText,marginLeft,barHeight/2f+firstHeight/2f,mPaint);
                canvas.drawText(secondText,rectWidth-secondWidth,barHeight/2f+firstHeight/2f,mPaint);
            }
        }else if(secondText!=""){
            if(rectWidth<marginLeft+ secondWidth){
                mPaint.setColor(context.getResources().getColor(R.color.color_red));
                canvas.drawText(secondText,rectWidth+barHeight,barHeight/2f+secondHeight/2f,mPaint);
            }else{
                canvas.drawText(secondText,rectWidth-secondWidth,barHeight/2f+secondHeight/2f,mPaint);
            }
        }

        super.onDraw(canvas);
    }

    private void initData() {
        measuredWidth = getMeasuredWidth();
        //可用总宽度
        useFulWidth = measuredWidth - DensityUtil.dp2px(context,20);
        if(firstText!=""&&secondText!=""){
           Rect mFirstBound = new Rect();
            mPaint.getTextBounds(firstText,0,firstText.length(), mFirstBound);
            //日期文本宽度
           firstWidth = mFirstBound.right - mFirstBound.left;
            firstHeight = mFirstBound.bottom - mFirstBound.top;
            Rect mSecondBound = new Rect();
            mPaint.getTextBounds(secondText,0,secondText.length(), mSecondBound);
            //单数文本宽度
            secondWidth =mSecondBound.right - mSecondBound.left;
            //半圆角矩形长度
            rectWidth = useFulWidth * radio;
        }else if(secondText!=""){
            //只有杯数的一系列测量
            Rect mSecondBound = new Rect();
            mPaint.getTextBounds(secondText,0,secondText.length(), mSecondBound);
            //单数文本宽度
            secondWidth =mSecondBound.right - mSecondBound.left;
            secondHeight = mSecondBound.bottom - mSecondBound.top;
            //半圆角矩形长度
            rectWidth = useFulWidth * radio;

        }
    }

 
}
