package com.hzjytech.operation.adapters.group;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.group.viewholders.GroupViewHolder;
import com.hzjytech.operation.adapters.home.viewholders.SearchViewHolder;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.GroupList;
import com.hzjytech.operation.inter.SearchViewClickListener;
import com.hzjytech.operation.module.group.DetailGroupActivity;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class MoreGroupAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_NORMAL = 1;
    private  Context context;
    private  List<GroupList> groups;
    private  LayoutInflater inflater;
    private boolean hasHead=false;
    private SearchViewClickListener searchViewClickListener;

    public MoreGroupAdapter(Context context, List<GroupList> groups) {
        this.context=context;
        this.groups=groups;
        inflater = LayoutInflater.from(context);
    }
    public void setHasHead(boolean hasHead) {
      this.hasHead=hasHead;
    }
   public void setGroupData(List<GroupList> groups){
       this.groups=groups;
       notifyDataSetChanged();
   }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEAD){
            View view = inflater.inflate(R.layout.home_search, parent, false);
            return new SearchViewHolder(view,searchViewClickListener);
        }else{
            View view = inflater.inflate(R.layout.item_group, parent, false);
            return  new GroupViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(hasHead){
            if(holder instanceof SearchViewHolder){
                SearchViewHolder searchViewHolder = (SearchViewHolder) holder;
                searchViewHolder.setViewData(context,null,0,getItemViewType(position), Constants.state_single_group);
            }
            if(holder instanceof GroupViewHolder){
                GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
                groupViewHolder.tvGroupId.setText(position+"");
                groupViewHolder.tvGroupName.setText(groups.get(position-1).getName());
                groupViewHolder.tvGroupNote.setVisibility(View.GONE);
                groupViewHolder.view_top_grey.setVisibility(View.GONE);
                groupViewHolder.ll_more_item_group.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailGroupActivity.class);
                        intent.putExtra("groupId",groups.get(position-1).getId());
                        context.startActivity(intent);
                    }
                });
            }

        }else{
            GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
            if(position==0){
                groupViewHolder.view_top_grey.setVisibility(View.VISIBLE);
            }else{
                groupViewHolder.view_top_grey.setVisibility(View.GONE);
            }
            groupViewHolder.tvGroupId.setText(position+1+"");
            groupViewHolder.tvGroupName.setText(groups.get(position).getName());
            groupViewHolder.tvGroupNote.setVisibility(View.GONE);
            groupViewHolder.ivGroup.setImageResource(groups.get(position).isIsSuper()?R.drawable.icon_groups:R.drawable.icon_group);
            groupViewHolder.ll_more_item_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailGroupActivity.class);
                    intent.putExtra("groupId",groups.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(hasHead){
            if(position==0){
                return TYPE_HEAD;
            }else{
                return TYPE_NORMAL;
            }
        }else{
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return (groups==null?0:groups.size())+(hasHead?1:0);
    }

    public void setSearchViewClickListener(SearchViewClickListener searchViewClickListener){
        this.searchViewClickListener=searchViewClickListener;
    }
}
