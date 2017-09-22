package com.hzjytech.operation.adapters.data.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hzjytech.operation.R;
import com.hzjytech.operation.widgets.OrderBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/23.
 */
public class SaleAmoutViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.view_amount_head)
    public View viewAmountHead;
    @BindView(R.id.bar_amount)
    public OrderBarView barAmount;
    @BindView(R.id.view_head2)
    public View mViewHead2;
    @BindView(R.id.view_foot)
    public View mViewFoot;

    public SaleAmoutViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
