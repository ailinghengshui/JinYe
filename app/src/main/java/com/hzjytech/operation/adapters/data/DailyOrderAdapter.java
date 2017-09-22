package com.hzjytech.operation.adapters.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.viewholders.DailyOrderHeadViewHolder;
import com.hzjytech.operation.entity.VolumeBean;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/23.
 */
public class DailyOrderAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_NORMAL = 1;
    private  Context context;
    private  List<VolumeBean> list;
    private  LayoutInflater inflater;
    private int maxCup=0;
    private int maxOrder=0;

    public DailyOrderAdapter(Context context, List<VolumeBean> list) {
        this.context=context;
        this.list=list;
        inflater = LayoutInflater.from(context);
    }
    public void setData( List<VolumeBean> list){
        this.list=list;
        notifyDataSetChanged();
        for (VolumeBean bean : list) {
            if(bean.getCupNum()>maxCup){
                maxCup=bean.getCupNum();
            }
            if(bean.getOrderNum()>maxOrder){
                maxOrder=bean.getOrderNum();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEAD){
            View view = inflater.inflate(R.layout.item_daily_order_head,parent,false);
            return new DailyOrderHeadViewHolder(view);
        }else{
            View view = inflater.inflate(R.layout.item_daily_order, parent, false);
            return new DailyOrderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
  if(holder instanceof DailyOrderViewHolder){
      DailyOrderViewHolder orderViewHolder = (DailyOrderViewHolder) holder;
      VolumeBean volumeBean = list.get(position - 1);
      orderViewHolder.barOrder.setTwoData(volumeBean.getTime(),volumeBean.getOrderNum(),maxOrder,"单");
      orderViewHolder.barCup.setOneData(volumeBean.getCupNum(),maxCup,"杯");
  }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }else{
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return list==null?1:list.size()+1;
    }
}
