package com.hzjytech.operation.adapters.task.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.entity.DetailTaskInfo;
import com.hzjytech.operation.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/6/19.
 */
public class HistroryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_history_name)
    TextView mTvHistoryName;
    @BindView(R.id.tv_history_time)
    TextView mTvHistoryTime;
    @BindView(R.id.tv_history_content)
    TextView mTvHistoryContent;

    public HistroryViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setData(
            Context context,
            List<DetailTaskInfo.TaskHistoryDOListBean> taskHistoryDOList,
            DetailTaskInfo data,
            String creatTime,
            int position) {
        if(position==taskHistoryDOList.size()-1){
             String typeStirng = null;
            String createrName = data.getCreaterName();
            int type = data.getType();
            mTvHistoryName.setText(createrName);
            mTvHistoryTime.setText(creatTime);
            switch (type){
                case 1:
                    typeStirng =context.getResources().getString(R.string.fix_task);
                    break;
                case 2:
                    typeStirng =context.getResources().getString(R.string.feed_task);
                    break;
                case 3:
                    typeStirng =context.getResources().getString(R.string.other_task);
                    break;
                default:
                    break;
            }
            StringBuilder sb = new StringBuilder(context.getResources().getString(R.string.creat_task));
            sb.append(typeStirng);
            mTvHistoryContent.setText(sb.toString());
        }else {
            DetailTaskInfo.TaskHistoryDOListBean bean = taskHistoryDOList.get
                    (position);
            mTvHistoryName.setText(bean.getName());
            mTvHistoryTime.setText(bean.getUpdatedAt());
            switch (bean.getType()){
                case 1:
                    if(bean.getStatus()==1){
                        mTvHistoryContent.setText(R.string.change_unHandler);
                    }else if(bean.getStatus()==2){
                        mTvHistoryContent.setText(R.string.change_finish);
                    }
                    break;
                case 2:
                    mTvHistoryContent.setText(context.getString(R.string.change_finish_time,
                            bean.getCutOffTime()));
                    break;
                case 3:
                    String s=null;
                    if(bean.isAddOrSubtract()){
                        s=context.getString(R.string.person_add_change,bean.getDutyName());
                    }else{
                        s=context.getString(R.string.person_dis_change,bean.getDutyName());
                    }
                    mTvHistoryContent.setText(s);
                    break;
                default:
                    break;
            }


        }


    }
}
