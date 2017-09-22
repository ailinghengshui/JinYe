package com.hzjytech.operation.adapters.group.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.inter.OnInfoClickListener;
import com.hzjytech.operation.utils.MyMath;
import com.hzjytech.operation.utils.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hzjytech.operation.constants.Constants.GroupClick.MONNEYDISCONUT;
import static com.hzjytech.operation.constants.Constants.GroupClick.MONNEYOFF;
import static com.hzjytech.operation.constants.Constants.GroupClick.PROMOTIONTEXT;

/**
 * Created by hehongcan on 2017/7/18.
 */
public class GroupSettingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ll_setting_promotion_info)
    LinearLayout mLlSettingPromotionInfo;
    @BindView(R.id.tv_settting_promotion_info)
    TextView mTvSetttingPromotionInfo;
    @BindView(R.id.ll_setting_money_off)
    LinearLayout mLlSettingMoneyOff;
    @BindView(R.id.tv_money_off)
    TextView mTvMoneyOff;
    @BindView(R.id.tv_off_time)
    TextView mTvOffTime;
    @BindView(R.id.ll_setting_money_discount)
    LinearLayout mLlSettingMoneyDiscount;
    @BindView(R.id.tv_discount)
    TextView mTvDiscount;
    @BindView(R.id.tv_discount_time)
    TextView mTvDiscountTime;

    public GroupSettingViewHolder(View view) {
        super(view);
        //R.layout.item_group_setting
        ButterKnife.bind(this, view);
    }

    public void setData(
            Context context, GroupInfo groupInfo, final OnInfoClickListener onInfoClickListener) {
        GroupInfo.BasicInfoBean info = groupInfo.getBasicInfo();
        mTvSetttingPromotionInfo.setText((groupInfo.getPromotionText()==null||groupInfo.getPromotionText().equals(""))?"无":groupInfo.getPromotionText());
        if (groupInfo.getDiscount() == 0 ) {
            mTvDiscount.setText("无");
             mTvDiscountTime.setVisibility(View.GONE);
        }else{
            mTvDiscount.setText(context.getString(R.string.discount,
                    (MyMath.getIntOrDouble(groupInfo.getDiscount() * 10) + "")));
            mTvDiscountTime.setVisibility(View.VISIBLE);
            mTvDiscountTime.setText(TimeUtil.getLong2FromLong(info.getDiscountStartTime()) + "/"
                    + TimeUtil.getLong2FromLong(
                    info.getDiscountEndTime()));

        }
        if( groupInfo.getDiscountM() == 0){
            mTvMoneyOff.setText("无");
            mTvOffTime.setVisibility(View.GONE);
        }else{
            mTvMoneyOff.setText(context.getString(R.string.money_off,
                    MyMath.getIntOrDouble(groupInfo.getDiscountM()) + "",
                    MyMath.getIntOrDouble(groupInfo.getDiscountJ()) + ""));
            mTvOffTime.setVisibility(View.VISIBLE);
            mTvOffTime.setText(TimeUtil.getLong2FromLong(info.getMoneyOfStartTime()) + "/" + TimeUtil.getLong2FromLong(
                    info.getMoneyOfEndTime()));

        }
        mLlSettingPromotionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onInfoClickListener!=null){
                    onInfoClickListener.click(PROMOTIONTEXT);
                }
            }
        });
        mLlSettingMoneyOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onInfoClickListener!=null){
                    onInfoClickListener.click(MONNEYOFF);
                }
            }
        });
        mLlSettingMoneyDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onInfoClickListener!=null){
                    onInfoClickListener.click(MONNEYDISCONUT);
                }
            }
        });

    }
}
