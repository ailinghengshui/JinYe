package com.hzjytech.operation.adapters.scan;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.google.zxing.common.detector.MathUtils;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.RecyclerViewAdapter;
import com.hzjytech.operation.adapters.data.viewholders.RecyclerViewHolder;
import com.hzjytech.operation.entity.MaterialInfo.InventoriesBean;
import com.hzjytech.operation.entity.StickyHeadEntity;
import com.hzjytech.operation.inter.OnTwoButtonClickListener;
import com.hzjytech.operation.utils.MyMath;

/**
 * Created by hehongcan on 2017/8/24.
 */

public class DosingAdapter extends RecyclerViewAdapter<InventoriesBean,StickyHeadEntity<InventoriesBean>> {


    private OnTwoButtonClickListener mOnTwoButtonClickListener;
    public DosingAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        switch (viewType){
            case RecyclerViewAdapter.TYPE_STICKY_HEAD:
                return R.layout.item_sign_head;
            case RecyclerViewAdapter.TYPE_DATA:
                return R.layout.item_dosing_data;
            case RecyclerViewAdapter.TYPE_FOOTER:
                return R.layout.item_dosing_button;
        }
        return 0;
    }

    @Override
    public void bindData(
            RecyclerViewHolder holder, int viewType, int position, InventoriesBean item) {
           switch (viewType){
               case RecyclerViewAdapter.TYPE_STICKY_HEAD:
                   holder.setText(R.id.tv_head,item.getMaterial_name());
                   break;
               case RecyclerViewAdapter.TYPE_DATA:
                   holder.setText(R.id.tv_dosing_name,item.getMaterial_name());
                   if(item.getMaterial_name().equals("杯子")){
                       holder.setText(R.id.tv_dosing_value, MyMath.getIntOrDouble(item.getRemain())+"个");
                   }else if(item.getMaterial_name().equals("水")){
                       holder.setText(R.id.tv_dosing_value,MyMath.getIntOrDouble(item.getRemain())+"ml");
                   }else{
                       holder.setText(R.id.tv_dosing_value,MyMath.getIntOrDouble(item.getRemain())+"g");
                   }
                   break;
               case RecyclerViewAdapter.TYPE_FOOTER:
                   Button bt_add_material = holder.getButton(R.id.bt_add_materail);
                   Button bt_calibration_materail = holder.getButton(R.id.bt_calibration_materail);
                   bt_add_material.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(mOnTwoButtonClickListener!=null){
                               mOnTwoButtonClickListener.leftClick();
                           }
                       }
                   });
                   bt_calibration_materail.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(mOnTwoButtonClickListener!=null){
                               mOnTwoButtonClickListener.rightClick();
                           }
                       }
                   });
                   break;
           }
    }
    public void setOnTwoButtonClickListener(OnTwoButtonClickListener onTwoButtonClickListener) {
        mOnTwoButtonClickListener = onTwoButtonClickListener;
    }

}
