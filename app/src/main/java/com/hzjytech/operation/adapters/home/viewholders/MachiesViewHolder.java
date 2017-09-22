package com.hzjytech.operation.adapters.home.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.Machies;
import com.hzjytech.operation.inter.MachineGroupClickListener;
import com.hzjytech.operation.inter.MachineMenuClickListener;
import com.hzjytech.operation.inter.MachineNumberClickListener;
import com.hzjytech.operation.inter.OnMachineItemClickListener;
import com.hzjytech.operation.module.group.DetailGroupActivity;
import com.hzjytech.operation.module.machine.DetailMachineActivity;
import com.hzjytech.operation.module.menu.MenuActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/4/24.
 */
public class MachiesViewHolder extends RecyclerView.ViewHolder {
    private OnMachineItemClickListener onMachinesItemClickListener;
    private MachineMenuClickListener machineMenuClickListener;
    private MachineGroupClickListener machineGroupClcikListener;
    private MachineNumberClickListener machineNumberClickListener;
    @BindView(R.id.tv_machines_id)
    TextView tvMachinesId;
    @BindView(R.id.tv_machines_location)
    TextView tvMachinesLocation;
    @BindView(R.id.tv_machines_normal)
    TextView tvMachinesNormal;
    @BindView(R.id.tv_machines_leave)
    TextView tvMachinesLeave;
    @BindView(R.id.tv_machines_line)
    TextView tvMachinesLine;
    @BindView(R.id.tv_machines_lock)
    TextView tvMachinesLock;
    @BindView(R.id.tv_machines_operation)
    TextView tvMachinesOperation;
    @BindView(R.id.tv_machines_number)
    TextView tvMachinesNumber;
    @BindView(R.id.tv_machines_group)
    TextView tvMachinesGroup;
    @BindView(R.id.tv_machines_menu)
    TextView tvMachinesMenu;
    @BindView(R.id.ll_machines_item)
    LinearLayout ll_machines_item;
    @BindView(R.id.item_top_grey)
    View viewTopGrey;
    private Machies.VendingMachines vend;

    public MachiesViewHolder(View machiesView, MachineNumberClickListener machineNumberClickListener,
                             MachineGroupClickListener machineGroupClcikListener, MachineMenuClickListener machineMenuClickListener,
                             OnMachineItemClickListener onMachinesItemClickListener) {
        super(machiesView);
        ButterKnife.bind(this, machiesView);
        this.machineNumberClickListener = machineNumberClickListener;
        this.machineGroupClcikListener = machineGroupClcikListener;
        this.machineMenuClickListener = machineMenuClickListener;
        this.onMachinesItemClickListener=onMachinesItemClickListener;
    }

    public void setViewData(final Context mContext, Object item, int position, int type) {
        if(type!= Constants.state_opteration&&position==0){
            viewTopGrey.setVisibility(View.GONE);
        }else{
            viewTopGrey.setVisibility(View.VISIBLE);
        }
        int grey= mContext.getResources().getColor(R.color.hint_color);
        int red = mContext.getResources().getColor(R.color.little_red);
        List<Machies.VendingMachines> vendingMachines = (List<Machies.VendingMachines>) item;
        vend = vendingMachines.get(position);
        tvMachinesId.setText(vend.getId() + "");
        tvMachinesLocation.setText(vend.getLocation());
        tvMachinesNormal.setText(vend.isStatus() ? "机器正常" : "机器错误");
        tvMachinesNormal.setTextColor(vend.isStatus() ? grey : red);
        tvMachinesLeave.setText(!vend.isInventory_flag() ? "余料充足" : "余料不足");
        tvMachinesLeave.setTextColor(!vend.isInventory_flag() ? grey : red);
        tvMachinesLine.setText(vend.isLink_status() ? "网络正常" : "网络离线");
        tvMachinesLine.setTextColor(vend.isLink_status()?grey:red);
        tvMachinesLock.setText(vend.isIs_locked() ? "锁定" : "未锁定");
        tvMachinesLock.setTextColor(vend.isIs_locked() ? red :grey);
        tvMachinesOperation.setText(vend.isOperation_status() ? "运营中" : "未运营");
        tvMachinesOperation.setTextColor(vend.isOperation_status() ? grey : red);
        tvMachinesNumber.setVisibility(vend.getName()==null?View.INVISIBLE:View.VISIBLE);
        tvMachinesNumber.setText(vend.getName());
        tvMachinesGroup.setText(vend.getMarket_group_name() == null || vend.getMarket_group_name() == null ? "" : vend.getMarket_group_name());
        tvMachinesGroup.setVisibility(vend.getMarket_group_name()==null?View.INVISIBLE:View.VISIBLE);
        tvMachinesMenu.setText(vend.getMenu_name() == null|| vend.getMenu_name() == null ? "" :  vend.getMenu_name());
        tvMachinesMenu.setVisibility(vend.getMenu_name()==null?View.INVISIBLE:View.VISIBLE);
        ll_machines_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailMachineActivity.class);
                intent.putExtra("machineId",vend.getId());
                mContext.startActivity(intent);
                if(onMachinesItemClickListener!=null){
                    onMachinesItemClickListener.onClick(vend.getId());
                }
            }
        });
        tvMachinesNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailMachineActivity.class);
                intent.putExtra("machineId",vend.getId());
                mContext.startActivity(intent);
                if(machineNumberClickListener!=null){
                    machineNumberClickListener.machineNumberClick(vend.getId());
                }
            }
        });
        tvMachinesGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailGroupActivity.class);
                intent.putExtra("groupId",vend.getMarket_group_id());
                mContext.startActivity(intent);
                if(machineGroupClcikListener!=null){
                    machineGroupClcikListener.MachineGroupClick(vend.getMarket_group_id());
                }
            }
        });
        tvMachinesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MenuActivity.class);
                intent.putExtra("menuId",vend.getMenu_id());
                mContext.startActivity(intent);
                if(machineMenuClickListener!=null){
                    machineMenuClickListener.machineMenuClick(vend.getMenu_id());
                }
            }
        });
    }

}
