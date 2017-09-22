package com.hzjytech.operation.adapters.task.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/6/14.
 */
public class TaskListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_task_type)
    public TextView mTvTaskType;
    @BindView(R.id.tv_task_area)
    public TextView mTvTaskArea;
    @BindView(R.id.tv_task_code)
    public TextView mTvTaskCode;
    @BindView(R.id.tv_task_time)
    public TextView mTvTaskTime;
    @BindView(R.id.tv_task_status)
    public TextView mTvTaskStatus;
    @BindView(R.id.ll_item_task)
    public LinearLayout mLLTask;
    @BindView(R.id.iv_task)
    public ImageView mIvTask;

    public TaskListViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
