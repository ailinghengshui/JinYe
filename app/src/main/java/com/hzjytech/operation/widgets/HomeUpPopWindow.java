package com.hzjytech.operation.widgets;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.module.scan.MenuControlActivity;
import com.hzjytech.operation.module.task.NewTaskActivity;
import com.hzjytech.operation.utils.CameraUtil;
import com.hzjytech.operation.widgets.dialogs.HintDialog;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;


/**
 * 用于首页弹出的PopUpWindow
 * Created by hehongcan on 2017/4/25.
 */
public class HomeUpPopWindow extends PopupWindow  {

    private static final int REQUEST_CODE_CAMERA = 111;
    private  Activity activity;
    private  Context context;

    public HomeUpPopWindow(Activity activity) {
        super(activity);
        this.activity=activity;
        context = activity;
        View inflate =  LayoutInflater.from(context).inflate(R.layout.popview_item, null, false);
        setContentView(inflate);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(true);
        initView(inflate);
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);


    }
    private void initView(View inflate) {
        LinearLayout ll_scan = (LinearLayout) inflate.findViewById(R.id.ll_scan);
        LinearLayout ll_new_task = (LinearLayout) inflate.findViewById(R.id.ll_new_task);
        RelativeLayout rl_pop_bg = (RelativeLayout) inflate.findViewById(R.id.rl_pop_bg);
        ll_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });
        ll_new_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTask();
            }
        });
      /*  rl_pop_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });*/

    }

    private void newTask() {
        Intent intent = new Intent(context, NewTaskActivity.class);
        context.startActivity(intent);
        dismiss();
    }
    private void scan() {
        dismiss();
        MPermissions.requestPermissions(activity,4, Manifest.permission.CAMERA);
       /* if (!CameraUtil.isCameraCanUse()) {
            //如果没有授权，则请求授权
            dismiss();
            HintDialog hintDialog = HintDialog.newInstance("提示", "无法获取摄像头数据，请检查是否已经打开摄像头权限。", "确定");
            hintDialog.show(((BaseActivity)context).getSupportFragmentManager(),"cameraHint");

            //ToastUtil.showShort(getActivity(),"无法获取摄像头权限，请确认是否开启。");
            //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        } else {
            //有授权，直接开启摄像头
            Intent intent = new Intent(context, MenuControlActivity.class);
            context.startActivity(intent);
            dismiss();
        }*/

    }


    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = 1f;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }


}
