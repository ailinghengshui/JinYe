package com.hzjytech.operation.adapters.scan;

import android.content.Context;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.RecyclerViewAdapter;
import com.hzjytech.operation.adapters.data.viewholders.RecyclerViewHolder;
import com.hzjytech.operation.entity.IConsBean;
import com.hzjytech.operation.entity.StickyHeadEntity;

/**
 * Created by hehongcan on 2017/8/22.
 */

public class MenuControlAdapter extends RecyclerViewAdapter<IConsBean,StickyHeadEntity<IConsBean>> {

    public MenuControlAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_scan_menu;
    }

    @Override
    public void bindData(
            RecyclerViewHolder holder, int viewType, int position, IConsBean item) {
        holder.setText(R.id.tv_menu_control,item.getIconName());
        holder.setImageResource(R.id.iv_menu_control,item.getIcon());

    }
}
