package com.hzjytech.operation.adapters.group;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.group.viewholders.MoreMachinesViewHolder;
import com.hzjytech.operation.adapters.home.viewholders.SearchViewHolder;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.inter.SearchViewClickListener;
import com.hzjytech.operation.module.machine.DetailMachineActivity;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class MoreMachineAdapter extends RecyclerView.Adapter{

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_NORMAL = 1;
    private  LayoutInflater inflater;
    private  Context context;
    private  List<GroupInfo.SubMachinesBean> machines;
    private boolean hasHead=false;
    private SearchViewClickListener searchViewClickListener;

    public MoreMachineAdapter(Context context, List<GroupInfo.SubMachinesBean> machines) {
        this.context=context;
        this.machines=machines;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 是否含有头部搜索栏
     * @param hasHead
     */
    public void setHasHead(boolean hasHead) {
        this.hasHead=hasHead;
    }
  public void setMachinesData(List<GroupInfo.SubMachinesBean> machines){
      this.machines=machines;
      notifyDataSetChanged();
  }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEAD){
            View view = inflater.inflate(R.layout.home_search, parent, false);
            return new SearchViewHolder(view,searchViewClickListener);
        }else{
            View view = inflater.inflate(R.layout.item_machines, parent, false);
            return new MoreMachinesViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(hasHead) {
            if(holder instanceof SearchViewHolder){
                SearchViewHolder searchViewHolder = (SearchViewHolder) holder;
                searchViewHolder.setViewData(context,null,0,getItemViewType(position), Constants.state_single_machine);
            }
            if(holder instanceof MoreMachinesViewHolder){
                MoreMachinesViewHolder machinesViewHolder = (MoreMachinesViewHolder) holder;
                machinesViewHolder.tvMachineOrder.setText(position+"");
                machinesViewHolder.tvMachineItemId.setText(machines.get(position-1).getId()+"");
                machinesViewHolder.tvMachineName.setText(machines.get(position-1).getName());
                machinesViewHolder.tvMachineLocation.setText(machines.get(position-1).getAddress());
                machinesViewHolder.ll_more_item_machine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailMachineActivity.class);
                        intent.putExtra("machineId",machines.get(position-1).getId());
                        context.startActivity(intent);
                    }
                });
            }
        }else{
            if(holder instanceof MoreMachinesViewHolder){
                MoreMachinesViewHolder machinesViewHolder = (MoreMachinesViewHolder) holder;
                machinesViewHolder.tvMachineOrder.setText(position+1+"");
                machinesViewHolder.tvMachineItemId.setText(machines.get(position).getId()+"");
                machinesViewHolder.tvMachineName.setText(machines.get(position).getName());
                machinesViewHolder.tvMachineLocation.setText(machines.get(position).getAddress());
                machinesViewHolder.ll_more_item_machine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailMachineActivity.class);
                        intent.putExtra("machineId",machines.get(position).getId());
                        context.startActivity(intent);
                    }
                });
            }
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
        return (machines==null?0:machines.size())+(hasHead?1:0);
    }
    public void setSearchViewClickListener(SearchViewClickListener searchViewClickListener){
        this.searchViewClickListener=searchViewClickListener;
    }

}
