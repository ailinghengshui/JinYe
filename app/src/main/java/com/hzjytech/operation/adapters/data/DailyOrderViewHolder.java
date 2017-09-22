package com.hzjytech.operation.adapters.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hzjytech.operation.R;
import com.hzjytech.operation.widgets.OrderBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/23.
 */
public class DailyOrderViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.bar_order)
    public OrderBarView barOrder;
    @BindView(R.id.bar_cup)
    public OrderBarView barCup;

    public DailyOrderViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
