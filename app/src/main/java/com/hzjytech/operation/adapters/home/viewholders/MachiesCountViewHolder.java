package com.hzjytech.operation.adapters.home.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hzjytech.operation.R;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.Machies;
import com.hzjytech.operation.inter.MachineStateClickListener;
import com.hzjytech.operation.utils.DensityUtil;
import com.hzjytech.operation.widgets.BadgeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hehongcan on 2017/4/24.
 */
public class MachiesCountViewHolder extends RecyclerView.ViewHolder {
    private MachineStateClickListener machineStateChangeLister;
    @BindView(R.id.item_machines_error)
    LinearLayout itemMachinesError;
    @BindView(R.id.item_machines_shortage)
    LinearLayout itemMachinesShortage;
    @BindView(R.id.item_machines_offline)
    LinearLayout itemMachinesOffline;
    @BindView(R.id.item_machines_lock)
    LinearLayout itemMachinesLock;
    @BindView(R.id.item_machines_unoperation)
    LinearLayout itemMachinesUnoperation;
    @BindView(R.id.iv_error)
    ImageView ivError;
    @BindView(R.id.iv_shortage)
    ImageView ivShortage;
    @BindView(R.id.iv_offline)
    ImageView ivOffline;
    @BindView(R.id.iv_lock)
    ImageView ivLock;
    @BindView(R.id.iv_unoperation)
    ImageView ivUnoperation;
    private BadgeView mErrorBadgeView;
    private BadgeView mLockBadgeView;
    private BadgeView mOfflineBadgeView;
    private BadgeView mShortageBadgeView;
    private BadgeView mUnoperationBadageView;

    public MachiesCountViewHolder(View machiesCountView, MachineStateClickListener machineStateChangeLister) {
        super(machiesCountView);
        ButterKnife.bind(this, machiesCountView);
        this.machineStateChangeLister = machineStateChangeLister;
    }

    public void setViewData(Context mContext, Object item, int position, int viewType) {
        int x = DensityUtil.dp2px(mContext, -7);
        int y = DensityUtil.dp2px(mContext, 8);
        if(mErrorBadgeView==null){
            mErrorBadgeView = new BadgeView(mContext, ivError);
            mErrorBadgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            mErrorBadgeView.setBadgeBackgroundColor(mContext.getResources().getColor(R.color.light_red));
            mErrorBadgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13); //22SP
            mErrorBadgeView.setTranslationX(x);
            mErrorBadgeView.setTranslationY(y);
        }
        if(mLockBadgeView==null){
            mLockBadgeView = new BadgeView(mContext, ivLock);
            mLockBadgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            mLockBadgeView.setBadgeBackgroundColor(mContext.getResources().getColor(R.color.heavy_green));
            mLockBadgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
            mLockBadgeView.setTranslationX(x);
            mLockBadgeView.setTranslationY(y);
        }
        if(mOfflineBadgeView==null){
            mOfflineBadgeView = new BadgeView(mContext, ivOffline);
            mOfflineBadgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            mOfflineBadgeView.setBadgeBackgroundColor(mContext.getResources().getColor(R.color.light_green));
            mOfflineBadgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
            mOfflineBadgeView.setTranslationX(x);
            mOfflineBadgeView.setTranslationY(y);
        }
        if(mShortageBadgeView==null){
            mShortageBadgeView = new BadgeView(mContext, ivShortage);
            mShortageBadgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            mShortageBadgeView.setBadgeBackgroundColor(mContext.getResources().getColor(R.color.light_orange));
            mShortageBadgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
            mShortageBadgeView.setTranslationX(x);
            mShortageBadgeView.setTranslationY(y);
        }
         if(mUnoperationBadageView==null){
             mUnoperationBadageView = new BadgeView(mContext, ivUnoperation);
             mUnoperationBadageView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
             mUnoperationBadageView.setBadgeBackgroundColor(mContext.getResources().getColor(R.color.badage_blue));
             mUnoperationBadageView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
             mUnoperationBadageView.setTranslationX(x);
             mUnoperationBadageView.setTranslationY(y);
         }
        Machies machies = (Machies) item;
        int sick = machies.getSick();
        if (sick > 0) {
            mErrorBadgeView.setText(sick + "");
            mErrorBadgeView.show();
        } else {
            mErrorBadgeView.hide();
        }
        int locked = machies.getLocked();
        if (locked > 0) {
            mLockBadgeView.setText(locked + "");
            mLockBadgeView.show();
        } else {
            mLockBadgeView.hide();
        }
        int offline = machies.getOffline();
        if (offline > 0) {
            mOfflineBadgeView.setText(offline + "");
            mOfflineBadgeView.show();
        } else {
            mOfflineBadgeView.hide();
        }
        int lack = machies.getLack();
        if (lack > 0) {
            mShortageBadgeView.setText(lack + "");
            mShortageBadgeView.show();
        } else {
            mShortageBadgeView.hide();
        }
        int offOperation = machies.getOffOperation();
        if (offOperation > 0) {
            mUnoperationBadageView.setText(offOperation + "");
            mUnoperationBadageView.show();
        } else {
            mUnoperationBadageView.hide();
        }
    }

    @OnClick({R.id.item_machines_error, R.id.item_machines_shortage, R.id.item_machines_offline, R.id.item_machines_lock, R.id.item_machines_unoperation})
    public void onClick(View view) {
        if(machineStateChangeLister==null){
            return;
        }
        switch (view.getId()) {
            case R.id.item_machines_error:
                machineStateChangeLister.state(Constants.state_error);
                break;
            case R.id.item_machines_shortage:
                machineStateChangeLister.state(Constants.state_lack);
                break;
            case R.id.item_machines_offline:
                machineStateChangeLister.state(Constants.state_offline);
                break;
            case R.id.item_machines_lock:
                machineStateChangeLister.state(Constants.state_lock);
                break;
            case R.id.item_machines_unoperation:
                machineStateChangeLister.state(Constants.state_unoperation);
                break;
        }
    }
}
