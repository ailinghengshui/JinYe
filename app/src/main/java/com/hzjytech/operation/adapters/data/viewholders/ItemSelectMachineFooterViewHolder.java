package com.hzjytech.operation.adapters.data.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by hehongcan on 2017/5/19.
 */
public class ItemSelectMachineFooterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_select_count)
    public TextView tvSelectCount;
    @BindView(R.id.tv_remove_all)
    public TextView tvRemoveAll;

    public ItemSelectMachineFooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
