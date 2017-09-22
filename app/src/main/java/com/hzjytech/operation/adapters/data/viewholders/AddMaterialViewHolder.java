package com.hzjytech.operation.adapters.data.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/24.
 */
public class AddMaterialViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.view_add_material_head)
    public View viewAddMaterialHead;
    @BindView(R.id.tv_materiall_name)
    public  TextView tvMateriallName;
    @BindView(R.id.tv_material_value)
    public TextView tvMaterialValue;

    public AddMaterialViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
