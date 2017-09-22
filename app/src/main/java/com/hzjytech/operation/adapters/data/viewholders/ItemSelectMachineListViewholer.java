package com.hzjytech.operation.adapters.data.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/19.
 */
public class ItemSelectMachineListViewholer extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_select_id)
    public TextView tvSelectId;
    @BindView(R.id.tv_select_name)
    public TextView tvSelectName;
    @BindView(R.id.tv_select_des)
    public TextView tvSelectDes;
    @BindView(R.id.btnDelete)
    public Button btnDelete;
    public ItemSelectMachineListViewholer(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
