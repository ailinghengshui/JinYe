package com.hzjytech.operation.adapters.data.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.hzjytech.operation.R;
import com.hzjytech.operation.widgets.TimeSelectLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/19.
 */
public class TimeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ll_time_picker)
    public  TimeSelectLinearLayout llTimePicker;
    @BindView(R.id.ll_time_container)
    public LinearLayout ll_time_container;

    public TimeViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
