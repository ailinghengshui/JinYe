package com.hzjytech.operation.adapters.data.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/24.
 */
public class ErrorListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_error_id)
    public  TextView tvErrorId;
    @BindView(R.id.tv_error_occure_time)
    public  TextView tvErrorOccureTime;
    @BindView(R.id.tv_error_content)
    public   TextView tvErrorContent;
    @BindView(R.id.tv_error_reset_time)
    public  TextView tvErrorResetTime;
    @BindView(R.id.tv_error_solve)
    public  TextView tvErrorSolve;
    @BindView(R.id.tv_check_task)
    public  TextView tvCheckTask;
    @BindView(R.id.tv_error_machine_code)
    public TextView tv_error_machine_code;
    @BindView(R.id.tv_error_machine_location)
    public TextView tv_error_machine_location;
    @BindView(R.id.ll_error_list_container)
    public LinearLayout ll_container;

    public ErrorListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
