package com.hzjytech.operation.adapters.menu.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.entity.MenuInfo;
import com.hzjytech.operation.inter.MoreMachineClickListener;
import com.hzjytech.operation.module.machine.DetailMachineActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class MenuMachineViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ll_menu_machine_container)
    public LinearLayout llMenuMachineContainer;

    public MenuMachineViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
    public void setMenuMachineData(final Context context, final List<MenuInfo.SubMachinesBean> machines, final MoreMachineClickListener moreMachineClickListener){
        View head = llMenuMachineContainer.getChildAt(0);
        View lineView = llMenuMachineContainer.getChildAt(1);
        llMenuMachineContainer.removeAllViews();
        final LayoutInflater inflater = LayoutInflater.from(context);
        if(machines==null||machines.size()==0){
            llMenuMachineContainer.setVisibility(View.GONE);
            return;
        }else{
            llMenuMachineContainer.addView(head);
            llMenuMachineContainer.addView(lineView);
            if(machines.size()==1){
                final MenuInfo.SubMachinesBean subMachinesBean = machines.get(0);
                View view = inflater.inflate(R.layout.item_machines, null, false);
                llMenuMachineContainer.addView(view);
                TextView tv_id = (TextView) view.findViewById(R.id.tv_machine_item_id);
                TextView tv_order = (TextView) view.findViewById(R.id.tv_machine_order);
                TextView tv_name = (TextView) view.findViewById(R.id.tv_machine_name);
                TextView tv_location = (TextView) view.findViewById(R.id.tv_machine_location);
                tv_order.setText(1+"");
                tv_id.setText(subMachinesBean.getId()+"");
                tv_name.setText(subMachinesBean.getName());
                tv_location.setText(subMachinesBean.getAddress());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailMachineActivity.class);
                        intent.putExtra("machineId",subMachinesBean.getId());
                        context.startActivity(intent);
                    }
                });
            }else{
                for (int i=0;i<2;i++){
                    View view= inflater.inflate(R.layout.item_machines, null, false);
                    llMenuMachineContainer.addView(view);
                }
                for (int i=0;i<2;i++){
                    View view = llMenuMachineContainer.getChildAt(i + 2);
                    TextView tv_id = (TextView) view.findViewById(R.id.tv_machine_item_id);
                    TextView tv_order = (TextView) view.findViewById(R.id.tv_machine_order);
                    TextView tv_name = (TextView) view.findViewById(R.id.tv_machine_name);
                    TextView tv_location = (TextView) view.findViewById(R.id.tv_machine_location);
                    tv_order.setText(1+i+"");
                    tv_id.setText(machines.get(i).getId()+"");
                    tv_name.setText(machines.get(i).getName());
                    tv_location.setText(machines.get(i).getAddress());

                }
                llMenuMachineContainer.getChildAt(2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,DetailMachineActivity.class);
                        intent.putExtra("machineId",machines.get(0).getId());
                        context.startActivity(intent);
                    }
                });
                llMenuMachineContainer.getChildAt(3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,DetailMachineActivity.class);
                        intent.putExtra("machineId",machines.get(1).getId());
                        context.startActivity(intent);
                    }
                });
               if(machines.size()>2){
                   View foot = inflater.inflate(R.layout.item_root_check_more, null, false);
                   llMenuMachineContainer.addView(foot);
                   foot.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(moreMachineClickListener!=null){
                               moreMachineClickListener.click();
                           }
                       }
                   });
               }

            }
        }
    }
}
