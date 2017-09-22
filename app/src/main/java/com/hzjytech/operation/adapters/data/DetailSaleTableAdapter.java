package com.hzjytech.operation.adapters.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.viewholders.SaleListViewHolder;
import com.hzjytech.operation.entity.DetailSaleMachine;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by hehongcan on 2017/5/22.
 */
public class DetailSaleTableAdapter extends RecyclerView.Adapter{
    private  DecimalFormat df;
    private Context context;
    private List<DetailSaleMachine.VendingMachinesBean> data;
    private final LayoutInflater inflater;

    public DetailSaleTableAdapter(Context context, List<DetailSaleMachine.VendingMachinesBean> data) {
        this.context=context;
        this.data=data;
        inflater = LayoutInflater.from(context);
        df = new DecimalFormat("0.00");
    }
    public void setData(List<DetailSaleMachine.VendingMachinesBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_salelist, parent, false);
        return new SaleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SaleListViewHolder listViewHolder = (SaleListViewHolder) holder;
        listViewHolder.mTvSaleListMachineCode.setText(position+1+"."+data.get(position).getMachineName());
        listViewHolder.mTvSaleListMachineGroup.setText(data.get(position).getGroupName()==null?"":"·"+data.get(position).getGroupName());
        listViewHolder.tvSalelistOrderAdrress.setText(data.get(position).getLocation());
        listViewHolder.tvSalelistCount.setText("销售量："+data.get(position).getNum()+"杯");
        listViewHolder.tvSalelistMoney.setText("销售总额："+df.format(data.get(position).getSum())+"元");
        listViewHolder.tvSalelistCount.setTextColor(context.getResources().getColor(R.color.normal_red));
        listViewHolder.tvSalelistMoney.setTextColor(context.getResources().getColor(R.color.normal_red));
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }


}
