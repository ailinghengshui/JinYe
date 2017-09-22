package com.hzjytech.operation.adapters.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.task.viewholder.CommentViewHolder;
import com.hzjytech.operation.adapters.task.viewholder.CreatPersonAndCutDownTimeViewHolder;
import com.hzjytech.operation.adapters.task.viewholder.DutyNameViewHolder;
import com.hzjytech.operation.adapters.task.viewholder.HistroryViewHolder;
import com.hzjytech.operation.adapters.task.viewholder.MachineInfoViewHolder;
import com.hzjytech.operation.adapters.task.viewholder.TabViewHolder;
import com.hzjytech.operation.adapters.task.viewholder.TaskStatuViewHolder;
import com.hzjytech.operation.entity.DetailTaskInfo;
import com.hzjytech.operation.inter.GroupOrHistoryClickListener;
import com.hzjytech.operation.inter.OnCutDownTimeClickListener;
import com.hzjytech.operation.inter.OnRemoveClickListener;
import com.hzjytech.operation.inter.OnViewItemClickListener;

import java.util.List;

/**
 * Created by hehongcan on 2017/6/19.
 */
public class DetailTaskAdapter extends RecyclerView.Adapter {
    private static final int TYPE_TASK_STATU = 1;
    private static final int TYPE_MACHINE_INFO = 2;
    private static final int TYPE_DUTY_NAME = 3;
    private static final int TYPE_TAB = 4;
    private static final int TYPE_COMMENT = 5;
    private static final int TYPE_HISTORY = 6;
    private static final int TYPE_CREATE_PERSON_CUT_TIME = 7;
    private  Context context;
    private DetailTaskInfo mData;
    private List<String> mDutyName;
    private List<DetailTaskInfo.TaskCommentDOListBean> mTaskCommentDOList;
    private List<DetailTaskInfo.TaskHistoryDOListBean> mTaskHistoryDOList;
    //是否选中评论栏
    boolean isComment=true;
    private  LayoutInflater mInflater;

    private OnViewItemClickListener onViewItemClickListener;
    private OnRemoveClickListener onRemoveClickListener;



    private OnCutDownTimeClickListener mOnCutDownTimeClickListener;
    private String mCutDownTime;

    public DetailTaskAdapter(Context context, DetailTaskInfo data) {
        this.context=context;
        mData=data;
        mInflater = LayoutInflater.from(context);
    }
    // TODO: 2017/8/3 加入截止时间
    public void setData(DetailTaskInfo data, List<String> selectedNames) {
        if(data==null){
            return;
        }
        mData = data;
        mDutyName = selectedNames;
        mCutDownTime=data.getCutOffTime();
        mTaskCommentDOList = mData.getTaskCommentDOList();
        mTaskHistoryDOList = mData.getTaskHistoryDOList();
        notifyDataSetChanged();
    }
    public void setDutyNames(List<String> names){
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TASK_STATU:
                View view = mInflater.inflate(R.layout.item_task_statu, parent, false);
                return new TaskStatuViewHolder(view);
            case TYPE_MACHINE_INFO:
                View view1 = mInflater.inflate(R.layout.item_machine_info, parent, false);
                return new MachineInfoViewHolder(view1);
            case TYPE_DUTY_NAME:
                View view2 = mInflater.inflate(R.layout.item_duty_name, parent, false);
                return new DutyNameViewHolder(view2);
            case TYPE_TAB:
                View view3 = mInflater.inflate(R.layout.item_commnet_history_tab, parent, false);
                return new TabViewHolder(view3);
            case TYPE_COMMENT:
                View view4 = mInflater.inflate(R.layout.item_comment, parent, false);
                return new CommentViewHolder(view4);
            case TYPE_HISTORY:
                View view5 = mInflater.inflate(R.layout.item_history, parent, false);
                return new HistroryViewHolder(view5);
            case TYPE_CREATE_PERSON_CUT_TIME:
                View view7 = mInflater.inflate(R.layout.item_creat_person_cut_time,
                        parent,
                        false);
                return new CreatPersonAndCutDownTimeViewHolder(view7);
            default:return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
          if(holder instanceof TaskStatuViewHolder){
              ((TaskStatuViewHolder)holder).setData(context,mData);
          }else if(holder instanceof MachineInfoViewHolder){
              ((MachineInfoViewHolder)holder).setData(context,mData.getVendingMachineDO());
          }else if(holder instanceof DutyNameViewHolder){
              ((DutyNameViewHolder)holder).setData(context,mDutyName, onViewItemClickListener,onRemoveClickListener);
          }else if(holder instanceof TabViewHolder){
              ((TabViewHolder)holder).setData(context, new GroupOrHistoryClickListener() {
                  @Override
                  public void onIsCommentClick(boolean isComment) {
                      DetailTaskAdapter.this.isComment=isComment;
                      notifyDataSetChanged();
                  }
              });
          }else if(holder instanceof CreatPersonAndCutDownTimeViewHolder){
              ((CreatPersonAndCutDownTimeViewHolder)holder).setData(mData.getCreaterName(),mCutDownTime,mOnCutDownTimeClickListener);
          }else if(holder instanceof CommentViewHolder){
              ((CommentViewHolder)holder).setData(context,mTaskCommentDOList,position-5);
          }else if(holder instanceof HistroryViewHolder){
              ((HistroryViewHolder)holder).setData(context,mTaskHistoryDOList,mData,mData.getCreatedAt(),position-5);
          }
    }

    @Override
    public int getItemViewType(int position) {
            if(position==0){
                return TYPE_TASK_STATU;
            }else if(position==1){
                return TYPE_MACHINE_INFO;
            }else if(position==2){
                return TYPE_DUTY_NAME;
            }else if(position==3){
                return TYPE_CREATE_PERSON_CUT_TIME;
            }else if(position==4){
                return TYPE_TAB;
            }else{
                return isComment?TYPE_COMMENT:TYPE_HISTORY;
            }


    }

    @Override
    public int getItemCount() {
        if(mData==null){
            return 0;
        }else{
            if(isComment){
                return 5+(mTaskCommentDOList==null?0:mTaskCommentDOList.size());
            }else{
                return 5+(mTaskHistoryDOList==null?0:mTaskHistoryDOList.size());
            }

        }

    }

    public void setOnViewItemClickListener(OnViewItemClickListener onViewItemClickListener) {
        this.onViewItemClickListener = onViewItemClickListener;
    }

    public void setOnRemoveClickListener(OnRemoveClickListener onRemoveClickListener) {
        this.onRemoveClickListener = onRemoveClickListener;
    }
    public void setOnCutDownTimeClickListener(OnCutDownTimeClickListener
                                                      onCutDownTimeClickListener) {
        mOnCutDownTimeClickListener = onCutDownTimeClickListener;
    }

    public void setCutDownTime(String cutDownTime) {
        mCutDownTime = cutDownTime;
        notifyDataSetChanged();
    }
}
