package com.hzjytech.operation.utils;

/**
 * Created by hehongcan on 2017/4/19.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.widgets.TipRelativeLayout;

/**
 * 封装了显示Popupwindow的方法.
 * @author ansen
 * @create time 2015-10-27
 */
public class TipUtil implements TipRelativeLayout.AnimationEndCallback {

    private View popView;
    private static Handler tipHandler=new Handler();
    private int statusBar;
    private final WindowManager.LayoutParams mParams;

    public TipUtil(Activity activity) {
        popView = LayoutInflater.from(activity).inflate(R.layout.activity_popupwindow_tips, null);
        statusBar = getStatusBarHeight(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity=Gravity.TOP;
        popView.setLayoutParams(layoutParams);
        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_TOAST);
        mParams.flags= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        popView.setVisibility(View.INVISIBLE);
        activity.addContentView(popView, mParams);
    }

    /**\
     * 相同tip。
     * 不同tip
     * 多次点击tip
     * @param tip
     */
    public void showTips(String tip){
        TextView tv_tip = (TextView) popView.findViewById(R.id.tv_tips);
        if(tv_tip.getText().toString().equals(tip)&&(popView.getTranslationY())!= 0){
            tipHandler.removeCallbacksAndMessages(null);
            tipHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator anim2 = ObjectAnimator.ofFloat(popView, "translationY", statusBar,0);
                    ObjectAnimator alpha3 = ObjectAnimator.ofFloat(popView, "alpha", 1, 0);
                    AnimatorSet animSet = new AnimatorSet();
                    animSet.play(anim2).with(alpha3);
                    animSet.setDuration(300);
                    animSet.start();
                    animSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            popView.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            },2000);
            return;
        }
        tv_tip.setText(tip);
        popView.setVisibility(View.VISIBLE);
        popView.setAlpha(1);
        final ObjectAnimator anim = ObjectAnimator.ofFloat(popView, "translationY",0, statusBar);
        anim.setDuration(300);
        anim.start();
        tipHandler.removeCallbacksAndMessages(null);
        tipHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(popView, "translationY", statusBar,0);
                ObjectAnimator alpha3 = ObjectAnimator.ofFloat(popView, "alpha", 1, 0);
                AnimatorSet animSet = new AnimatorSet();
                animSet.play(anim2).with(alpha3);
                animSet.setDuration(300);
                animSet.start();
                animSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        popView.setVisibility(View.INVISIBLE);
                    }
                });
            }
        },2000);
    }


    public  int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private  float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        float result = dpValue * scale + 0.5f;
        return result;
    }

    @Override
    public void onAnimationEnd() {
       // reportVideoPopwindow.dismiss();//动画结束，隐藏popupwindow
        popView.setVisibility(View.INVISIBLE);

    }
}