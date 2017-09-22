package com.hzjytech.operation.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.hzjytech.operation.R;
import com.hzjytech.operation.utils.MyToastUtil;
import com.hzjytech.operation.utils.TipUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hehongcan on 2017/4/18.
 */
public abstract  class BaseFragment extends Fragment {

    private Unbinder bind;
    private TipUtil tipUtil;
    public ProgressDialog mProgressDlg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getResId(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bind = ButterKnife.bind(this,view);
        tipUtil = new TipUtil(getActivity());
        mProgressDlg = new ProgressDialog(getActivity(), R.style.MyDialogStyle);
        mProgressDlg.setCancelable(false);
        initView();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 自定义下滑提示框
     * @param tip
     */
    public void showTip(String tip){
        MyToastUtil.show(getActivity(),tip);
    }
    public void showTip(int resId){
        MyToastUtil.show(getActivity(),getResources().getString(resId));
    }
    @LayoutRes
    protected abstract int getResId();

    /**
     * 初始化界面
     */
    protected abstract void initView();
    /**
     * 设置页面的透明度
     * @param bgAlpha 1表示不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }
}
