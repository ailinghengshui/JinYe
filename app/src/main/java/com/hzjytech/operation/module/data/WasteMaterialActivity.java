package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.entity.WasteMaterialInfo;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.MyMath;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import com.hzjytech.operation.entity.WasteMaterialInfo.MaterialsBean;

import butterknife.BindView;
import rx.Observable;

/**
 * Created by hehongcan on 2017/5/24.
 */
public class WasteMaterialActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.ll_waste_material_container)
    LinearLayout mContainer;


    @Override
    protected int getResId() {
        return R.layout.activity_waste_material;
    }

    @Override
    protected void initView() {
        initTitle();
        initData();
    }

    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getIntent();
        long startTime = intent.getLongExtra("startTime", 0);
        long endTime = intent.getLongExtra("endTime", 0);
        final ArrayList<Integer> machinesId = intent.getIntegerArrayListExtra("machinesId");
        Observable<WasteMaterialInfo> observable = DataApi.getWasteMaterial(userInfo
                        .getToken(),
                startTime,
                endTime,
                0,
                machinesId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<WasteMaterialInfo>() {
                    @Override
                    public void onNext(WasteMaterialInfo wasteMaterialInfo) {
                        resolveData(wasteMaterialInfo.getMaterials());
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    /**
     * 处理返回数据
     *
     * @param list
     */
    private void resolveData(List<MaterialsBean> list) {
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < list.size() - 1; i++) {
            View view = inflater.inflate(R.layout.item_other_waste_material, null, false);
            mContainer.addView(view);
        }
        for (int i = 1; i < list.size(); i++) {
            MaterialsBean info = list.get(i - 1);
            View view = mContainer.getChildAt(i);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_material_name);
            TextView tv_normal_count = (TextView) view.findViewById(R.id.tv_normal_count);
            TextView tv_extra_count = (TextView) view.findViewById(R.id.tv_extra_count);
            TextView tv_totle_count = (TextView) view.findViewById(R.id.tv_totle_count);
            tv_name.setText(info.getName());
            if(info.getName().equals("杯子")){
                tv_extra_count.setText(MyMath.getIntOrDouble(info.getExtraConsume())+"个");
                tv_normal_count.setText(MyMath.getIntOrDouble(info.getConsume())+"个");
                tv_totle_count.setText(MyMath.getIntOrDouble(info.getAllConsume())+"个");
            }else{
                tv_extra_count.setText(MyMath.getIntOrDouble(info.getExtraConsume())+"克");
                tv_normal_count.setText(MyMath.getIntOrDouble(info.getConsume())+"克");
                tv_totle_count.setText(MyMath.getIntOrDouble(info.getAllConsume())+"克");
            }

        }
        View lastView = inflater.inflate(R.layout.item_last_waste_material, null, false);
        mContainer.addView(lastView);
        View view = mContainer.getChildAt(mContainer.getChildCount() - 1);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_last_name);
        TextView tv_normal_count = (TextView) view.findViewById(R.id.tv_last_normal_count);
        TextView tv_extra_count = (TextView) view.findViewById(R.id.tv_last_extra_count);
        TextView tv_totle_count = (TextView) view.findViewById(R.id.tv_last_totle_count);
        MaterialsBean lastInfo = list.get(list.size() - 1);
      tv_name.setText(lastInfo.getName());
        if(lastInfo.getName().equals("杯子")){
            tv_normal_count.setText(MyMath.getIntOrDouble(lastInfo.getConsume())+"个");
            tv_extra_count.setText(MyMath.getIntOrDouble(lastInfo.getExtraConsume())+"个");
            tv_totle_count.setText(MyMath.getIntOrDouble(lastInfo.getAllConsume())+"个");
        }else{
            tv_normal_count.setText(MyMath.getIntOrDouble(lastInfo.getConsume())+"克");
            tv_extra_count.setText(MyMath.getIntOrDouble(lastInfo.getExtraConsume())+"克");
            tv_totle_count.setText(MyMath.getIntOrDouble(lastInfo.getAllConsume())+"克");
        }
        //this.getWindow().getDecorView().invalidate();
    }





    private void initTitle() {
        titleBar.setTitle(R.string.waste_material);
        titleBar.setTitleBold(true);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
