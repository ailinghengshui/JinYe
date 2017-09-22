package com.hzjytech.operation.adapters.group.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/9/19.
 */

public class EditGroupHeadViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ll_add_groups)
    public LinearLayout mLlAddGroups;
    @BindView(R.id.tv_add_something)
    public TextView mTvAddSomething;

    public EditGroupHeadViewHolder(
            View view) {
        super(view);
       // R.layout.edit_group_head
        ButterKnife.bind(this,view);
    }
}
