package com.hzjytech.operation.adapters.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.viewholders.SaleAmoutViewHolder;
import com.hzjytech.operation.entity.Amount;
import com.hzjytech.operation.utils.MyMath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hehongcan on 2017/5/23.
 */
public class DailyAmoutAdapter extends RecyclerView.Adapter {
    private  Context context;
    private  List<Amount> list;
    private final LayoutInflater inflater;
    private double maxSum;

    public DailyAmoutAdapter(Context context, List<Amount> list) {
        this.context =context;
        this.list=list;
        inflater = LayoutInflater.from(context);
    }
    public void setData(ArrayList<Amount> data) {
        this.list = data;
        for (Amount amount : list) {
            if(amount.getSum()>maxSum){
                maxSum=amount.getSum();
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_sale_amout, parent, false);
        return new SaleAmoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SaleAmoutViewHolder amoutViewHolder = (SaleAmoutViewHolder) holder;
        if(position!=0){
            amoutViewHolder.viewAmountHead.setVisibility(View.GONE);
            amoutViewHolder.mViewHead2.setVisibility(View.GONE);
        }else{
            amoutViewHolder.viewAmountHead.setVisibility(View.VISIBLE);
            amoutViewHolder.mViewHead2.setVisibility(View.VISIBLE);
        }
        if(position==list.size()-1){
            amoutViewHolder.mViewFoot.setVisibility(View.VISIBLE);
        }else{
            amoutViewHolder.mViewFoot.setVisibility(View.GONE);
        }
        Amount amount = list.get(position);
        amoutViewHolder.barAmount.setTwoData(amount.getTime(), (float)MyMath.round(amount.getSum(),2),(float)MyMath.round(maxSum,2),"元");
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }


}
