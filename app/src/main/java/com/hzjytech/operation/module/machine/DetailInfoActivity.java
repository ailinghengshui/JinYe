package com.hzjytech.operation.module.machine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.MachineInfo;
import com.hzjytech.operation.utils.MyMath;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.widgets.TitleBar;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/9/5.
 */

public class DetailInfoActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.tv_machine_brand)
    TextView mTvMachineBrand;
    @BindView(R.id.tv_machine_type)
    TextView mTvMachineType;
    @BindView(R.id.tv_alliance)
    TextView mTvAlliance;
    @BindView(R.id.tv_box_number)
    TextView mTvBoxNumber;
    @BindView(R.id.tv_cup_radius)
    TextView mTvCupRadius;
    @BindView(R.id.tv_bean_count)
    TextView mTvBeanCount;
    @BindView(R.id.tv_create_time)
    TextView mTvCreateTime;
    @BindView(R.id.tv_longtitude)
    TextView mTvLongtitude;
    @BindView(R.id.tv_latitude)
    TextView mTvLatitude;
    @BindView(R.id.tv_note)
    TextView mTvNote;
    private MachineInfo.BasicInfoBean mInfo;

    @Override
    protected int getResId() {
        return R.layout.activity_detail_info;
    }

    @Override
    protected void initView() {
        initTilteBar();
        initIntent();
    }

    private void initIntent() {
        Intent intent = getIntent();
        mInfo = intent.getParcelableExtra(
                "info");
        setViews();
    }

    /**
     * 初始化基础信息界面
     */
    private void setViews() {
        DecimalFormat df = new DecimalFormat("0.000000");
        mTvMachineBrand.setText(mInfo.getBrand());
        mTvMachineType.setText(mInfo.getTypeName() == null ? "": mInfo.getTypeName
                ());
        mTvAlliance.setText(mInfo.getFranchiseeName() == null ? "" : mInfo
                .getFranchiseeName());
        mTvBoxNumber.setText(mInfo.getMagazineNum()+"");
        mTvCupRadius.setText(MyMath.getIntOrDouble(mInfo.getCapCaliber())+"");
        mTvBeanCount.setText(MyMath.getIntOrDouble(mInfo.getBeansWeight())+"");
        mTvCreateTime.setText(TimeUtil.getShortTimeFromLong(mInfo.getBeginTime()));
        mTvLongtitude.setText(df.format(Double.valueOf(mInfo.getLongitude()))+"");
        mTvLatitude.setText( df.format(Double.valueOf(mInfo.getLatitude()))+"");
        mTvNote.setText(mInfo.getNote()==null?"":mInfo.getNote());
    }

    private void initTilteBar() {
        mTitleBar.setTitle(R.string.more_info);
        mTitleBar.setLeftImageResource(R.drawable.icon_left);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
