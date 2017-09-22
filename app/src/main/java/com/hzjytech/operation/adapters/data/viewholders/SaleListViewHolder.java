package com.hzjytech.operation.adapters.data.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/16.
 */
public class SaleListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_salelist_order_adrress)
    public TextView tvSalelistOrderAdrress;
    @BindView(R.id.tv_salelist_count)
    public TextView tvSalelistCount;
    @BindView(R.id.tv_salelist_money)
    public TextView tvSalelistMoney;
    @BindView(R.id.tv_saleList_machine_code)
    public TextView mTvSaleListMachineCode;
    @BindView(R.id.tv_saleList_machine_group)
    public TextView mTvSaleListMachineGroup;

    public SaleListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
