package com.hzjytech.operation.adapters.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.menu.viewholders.MenuBasicViewHolder;
import com.hzjytech.operation.adapters.menu.viewholders.MenuItemViewHolder;
import com.hzjytech.operation.adapters.menu.viewholders.MenuMachineViewHolder;
import com.hzjytech.operation.adapters.menu.viewholders.MenuPackViewHolder;
import com.hzjytech.operation.entity.MenuInfo;
import com.hzjytech.operation.inter.MoreMachineClickListener;
import com.hzjytech.operation.inter.MorePackClickListener;
import com.hzjytech.operation.inter.MoreSingleItemClickListener;
import com.hzjytech.operation.utils.TimeUtil;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class MyMenuAdapter extends RecyclerView.Adapter {
    private static final int TYPE_BASIC = 0;
    private static final int TYPE_MACHINE = 1;
    private static final int TYPE_ITEM = 3;
    private static final int TYPE_PACK = 4;
    private  Context context;
    private  MenuInfo menuInfo;
    private final LayoutInflater inflater;
    private MoreMachineClickListener moreMachineClickListener;
    private MoreSingleItemClickListener moreSingleItemClickListener;
    private MorePackClickListener morePackClickListener;

    public MyMenuAdapter(Context context, MenuInfo menuInfo) {
        this.context=context;
        this.menuInfo=menuInfo;
        inflater = LayoutInflater.from(context);
    }
   public void setMenuData(MenuInfo menuInfo){
       this.menuInfo=menuInfo;
       notifyDataSetChanged();
   }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType== TYPE_BASIC){
            View view = inflater.inflate(R.layout.item_menu_basic, parent, false);
            return new MenuBasicViewHolder(view);

        }else if(viewType==TYPE_MACHINE){
            View view = inflater.inflate(R.layout.item_menu_machine, parent, false);
            return new MenuMachineViewHolder(view);

        }else if(viewType==TYPE_ITEM){
            View view = inflater.inflate(R.layout.item_menu_item, parent, false);
            return new MenuItemViewHolder(view);
        }else{
            View view = inflater.inflate(R.layout.item_menu_pack, parent, false);
            return new MenuPackViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MenuBasicViewHolder){
            MenuBasicViewHolder menuBasicViewHolder = (MenuBasicViewHolder) holder;
            menuBasicViewHolder.tvMenuBasicType.setText(menuInfo.getVmTypeName());
            menuBasicViewHolder.tvMenuBasicTime.setText(TimeUtil.getCorrectTimeString(menuInfo.getCreateAt()));
        }else if(holder instanceof  MenuMachineViewHolder){
            MenuMachineViewHolder machineViewHolder = (MenuMachineViewHolder) holder;
            machineViewHolder.setMenuMachineData(context,menuInfo.getSubMachines(),moreMachineClickListener);
        }else if(holder instanceof MenuItemViewHolder){
            MenuItemViewHolder menuItemViewHolder = (MenuItemViewHolder) holder;
            menuItemViewHolder.setMenuItemData(context,menuInfo.getItems(),moreSingleItemClickListener);
        }else{
            MenuPackViewHolder menuPackViewHolder = (MenuPackViewHolder) holder;
            menuPackViewHolder.setMenuPackData(context,menuInfo.getPacks(),morePackClickListener);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_BASIC;
        }else if(position==1){
            return TYPE_MACHINE;
        }else if(position==2){
            return TYPE_ITEM;
        }else{
            return TYPE_PACK;
        }
    }

    @Override
    public int getItemCount() {
        return menuInfo==null?0:4;
    }
    public void setMoreMachineClickListener(MoreMachineClickListener moreMachineClickListener){
        this.moreMachineClickListener=moreMachineClickListener;
    }
    public void setMoreSingleItemClickListener(MoreSingleItemClickListener moreSingleItemClickListener){
        this.moreSingleItemClickListener=moreSingleItemClickListener;
    }
    public void setMorePackClickListener(MorePackClickListener morePackClickListener){
        this.morePackClickListener=morePackClickListener;
    }
}
