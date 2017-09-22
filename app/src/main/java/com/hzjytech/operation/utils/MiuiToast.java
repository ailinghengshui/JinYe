package com.hzjytech.operation.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hzjytech.operation.R;

import java.util.Timer;

/**
 * Created by hehongcan on 2017/5/5.
 */
public class  MiuiToast  {

    private final String mText;
    private boolean mShowTime;
    private boolean mIsShow;
    private  WindowManager mWdm;
    private  View mToastView;
    private View mNextView;
    private  Timer mTimer;
    private WindowManager.LayoutParams mParams;
    private static MiuiToast result;
    private final Handler handler;
    public static MiuiToast makeText(Context context, String text, boolean showTime) {
        if(result==null){
            result = new MiuiToast(context, text, showTime);
        }else {
            result.setText(text);
        }
        return result;
    }
    private MiuiToast(Context context, String text, boolean showTime ){
        mShowTime = showTime;//记录Toast的显示长短类型
        mIsShow = false;//记录当前Toast的内容是否已经在显示
        mText =text;
        mWdm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        handler = new Handler();
        //通过Toast实例获取当前android系统的默认Toast的View布局\
        LayoutInflater from = LayoutInflater.from(context.getApplicationContext());
        View view = from.inflate(R.layout.activity_popupwindow_tips, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_tips);
        textView.setText(text);
       // mToastView = Toast.makeText(context, text, Toast.LENGTH_SHORT).getView();
        mToastView=view;
        mTimer = new Timer();
        //设置布局参数
        setParams();
    }
    private void setText(String text){
        TextView textView = (TextView) mNextView.findViewById(R.id.tv_tips);
        textView.setText(text);
    }
    private void setParams() {
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = R.style.anim_view;//设置进入退出动画效果
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        mParams.gravity = Gravity.LEFT|Gravity.TOP;
        mParams.y=0;
        mParams.x=0;
    }

    /**
     * 未显示，直接显示
     * 显示，remove前面的，加入现在的
     */
    public void show(){
        if(!mIsShow){//如果Toast没有显示，则开始加载显示
            mIsShow = true;
            mNextView=mToastView;
            handlerTouch(mNextView);
            mWdm.addView(mNextView, mParams);//将其加载到windowManager上
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mWdm.removeView(mToastView);
                    mIsShow = false;
                }
            },2000);
        /*    mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mWdm.removeView(mToastView);
                    mIsShow = false;
                }
            }, (long)(mShowTime ? 3500 : 2000));*/
        }else{
            mIsShow = true;
            TextView tv_nextTip = (TextView) mNextView.findViewById(R.id.tv_tips);
            TextView tv_tip = (TextView) mToastView.findViewById(R.id.tv_tips);
            if(tv_nextTip.equals(tv_tip)){
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWdm.removeView(mToastView);
                        mIsShow = false;
                    }
                },2000);
            }else{
                mWdm.removeViewImmediate(mNextView);
                mNextView=mToastView;
                handlerTouch(mNextView);
                mWdm.addView(mNextView, mParams);//将其加载到windowManager上
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWdm.removeView(mToastView);
                        mIsShow = false;
                    }
                },2000);
            }


        }

    }
    private void handlerTouch(final View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            float  x0=0;
            float y0=0;
            float x1=0;
            float y1=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x0=event.getX();
                        y0=event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x1=event.getX();
                        y1=event.getY();
                        if((y1-y0<0)&&(Math.abs(y1-y0)>Math.abs(x1-x0))&&mIsShow){
                            handler.removeCallbacksAndMessages(null);
                            mWdm.removeView(view);
                            mIsShow = false;
                        }
                        break;
                }
                return false;
            }
        });
    }

}
