package com.hzjytech.operation.adapters.task;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.home.viewholders.SearchViewHolder;
import com.hzjytech.operation.adapters.task.viewholder.ImageViewHolder;
import com.hzjytech.operation.adapters.task.viewholder.TaskListViewHolder;
import com.hzjytech.operation.entity.TaskListInfo;
import com.hzjytech.operation.inter.SearchViewClickListener;
import com.hzjytech.operation.module.task.DetailTaskActivity;
import com.hzjytech.operation.utils.TimeUtil;

import java.util.List;

/**
 * 含有头部搜索框的列表展示adapter
 * Created by hehongcan on 2017/6/14.
 */
public class BaseSearchListAdapter extends RecyclerView.Adapter{

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_EMPTY = 2;
    private LayoutInflater inflater;
    private Context context;
    private List<TaskListInfo> tasks;
    private boolean hasHead=false;
    private SearchViewClickListener searchViewClickListener;
    private int searchType=10;

    public BaseSearchListAdapter(Context context, List<TaskListInfo> tasks) {
        this.context=context;
        this.tasks=tasks;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<TaskListInfo> tasks, int searchType){
        this.tasks=tasks;
        this.searchType=searchType;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEAD){
            View view = inflater.inflate(R.layout.home_search, parent, false);
            return new SearchViewHolder(view,searchViewClickListener);
        }else if(viewType==TYPE_NORMAL){
            View view = inflater.inflate(R.layout.item_task, parent, false);
            return new TaskListViewHolder(view);
        }else {
            ImageView view = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            return new ImageViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if(holder instanceof SearchViewHolder){
                SearchViewHolder searchViewHolder = (SearchViewHolder) holder;
                searchViewHolder.setViewData(context,null,0,getItemViewType(position), searchType);
            }
            if(holder instanceof TaskListViewHolder){
                TaskListViewHolder taskListViewHolder = (TaskListViewHolder) holder;
                final TaskListInfo info = tasks.get(position - 1);
                switch (info.getType()){
                    case 1:
                        taskListViewHolder.mTvTaskType.setText(R.string.fix_task);
                        taskListViewHolder.mIvTask.setImageResource(R.drawable.icon_fix_task);
                        break;
                    case 2:
                        taskListViewHolder.mTvTaskType.setText(R.string.feed_task);
                        taskListViewHolder.mIvTask.setImageResource(R.drawable.icon_feed_task);
                        break;
                    case 3:
                        taskListViewHolder.mTvTaskType.setText(R.string.other_task);
                        taskListViewHolder.mIvTask.setImageResource(R.drawable.icon_other_task);
                        break;
                }
                taskListViewHolder.mTvTaskArea.setText(info.getRegion());
                taskListViewHolder.mTvTaskCode.setText(info.getMachineCode());
                taskListViewHolder.mTvTaskStatus.setText(info.getStatus()==1?R.string.unfinish:R.string.finished);
                taskListViewHolder.mTvTaskTime.setText(TimeUtil.getShort6OrShort3FromTime(info.getUpdatedAt().getTime()));
                taskListViewHolder.mLLTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent intent = new Intent(context, DetailTaskActivity.class);
                        intent.putExtra("taskId",Integer.valueOf(info.getId()));
                        context.startActivity(intent);
                    }
                });
            }
        if(holder instanceof ImageViewHolder){
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            ImageView imageView = (ImageView) imageViewHolder.itemView;
            imageView.setImageResource(R.drawable.empty_no_task);
        }
    }

    @Override
    public int getItemViewType(int position) {
            if(position==0){
                return TYPE_HEAD;
            }else{
                if(tasks==null||tasks.size()==0){
                    return TYPE_EMPTY;
                }else{
                    return TYPE_NORMAL;
                }

            }

    }

    @Override
    public int getItemCount() {
        return ((tasks==null||tasks.size()==0)?2:tasks.size()+1);
    }
    public void setSearchViewClickListener(SearchViewClickListener searchViewClickListener){
        this.searchViewClickListener=searchViewClickListener;
    }

}
