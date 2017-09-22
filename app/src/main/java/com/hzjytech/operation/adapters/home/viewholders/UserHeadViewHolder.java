package com.hzjytech.operation.adapters.home.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/9/6.
 */

public class UserHeadViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rl_add_admin)
    public RelativeLayout mRlAddAdmin;

    public UserHeadViewHolder(View view) {
        super(view);
        //R.layout.item_select_user_head
        ButterKnife.bind(this, view);
    }
}
