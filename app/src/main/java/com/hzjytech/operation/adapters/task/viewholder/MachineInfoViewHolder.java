package com.hzjytech.operation.adapters.task.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.DetailTaskInfo;
import com.hzjytech.operation.entity.MachineInfo;
import com.hzjytech.operation.inter.OnSelectClickListener;
import com.hzjytech.operation.utils.GPSUtil;
import com.hzjytech.operation.widgets.dialogs.SelectDialog;
import com.hzjytech.operation.widgets.dialogs.TipDingDingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/6/19.
 */
public class MachineInfoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_machine_code)
    TextView mTvMachineCode;
    @BindView(R.id.tv_machine_group)
    TextView mTvMachineGroup;
    @BindView(R.id.tv_machine_menu)
    TextView mTvMachineMenu;
    @BindView(R.id.tv_machine_version)
    TextView mTvMachineVersion;
    @BindView(R.id.tv_machine_location)
    TextView mTvMachineLocation;
    @BindView(R.id.iv_guide)
    ImageView mIvGuide;

    public MachineInfoViewHolder(
            View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setData(final Context context, final DetailTaskInfo.VendingMachineBeanDO data) {
        if(data==null){
            return;
        }
        mTvMachineCode.setText("咖啡机编号："+data.getName());
        mTvMachineGroup.setText("促销分组："+(data.getGroupName()==null?"":data.getGroupName()));
        mTvMachineMenu.setText("菜单："+(data.getMenuName()==null?"":data.getMenuName()));
        mTvMachineVersion.setText("当前版本："+data.getVersion());
        mTvMachineLocation.setText(data.getAddress());
        mIvGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SelectDialog selectDialog = SelectDialog.newInstance();
                selectDialog.setOnSelectClickListener(new OnSelectClickListener() {
                    @Override
                    public void select(int positon) {
                        if(positon==2){
                            selectDialog.dismiss();
                        }else{
                            selectMap(context,positon,data);
                        }
                    }
                });
                selectDialog.show(((BaseActivity)context).getSupportFragmentManager(),"select");
            }
        });
    }

    /**
     * 0、高德地图
     * 1、百度地图
     * @param context
     * @param positon
     * @param data
     */
    private void selectMap(Context context, int positon, DetailTaskInfo.VendingMachineBeanDO data) {
        if(positon==0){
            double[] gcjs = GPSUtil.bd09_To_Gcj02(Double.valueOf(data.getLatitude()),
                    Double.valueOf(data.getLongitude()));
            try {

                Intent i1 = new Intent();
                // 步行路线规划

                i1.setAction(Intent.ACTION_VIEW);
                i1.addCategory(Intent.CATEGORY_DEFAULT);
                i1.setData(Uri.parse("amapuri://route/plan/?dlat="+gcjs[0]+"&dlon="+gcjs[1]+"&dname="+data.getName()+"&dev=0&t=2"));
                context.startActivity(i1);
            } catch (Exception e) {
          /*  String url = "http://api.map.baidu.com/direction?origin=name:我的位置&destination="+"latlng:"+latitude+","+
                    longitude+"&mode=driving&region=杭州&output=html"+"&src=operation";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent .setData(Uri.parse(url));
            context.startActivity(intent);*/
                TipDingDingDialog tipDingDingDialog = new TipDingDingDialog();
                tipDingDingDialog.setText(context.getResources().getString(R.string.please_install_gaodemap));
                tipDingDingDialog.show(((BaseActivity)context).getSupportFragmentManager(),"dingding");

            }
            //调起百度地图导航
        }else if(positon==1){
            try {
                Intent i1 = new Intent();
                // 步行路线规划


                i1.setData(Uri.parse(
                        "baidumap://map/direction?destination="+data.getLatitude()+","+data.getLongitude()+"&mode=driving"));

                context.startActivity(i1);
            } catch (Exception e) {
          /*  String url = "http://api.map.baidu.com/direction?origin=name:我的位置&destination="+"latlng:"+latitude+","+
                    longitude+"&mode=driving&region=杭州&output=html"+"&src=operation";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent .setData(Uri.parse(url));
            context.startActivity(intent);*/
                TipDingDingDialog tipDingDingDialog = new TipDingDingDialog();
                tipDingDingDialog.setText(context.getResources().getString(R.string.please_install_baidumap));
                tipDingDingDialog.show(((BaseActivity)context).getSupportFragmentManager(),"dingding");

            }
            //调起百度地图导航
        }
    }
    private void startBaidu(Context context, String latitude, String longitude) {
        try {
            Intent i1 = new Intent();
            // 步行路线规划

            i1.setData(Uri.parse(
                    "baidumap://map/direction?destination="+latitude+","+longitude+"&mode=driving"));

            context.startActivity(i1);
        } catch (Exception e) {
          /*  String url = "http://api.map.baidu.com/direction?origin=name:我的位置&destination="+"latlng:"+latitude+","+
                    longitude+"&mode=driving&region=杭州&output=html"+"&src=operation";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent .setData(Uri.parse(url));
            context.startActivity(intent);*/
            TipDingDingDialog tipDingDingDialog = new TipDingDingDialog();
            tipDingDingDialog.setText(context.getResources().getString(R.string.please_install_baidumap));
            tipDingDingDialog.show(((BaseActivity)context).getSupportFragmentManager(),"dingding");

        }
        //调起百度地图导航
    }

}
