package com.hzjytech.operation.adapters.group.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class MoreMachinesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_machine_order)
    public TextView tvMachineOrder;
    @BindView(R.id.tv_machine_item_id)
    public TextView tvMachineItemId;
    @BindView(R.id.tv_machine_name)
    public TextView tvMachineName;
    @BindView(R.id.tv_machine_location)
    public TextView tvMachineLocation;
    @BindView(R.id.ll_more_item_machine)
    public LinearLayout ll_more_item_machine;

    public MoreMachinesViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
