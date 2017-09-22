package com.hzjytech.operation.adapters.home.viewholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.MachineInfo;
import com.hzjytech.operation.inter.OnInfoClickListener;
import com.hzjytech.operation.inter.OnSbCheckChangeListener;
import com.hzjytech.operation.inter.OnSelectClickListener;
import com.hzjytech.operation.module.machine.DetailInfoActivity;
import com.hzjytech.operation.utils.GPSUtil;
import com.hzjytech.operation.widgets.dialogs.SelectDialog;
import com.hzjytech.operation.widgets.dialogs.TipDingDingDialog;
import com.hzjytech.operation.widgets.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailMessageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.version)
    TextView mVersion;
    @BindView(R.id.tv_machine_group)
    TextView mTvMachineGroup;
    @BindView(R.id.tv_machine_menu)
    TextView mTvMachineMenu;
    @BindView(R.id.sb_status)
    SwitchButton mSbStatus;
    @BindView(R.id.sb_lock)
    SwitchButton mSbLock;
    @BindView(R.id.sb_close)
    SwitchButton mSbClose;
    @BindView(R.id.sb_coffeeme)
    SwitchButton mSbCoffeeme;
    @BindView(R.id.tv_machine_location)
    TextView mTvMachineLocation;
    @BindView(R.id.tv_operation_time)
    TextView mTvOperationTime;
    @BindView(R.id.tv_default_drink)
    TextView mTvDefalut_drink;
    @BindView(R.id.iv_machine_group)
    ImageView mIvMachineGroup;
    @BindView(R.id.iv_machine_menu)
    ImageView mIvMachineMenu;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.iv_operation_time)
    ImageView mIvSetTime;
    @BindView(R.id.tv_lock)
    TextView mTvLock;
    @BindView(R.id.tv_close)
    TextView mTvClose;
    @BindView(R.id.iv_default_drink)
    ImageView mIvDefaultDrink;
    @BindView(R.id.tv_coffeeme)
    TextView mTvCoffeeMe;
    @BindView(R.id.tv_add_role)
    TextView mTvAddRole;
    @BindView(R.id.iv_add_role)
    ImageView mIvAddRole;
    @BindView(R.id.rl_machine_group)
    RelativeLayout mRlMachineGroup;
    @BindView(R.id.rl_machine_menu)
    RelativeLayout mRlMachineMenu;
    @BindView(R.id.rl_time_set)
    RelativeLayout mRlTimeSet;
    @BindView(R.id.rl_drink_type)
    RelativeLayout mRlDrinkType;
    @BindView(R.id.rl_add_role)
    RelativeLayout mRlAddRole;


    private Context context;
    private MachineInfo.BasicInfoBean basicInfo;
    private OnSbCheckChangeListener mOnSbCheckChangeListener;
    private OnInfoClickListener mOnInfoClickListener;

    public DetailMessageViewHolder(View itemView) {
        super(itemView);
        //R.layout.item_detail_message
        ButterKnife.bind(this, itemView);

    }



    public void setMessageData(
            final Context context,
            MachineInfo machineInfo,
            OnInfoClickListener onInfoClickListener,
            OnSbCheckChangeListener onSbCheckChangeListener,
            boolean basicInfoAuth,
            boolean staffInfoAuth) {
        this.context = context;
        this.mOnSbCheckChangeListener=onSbCheckChangeListener;
        this.mOnInfoClickListener=onInfoClickListener;
        basicInfo = machineInfo.getBasicInfo();
        mVersion.setText(basicInfo.getVersion() == null ? "" : basicInfo.getVersion());
        mTvMachineGroup.setText(basicInfo.getGroupName());
        //基础信息修改权限设置
        if(basicInfoAuth){
           mIvMachineGroup.setVisibility(View.VISIBLE);
            mRlMachineGroup.setEnabled(true);
            mIvMachineMenu.setVisibility(View.VISIBLE);
            mRlMachineMenu.setEnabled(true);
            mSbStatus.setVisibility(View.VISIBLE);
            mTvStatus.setVisibility(View.GONE);
            mRlTimeSet.setEnabled(true);
            mIvSetTime.setVisibility(View.VISIBLE);
            mSbLock.setVisibility(View.VISIBLE);
            mTvLock.setVisibility(View.GONE);
            mSbClose.setVisibility(View.VISIBLE);
            mTvClose.setVisibility(View.GONE);
            mRlDrinkType.setEnabled(true);
            mIvDefaultDrink.setVisibility(View.VISIBLE);
            mSbCoffeeme.setVisibility(View.VISIBLE);
            mTvCoffeeMe.setVisibility(View.GONE);
        }else{
           mIvMachineGroup.setVisibility(View.GONE);
            mRlMachineGroup.setEnabled(false);
            mIvMachineMenu.setVisibility(View.GONE);
            mRlMachineMenu.setEnabled(false);
            mSbStatus.setVisibility(View.GONE);
            mTvStatus.setVisibility(View.VISIBLE);
            mTvStatus.setText(basicInfo.isOperationStatus()?"是":"否");
            mRlTimeSet.setEnabled(false);
            mIvSetTime.setVisibility(View.GONE);
            mSbLock.setVisibility(View.GONE);
            mTvLock.setVisibility(View.VISIBLE);
            mTvLock.setText(basicInfo.isLocked()?"是":"否");
            mSbClose.setVisibility(View.GONE);
            mTvClose.setVisibility(View.VISIBLE);
            mTvClose.setText(basicInfo.isClose()?"是":"否");
            mRlDrinkType.setEnabled(false);
            mIvDefaultDrink.setVisibility(View.GONE);
            mSbCoffeeme.setVisibility(View.GONE);
            mTvCoffeeMe.setVisibility(View.VISIBLE);
            mTvCoffeeMe.setText(basicInfo.isSupportCoffeeMe()?"是":"否");

        }

        if(staffInfoAuth){
            mRlAddRole.setEnabled(true);
            mTvAddRole.setVisibility(View.VISIBLE);
            mIvAddRole.setVisibility(View.VISIBLE);
        }else{
            mRlAddRole.setEnabled(false);
            mTvAddRole.setVisibility(View.GONE);
            mIvAddRole.setVisibility(View.GONE);
        }
        mTvMachineMenu.setText(basicInfo.getMenuName() == null ? "" : basicInfo.getMenuName());
        mSbStatus.setChecked(basicInfo.isOperationStatus());
        mSbLock.setChecked(basicInfo .isLocked());
        mSbClose.setChecked(basicInfo.isClose());
        mSbCoffeeme.setChecked(basicInfo.isSupportCoffeeMe());
        mTvMachineLocation.setText(basicInfo.getAddress());
        mTvDefalut_drink.setText(basicInfo.getDefaultSet()==0?"冷饮":"热饮");
        if (basicInfo.getOpeningTime() == null || basicInfo.getClosingTime() == null) {
            mTvOperationTime.setText("");
        } else {
            if(basicInfo.getOpeningTime().equals("00:00")&&basicInfo.getClosingTime().equals("23:59")){
                mTvOperationTime.setText("全天候");
            }else{
                mTvOperationTime.setText(basicInfo.getOpeningTime() + "-" + basicInfo
                        .getClosingTime());
            }
        }
        mSbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOnSbCheckChangeListener!=null){
                    mOnSbCheckChangeListener.check(Constants.InfoSb.STATUS,isChecked);
                }
            }
        });
        mSbLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOnSbCheckChangeListener!=null){
                    mOnSbCheckChangeListener.check(Constants.InfoSb.LOCK,isChecked);
                }
            }
        });
        mSbClose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOnSbCheckChangeListener!=null){
                    mOnSbCheckChangeListener.check(Constants.InfoSb.CLOSE,isChecked);
                }
            }
        });
        mSbCoffeeme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mOnSbCheckChangeListener!=null){
                    mOnSbCheckChangeListener.check(Constants.InfoSb.COFFEEME,isChecked);
                }
            }
        });

    }
    /**
     * 初始化各种点击事件
     */

    @OnClick({R.id.rl_more_info, R.id.rl_machine_group, R.id.rl_machine_menu, R.id.sb_status,
            R.id.rl_time_set, R.id.sb_lock, R.id.sb_close, R.id.rl_drink_type, R.id
            .sb_coffeeme, R.id.iv_machine_location, R.id.rl_add_role})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_more_info:
                Intent intent = new Intent(context,DetailInfoActivity.class);
                intent.putExtra("info",basicInfo);
                context.startActivity(intent);
                break;
            case R.id.rl_machine_group:
                if(mOnInfoClickListener!=null){
                    mOnInfoClickListener.click(Constants.InfoClick.GROUP);
                }
                break;
            case R.id.rl_machine_menu:
                if(mOnInfoClickListener!=null){
                    mOnInfoClickListener.click(Constants.InfoClick.MENU);
                }
                break;
            case R.id.sb_status:
                break;
            case R.id.rl_time_set:
                if(mOnInfoClickListener!=null){
                    mOnInfoClickListener.click(Constants.InfoClick.TIMESET);
                }
                break;
            case R.id.sb_lock:
                break;
            case R.id.sb_close:
                break;
            case R.id.rl_drink_type:
                if(mOnInfoClickListener!=null){
                    mOnInfoClickListener.click(Constants.InfoClick.DRINKTYPE);
                }
                break;
            case R.id.sb_coffeeme:
                break;
            case R.id.iv_machine_location:
                final SelectDialog selectDialog = SelectDialog.newInstance();
                selectDialog.setOnSelectClickListener(new OnSelectClickListener() {
                    @Override
                    public void select(int positon) {
                        if (positon == 2) {
                            selectDialog.dismiss();
                        } else {
                            selectMap(context, positon, basicInfo);
                        }
                    }
                });
                selectDialog.show(((BaseActivity) context).getSupportFragmentManager(),
                        "select");
                break;
            case R.id.rl_add_role:
                if(mOnInfoClickListener!=null){
                    mOnInfoClickListener.click(Constants.InfoClick.ROLE);
                }
                break;
        }
    }

    /**
     * 0、高德地图
     * 1、百度地图
     *
     * @param context
     * @param positon
     * @param basicInfo
     */
    private void selectMap(Context context, int positon, MachineInfo.BasicInfoBean basicInfo) {
        if (positon == 0) {
            double[] gcjs = GPSUtil.bd09_To_Gcj02(Double.valueOf(basicInfo.getLatitude()),
                    Double.valueOf(basicInfo.getLongitude()));
            try {
                Intent i1 = new Intent();
                // 步行路线规划

                i1.setAction(Intent.ACTION_VIEW);
                i1.addCategory(Intent.CATEGORY_DEFAULT);
                i1.setData(Uri.parse
                        ("androidamap://viewMap?sourceApplication=appname&poiname=" +
                                basicInfo.getName() + "&lat=" + gcjs[0] + "&lon=" + gcjs[1] + "&dev=0"));
                context.startActivity(i1);
            } catch (Exception e) {

                TipDingDingDialog tipDingDingDialog = new TipDingDingDialog();
                tipDingDingDialog.setText(context.getResources()
                        .getString(R.string.please_install_gaodemap));
                tipDingDingDialog.show(((BaseActivity) context).getSupportFragmentManager(),
                        "dingding");

            }
            //调起百度地图导航
        } else if (positon == 1) {
            try {
                Intent i1 = new Intent();
                // 步行路线规划


                i1.setData(Uri.parse("baidumap://map/marker?location=" + basicInfo
                        .getLatitude() + "," + basicInfo.getLongitude() + "&title=" +
                        basicInfo.getName() + "&traffic=off"));

                context.startActivity(i1);
            } catch (Exception e) {
                TipDingDingDialog tipDingDingDialog = new TipDingDingDialog();
                tipDingDingDialog.setText(context.getResources()
                        .getString(R.string.please_install_baidumap));
                tipDingDingDialog.show(((BaseActivity) context).getSupportFragmentManager(),
                        "dingding");

            }
            //调起百度地图导航
        }
    }


}