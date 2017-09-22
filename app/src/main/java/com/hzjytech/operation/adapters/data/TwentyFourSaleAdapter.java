package com.hzjytech.operation.adapters.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.viewholders.SaleAmoutViewHolder;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/23.
 */
public class TwentyFourSaleAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Double> data;
    private LayoutInflater inflater;
    private double max;
    public TwentyFourSaleAdapter(Context context, List<Double> data) {
        this.context=context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }
    public void setData(List<Double> data) {
        this.data = data;
        notifyDataSetChanged();
        for (Double Double : data) {
            if(max<Double){
                max=Double;
            }
        }
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
      if(position==data.size()-1){
          amoutViewHolder.mViewFoot.setVisibility(View.VISIBLE);
      }else{
          amoutViewHolder.mViewFoot.setVisibility(View.GONE);
      }
            double amount = data.get(position);
            amoutViewHolder.barAmount.setTwoData(""+position+"-"+(position+1),(int)amount,(int)max,"杯");


    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }


}
