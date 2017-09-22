package com.hzjytech.operation.adapters.data.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/18.
 */
public class SelectMachineViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_select_machine_isChecked)
   public CheckBox ivSelectMachineIsChecked;
    @BindView(R.id.iv_select_machine_icon)
    public TextView tvSelectMachineIcon;
    @BindView(R.id.tv_select_machine_name)
    public TextView tvSelectMachineName;
    @BindView(R.id.tv_select_machine_des)
    public TextView tvSelectMachineDes;
    @BindView(R.id.iv_select_machine_down)
    public ImageView ivSelectMachineDown;
    @BindView(R.id.item_select_machine_blank)
    public View itemBlank;
    @BindView(R.id.item_select_machine_line)
    public View itemLine;
    @BindView(R.id.ll_menu_item_container)
    public LinearLayout llContainer;
    @BindView(R.id.ll_check)
    public LinearLayout ll_check;

    public SelectMachineViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
