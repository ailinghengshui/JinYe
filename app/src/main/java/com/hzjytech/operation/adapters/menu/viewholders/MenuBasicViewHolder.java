package com.hzjytech.operation.adapters.menu.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class MenuBasicViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_menu_basic_type)
    public TextView tvMenuBasicType;
    @BindView(R.id.tv_menu_basic_time)
    public TextView tvMenuBasicTime;

    public MenuBasicViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
