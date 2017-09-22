package com.hzjytech.operation.adapters.widget.selectfrags;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.group.viewholders.GroupViewHolder;
import com.hzjytech.operation.adapters.group.viewholders.MoreMachinesViewHolder;
import com.hzjytech.operation.entity.GroupList;
import com.hzjytech.operation.module.group.DetailGroupActivity;
import com.hzjytech.operation.module.machine.DetailMachineActivity;
import com.hzjytech.operation.widgets.selectfrags.SelectActivity;

import java.util.ArrayList;
import com.hzjytech.operation.entity.GroupInfo.SubMachinesBean;

import static com.hzjytech.operation.constants.Constants.EditType.EDIT_GROUP_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.EditType.EDIT_MACHINE_FROM_GROUP;

/**
 * Created by hehongcan on 2017/9/22.
 */

public class FirstAdapter<T> extends RecyclerView.Adapter{

    private final SelectActivity context;
    private final ArrayList<T> list;
    private final String fragsType;
    private final LayoutInflater mInflater;

    public FirstAdapter(
            SelectActivity activity, ArrayList<T> list, String fragsType) {
        this.context=activity;
        mInflater = LayoutInflater.from(activity);
        this.list=list;
        this.fragsType=fragsType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(fragsType.equals(EDIT_GROUP_FROM_GROUP)){
            View view = mInflater.inflate(R.layout.item_group, parent, false);
            return  new GroupViewHolder(view);
        }else if(fragsType.equals(EDIT_MACHINE_FROM_GROUP)){
            View view = mInflater.inflate(R.layout.item_machines, parent, false);
            return new MoreMachinesViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(fragsType.equals(EDIT_GROUP_FROM_GROUP)){
            final GroupList bean = (GroupList) list.get(position);
            GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
            groupViewHolder.tvGroupId.setText(position+1+"");
            groupViewHolder.tvGroupName.setText(bean.getName());
            groupViewHolder.tvGroupNote.setVisibility(View.GONE);
            groupViewHolder.view_top_grey.setVisibility(View.GONE);
            groupViewHolder.ll_more_item_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailGroupActivity.class);
                    intent.putExtra("groupId",bean.getId());
                    context.startActivity(intent);
                }
            });
        }else if(fragsType.equals(EDIT_MACHINE_FROM_GROUP)){
            final SubMachinesBean bean = (SubMachinesBean) list.get(position);
            MoreMachinesViewHolder machinesViewHolder = (MoreMachinesViewHolder) holder;
            machinesViewHolder.tvMachineOrder.setText(position+1+"");
            machinesViewHolder.tvMachineItemId.setText(bean.getId()+"");
            machinesViewHolder.tvMachineName.setText(bean.getName());
            machinesViewHolder.tvMachineLocation.setText(bean.getAddress());
            machinesViewHolder.ll_more_item_machine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailMachineActivity.class);
                    intent.putExtra("machineId",bean.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
