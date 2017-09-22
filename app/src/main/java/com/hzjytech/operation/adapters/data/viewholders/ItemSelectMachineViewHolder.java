package com.hzjytech.operation.adapters.data.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/19.
 */
public class ItemSelectMachineViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_select_machine)
    public TextView tvSelectMachine;
    @BindView(R.id.tv_to_select_machine)
    public TextView tvToSelectMachine;
    @BindView(R.id.ll_select_machines)
    public LinearLayout llSelectMachines;

    public ItemSelectMachineViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);
    }
}
