package com.hzjytech.operation.adapters.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.group.viewholders.GroupViewHolder;
import com.hzjytech.operation.adapters.group.viewholders.MoreMachinesViewHolder;
import com.hzjytech.operation.adapters.home.viewholders.MachiesViewHolder;
import com.hzjytech.operation.adapters.menu.viewholders.MenuViewHolder;
import com.hzjytech.operation.adapters.task.viewholder.TaskListViewHolder;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.GroupList;
import com.hzjytech.operation.entity.Machies;
import com.hzjytech.operation.entity.MenuList;
import com.hzjytech.operation.entity.TaskListInfo;
import com.hzjytech.operation.inter.MachineGroupClickListener;
import com.hzjytech.operation.inter.MachineMenuClickListener;
import com.hzjytech.operation.inter.MachineNumberClickListener;
import com.hzjytech.operation.inter.OnMachineItemClickListener;
import com.hzjytech.operation.module.machine.DetailMachineActivity;
import com.hzjytech.operation.module.group.DetailGroupActivity;
import com.hzjytech.operation.module.menu.MenuActivity;
import com.hzjytech.operation.module.task.DetailTaskActivity;
import com.hzjytech.operation.utils.TimeUtil;

import java.util.List;

/**
 * Created by hehongcan on 2017/4/28.
 */
public class SearchAdapter extends RecyclerView.Adapter{
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_NORMAL = 1;
    private  Context context;
    private  Machies machies;
    private  List<Machies.VendingMachines> vendingMachines;
    private final LayoutInflater inflater;
    private MachineNumberClickListener machineNumberClickListener;
    private MachineGroupClickListener machineGroupClcikListener;
    private MachineMenuClickListener machineMenuClickListener;
    private OnMachineItemClickListener onMachinesItemClickListener;
    private List<GroupInfo.SubMachinesBean> singleMachines;
    private List<MenuList> menus;
    private List<GroupList> groups;
    private List<TaskListInfo> tasks;

    public SearchAdapter(Context context, Machies machies) {
        this.context=context;
        this.machies=machies;
        inflater = LayoutInflater.from(context);
        if(machies!=null){
            vendingMachines = machies.getVendingMachines();
        }

    }
    public void setSearchData(Machies machies) {
        this.machies = machies;
        vendingMachines = machies.getVendingMachines();
        notifyDataSetChanged();
    }
    public void setSingleMachinesSearchData(List<GroupInfo.SubMachinesBean> singleMachines) {
        this.singleMachines=singleMachines;
        notifyDataSetChanged();

    }

    public void setMenusSearchData(List<MenuList> menus) {
        this.menus=menus;
        notifyDataSetChanged();
    }

    public void setGroupsSearchData(List<GroupList> groups) {
        this.groups=groups;
        notifyDataSetChanged();
    }
    public void setTaskSearchData(List<TaskListInfo> taskSearchData) {
        this.tasks = taskSearchData;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(vendingMachines!=null){
            View view = inflater.inflate(R.layout.home_machies, parent, false);
            return new MachiesViewHolder(view,machineNumberClickListener,machineGroupClcikListener,machineMenuClickListener,onMachinesItemClickListener);
        }else if(singleMachines!=null){
            View view = inflater.inflate(R.layout.item_machines, parent, false);
            return new MoreMachinesViewHolder(view);
        }else if(groups!=null){
            View view = inflater.inflate(R.layout.item_group, parent, false);
            return  new GroupViewHolder(view);
        }else if(menus!=null){
            View view = inflater.inflate(R.layout.item_menu_list, parent, false);
            return new MenuViewHolder(view);
        }else{
            View view = inflater.inflate(R.layout.item_task, parent, false);
            return new TaskListViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
       if(holder instanceof MachiesViewHolder){
           MachiesViewHolder holder1 = (MachiesViewHolder) holder;
           holder1.setViewData(context,vendingMachines,position,getItemViewType(position));
       }else  if(holder instanceof MoreMachinesViewHolder){
           MoreMachinesViewHolder machinesViewHolder = (MoreMachinesViewHolder) holder;
           machinesViewHolder.tvMachineOrder.setText(position+1+"");
           machinesViewHolder.tvMachineItemId.setText(singleMachines.get(position).getId()+"");
           machinesViewHolder.tvMachineName.setText(singleMachines.get(position).getName());
           machinesViewHolder.tvMachineLocation.setText(singleMachines.get(position).getAddress());
           machinesViewHolder.ll_more_item_machine.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(context, DetailMachineActivity.class);
                   intent.putExtra("machineId",singleMachines.get(position).getId());
                   context.startActivity(intent);
               }
           });
       }else  if(holder instanceof GroupViewHolder){
           GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
           groupViewHolder.tvGroupId.setText(position+1+"");
           groupViewHolder.tvGroupName.setText(groups.get(position).getName());
           groupViewHolder.tvGroupNote.setVisibility(View.GONE);
           groupViewHolder.ivGroup.setImageResource(groups.get(position).isIsSuper()?R.drawable.icon_groups:R.drawable.icon_group);
           groupViewHolder.view_top_grey.setVisibility(View.GONE);
           groupViewHolder.ll_more_item_group.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(context, DetailGroupActivity.class);
                   intent.putExtra("groupId",groups.get(position).getId());
                   context.startActivity(intent);
               }
           });
       }else if(holder instanceof MenuViewHolder){
           MenuViewHolder MenuViewHolder = (MenuViewHolder) holder;
           MenuViewHolder.tvMenuId.setText(position+1+"");
           MenuViewHolder.tvMenuName.setText(menus.get(position).getName());
           MenuViewHolder.llMoreItemMenu.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(context, MenuActivity.class);
                   intent.putExtra("menuId",menus.get(position).getId());
                   context.startActivity(intent);
               }
           });
       }else if(holder instanceof TaskListViewHolder){
           TaskListViewHolder taskListViewHolder = (TaskListViewHolder) holder;
           final TaskListInfo info = tasks.get(position);
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
           taskListViewHolder.mTvTaskArea.setText(info.getCity());
           taskListViewHolder.mTvTaskCode.setText(info.getMachineCode());
           taskListViewHolder.mTvTaskStatus.setText(info.getStatus()==1?R.string.unfinish:R.string.finished);
           taskListViewHolder.mTvTaskTime.setText(TimeUtil.getShort6OrShort3FromTime(info.getUpdatedAt().getTime()));
           taskListViewHolder.mLLTask.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                        Intent intent = new Intent(context, DetailTaskActivity.class);
                        intent.putExtra("taskId",Integer.valueOf(info.getId()));
                        intent.putExtra("creatTime",TimeUtil.getLongTime(info.getCreatedAt().getTime()));
                        context.startActivity(intent);
               }
           });
       }
    }


    @Override
    public int getItemCount() {
        if(singleMachines==null&&vendingMachines==null&&groups==null&&menus==null&&tasks==null){
            return 0;
        }else if(singleMachines!=null){
            return singleMachines.size();
        }else if(vendingMachines!=null){
            return vendingMachines.size();
        }else if(groups!=null){
            return groups.size();
        }else if(menus!=null){
            return menus.size();
        }else{
            return tasks.size();
        }
    }



}
