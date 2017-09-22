package com.hzjytech.operation.adapters.task.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.entity.DetailTaskInfo;
import com.hzjytech.operation.utils.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/6/19.
 */
public class TaskStatuViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_task_status)
    TextView mTvTaskStatus;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.tv_task_type)
    TextView mTvTaskType;
    @BindView(R.id.tv_task_time)
    TextView mTvTaskTime;
    @BindView(R.id.ll_task_status)
    LinearLayout mLlTaskStatus;


    public TaskStatuViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setData(Context context, DetailTaskInfo data) {
        if(data==null){
            return;
        }
        int status = data.getStatus();
        if(status==1){
            //待处理
            mTvTaskStatus.setText(R.string.task_remain);
            mLlTaskStatus.setBackground(context.getResources().getDrawable(R.drawable.bg_task_statu_unfinish));
            mViewLine.setBackgroundColor(context.getResources().getColor(R.color.bg_red));
        }else{
            mTvTaskStatus.setText(R.string.task_has_handled);
            mLlTaskStatus.setBackground(context.getResources().getDrawable(R.drawable.bg_task_statu_finish));
            mViewLine.setBackgroundColor(context.getResources().getColor(R.color.bg_green));
        }
        int type = data.getType();
        switch (type){
            case 1:
                mTvTaskType.setText(R.string.fix_task);
                break;
            case 2:
                mTvTaskType.setText(R.string.feed_task);
                break;
            case 3:
                mTvTaskType.setText(R.string.other_task);
                break;
        }
        mTvTaskTime.setText(TimeUtil.getLong4TimeFromLong(data.getCreatedAt()));
    }
}
