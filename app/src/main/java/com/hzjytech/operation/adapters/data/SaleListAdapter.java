package com.hzjytech.operation.adapters.data;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.RecyclerViewAdapter;
import com.hzjytech.operation.adapters.data.viewholders.HeadViewHolder;
import com.hzjytech.operation.adapters.data.viewholders.RecyclerViewHolder;
import com.hzjytech.operation.adapters.data.viewholders.SaleListViewHolder;
import com.hzjytech.operation.entity.CurrentDataInfo;
import com.hzjytech.operation.entity.StickyHeadEntity;
import com.hzjytech.operation.entity.TenBean;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by hehongcan on 2017/5/16.
 */
public class SaleListAdapter extends RecyclerViewAdapter<TenBean,StickyHeadEntity<TenBean>>{
    private  int mRed;
    private  int mGreen;

    public SaleListAdapter(Context context) {
        super(context);
        mGreen = ContextCompat.getColor(context, R.color.color_green);
        mRed = ContextCompat.getColor(context, R.color.color_red);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        switch (viewType){
            case TYPE_STICKY_HEAD:
                return R.layout.item_salelist_top_head;
            case TYPE_DATA:
                return R.layout.item_salelist;
        }
        return 0;
    }

    @Override
    public void bindData(
            RecyclerViewHolder holder, int viewType, int position, TenBean item) {
        int type = holder.getItemViewType();
        switch (type){
            case TYPE_STICKY_HEAD:
                ImageView imageView = holder.getImageView(R.id.iv_saleList_isTenTop);
                if (holder.getLayoutPosition()==0){
                    imageView.setBackgroundColor(mRed);
                }else{
                    imageView.setBackgroundColor(mGreen);
                }
                holder.setText(R.id.tv_isTenTop,item.getStickyHeadName());
                break;
            case TYPE_DATA:
                setViewData(holder,item);
                break;
        }
    }

    private void setViewData(RecyclerViewHolder holder, TenBean item) {
        DecimalFormat df = new DecimalFormat("0.00");
        holder.setText(R.id.tv_salelist_count,"销售量："+item.getNum()+"杯");
        holder.setText(R.id.tv_salelist_money,"销售总额："+df.format(item.getSum())+"元");
        int position = holder.getLayoutPosition();
        TextView moneyText = holder.getTextView(R.id.tv_salelist_money);
        TextView countText = holder.getTextView(R.id.tv_salelist_count);
        holder.setText(R.id.tv_salelist_order_adrress,item.getLocation());
        holder.setText(R.id.tv_saleList_machine_group,item.getGroupName()==null?"":"·"+item.getGroupName());
        if(position<11){
            holder.setText(R.id.tv_saleList_machine_code,position+"."+item.getMachineName());
            moneyText.setTextColor(mRed);
            countText.setTextColor(mRed);
        }else{
            holder.setText(R.id.tv_saleList_machine_code,(position-11)+"."+item.getMachineName());
            moneyText.setTextColor(mGreen);
            countText.setTextColor(mGreen);
        }
    }

}
