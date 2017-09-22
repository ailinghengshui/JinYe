package com.hzjytech.operation.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hzjytech.operation.R;
import com.hzjytech.operation.module.scan.MenuControlActivity;
import com.hzjytech.operation.utils.MyToastUtil;
import com.hzjytech.operation.utils.TipUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hehongcan on 2017/4/6.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private InputMethodManager manager;
    private Unbinder bind;
    private TipUtil tipUtil;
    public ProgressDialog mProgressDlg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setContentView(getResId());
        mProgressDlg = new ProgressDialog(this, R.style.MyDialogStyle);
        mProgressDlg.setCancelable(false);
        bind = ButterKnife.bind(this);
        tipUtil = new TipUtil(this);
        initView();

    }
    /**
     * 点击空白区域收起键盘
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
 public void showSoftKeyboard(){
     InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
     imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
 }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideSoftKeyboard();
        bind.unbind();
    }

    @Override
    public void onBackPressed() {
        hideSoftKeyboard();
        super.onBackPressed();
    }

    /**
     * 显示顶部自定义Toast
     * @param tip
     */
    public void showTip(String tip){
        MyToastUtil.show(this,tip);
    }
    public void showTip(int resId){
        MyToastUtil.show(this,getResources().getString(resId));
    }
    public void hideSoftKeyboard() {
        View focusView = getCurrentFocus();
        if (null != focusView && null != focusView.getWindowToken()) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 设置布局文件
     * @return
     */
    @LayoutRes
   protected abstract int getResId();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 点击非edittext其他区域隐藏键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
