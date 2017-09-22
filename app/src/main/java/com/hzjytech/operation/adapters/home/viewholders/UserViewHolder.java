package com.hzjytech.operation.adapters.home.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/9/6.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_admin_name)
   public  TextView mTvAdminName;
    @BindView(R.id.tv_remove_admin)
    public TextView mTvRemoveAdmin;

    public UserViewHolder(View view) {
        super(view);
       // R.layout.item_select_users
        ButterKnife.bind(this, view);
    }
}
