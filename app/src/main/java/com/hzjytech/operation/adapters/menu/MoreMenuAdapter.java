package com.hzjytech.operation.adapters.menu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.home.viewholders.SearchViewHolder;
import com.hzjytech.operation.adapters.menu.viewholders.MenuViewHolder;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.MenuList;
import com.hzjytech.operation.inter.SearchViewClickListener;
import com.hzjytech.operation.module.menu.MenuActivity;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/11.
 */
public class MoreMenuAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_NORMAL = 1;
    private Context context;
    private List<MenuList> menus;
    private boolean hasHead;
    private final LayoutInflater inflater;
    private SearchViewClickListener searchViewClickListener;

    public MoreMenuAdapter(Context context, List<MenuList> menus) {
        this.context = context;
        this.menus = menus;
        inflater = LayoutInflater.from(context);
    }

    public void setMenuData(List<MenuList> menus) {
        this.menus = menus;
        notifyDataSetChanged();

    }

    public void setHasHead(boolean hasHead) {
        this.hasHead = hasHead;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View view = inflater.inflate(R.layout.home_search, parent, false);
            return new SearchViewHolder(view,searchViewClickListener);
        } else {
            View view = inflater.inflate(R.layout.item_menu_list, parent, false);
            return new MenuViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(hasHead){
            if(holder instanceof SearchViewHolder){
                SearchViewHolder searchViewHolder = (SearchViewHolder) holder;
                searchViewHolder.setViewData(context,null,0,getItemViewType(position), Constants.state_single_menu);
            }
            if(holder instanceof MenuViewHolder){
                MenuViewHolder MenuViewHolder = (MenuViewHolder) holder;
                MenuViewHolder.tvMenuId.setText(position+"");
                MenuViewHolder.tvMenuName.setText(menus.get(position-1).getName());
                MenuViewHolder.llMoreItemMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MenuActivity.class);
                        intent.putExtra("menuId",menus.get(position-1).getId());
                        context.startActivity(intent);
                    }
                });
            }

        }else{
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
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHead) {
            if (position == 0) {
                return TYPE_HEAD;
            } else {
                return TYPE_NORMAL;
            }
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return (menus == null ? 0 : menus.size()) + (hasHead ? 1 : 0);
    }


    public void setSearchViewClickListener(SearchViewClickListener searchViewClickListener){
        this.searchViewClickListener=searchViewClickListener;
    }
}
