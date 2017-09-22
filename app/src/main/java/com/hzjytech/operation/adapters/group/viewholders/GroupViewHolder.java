package com.hzjytech.operation.adapters.group.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class GroupViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_group_id)
    public TextView tvGroupId;
    @BindView(R.id.iv_group)
    public ImageView ivGroup;
    @BindView(R.id.tv_group_name)
    public TextView tvGroupName;
    @BindView(R.id.tv_group_note)
    public TextView tvGroupNote;
    @BindView(R.id.ll_more_item_group)
    public LinearLayout ll_more_item_group;
    @BindView(R.id.view_group_top_grey)
    public View view_top_grey;

    public GroupViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
