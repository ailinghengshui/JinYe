package com.hzjytech.operation.adapters.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.home.viewholders.MachiesCountViewHolder;
import com.hzjytech.operation.adapters.home.viewholders.MachiesViewHolder;
import com.hzjytech.operation.adapters.home.viewholders.SearchViewHolder;
import com.hzjytech.operation.base.BaseViewHolder;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.Machies;
import com.hzjytech.operation.inter.MachineGroupClickListener;
import com.hzjytech.operation.inter.MachineMenuClickListener;
import com.hzjytech.operation.inter.MachineNumberClickListener;
import com.hzjytech.operation.inter.MachineStateClickListener;
import com.hzjytech.operation.inter.OnMachineItemClickListener;
import com.hzjytech.operation.inter.SearchViewClickListener;

import java.util.List;


/**
 *
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  int type;
    private View headerView;
    private View footerView;

    private Context context;
    private List<Machies.VendingMachines> vendingMachines;
    private MachineStateClickListener machineStateChangeLister;
    private MachineNumberClickListener machineNumberClickListener;
    private MachineGroupClickListener machineGroupClcikListener;
    private MachineMenuClickListener machineMenuClickListener;
    private OnMachineItemClickListener onMachinesItemClickListener;
    private SearchViewClickListener searchViewClickListener;

    public void setHomeData(Machies machies) {
        this.machies = machies;
        vendingMachines = machies.getVendingMachines();
        notifyDataSetChanged();
    }

    private Machies machies;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_SEARCH = 1;
    private static final int TYPE_MACHINES_COUNT = 2;
    private static final int TYPE_MACHINES = 3;
    private static final int TYPE_FOOTER = 4;
    private final LayoutInflater inflater;

    public HomeAdapter(Context context, Machies machies,int type) {
        this.context = context;
        this.machies = machies;
        this.type=type;
        inflater = LayoutInflater.from(context);
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new ExtraViewHolder(headerView);
            case TYPE_FOOTER:
                return new ExtraViewHolder(footerView);
            case TYPE_SEARCH:
                View searchView = inflater.inflate(R.layout.home_search, parent, false);
                return new SearchViewHolder(searchView,searchViewClickListener);
            case TYPE_MACHINES_COUNT:
                View machiesCountView = inflater.inflate(R.layout.home_machies_count, parent, false);
                return new MachiesCountViewHolder(machiesCountView,machineStateChangeLister);
            case TYPE_MACHINES:
                View machiesView = inflater.inflate(R.layout.home_machies, parent, false);
                return new MachiesViewHolder(machiesView,machineNumberClickListener,machineGroupClcikListener,machineMenuClickListener,onMachinesItemClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SearchViewHolder){
            ((SearchViewHolder) holder).setViewData(context,machies,0,getItemViewType(position),type);
        }else if(holder instanceof MachiesCountViewHolder){
            ((MachiesCountViewHolder)holder).setViewData(context,machies,1,getItemViewType(position));
        }else if(holder instanceof MachiesViewHolder){
            ((MachiesViewHolder)holder).setViewData(context,vendingMachines,position-(type==Constants.state_opteration?2:1)-(headerView==null?0:1),type);
        }
    }

/*
    private Object getItem(int position) {
        if(position==0){
            return 233;
        }else if(position==1){
            return null;
        }
        return machies.getVendingMachines().get(position-2);
    }*/


    @Override
    public int getItemCount() {
        return machies==null?0:machies.getVendingMachines().size() + (headerView == null ? 0 : 1) + (footerView == null ? 0 : 1)+(type==Constants.state_opteration?2:1);
    }

    @Override
    public int getItemViewType(int position) {
        if(headerView!=null){
            if (position == 0 ) {
                return TYPE_HEADER;
            } else if (position == getItemCount()-1 && footerView != null) {
                return TYPE_FOOTER;
            }else if(position==1){
                return TYPE_SEARCH;
            }else if(position==2){
                return type== Constants.state_opteration?TYPE_MACHINES_COUNT:TYPE_MACHINES;
            } else {
                return TYPE_MACHINES;
            }
        }else {
            if(position==0){
                return TYPE_SEARCH;
            }else if(position==1){
                return type== Constants.state_opteration?TYPE_MACHINES_COUNT:TYPE_MACHINES;
            } else {
                return TYPE_MACHINES;
            }
        }

    }
    private class ExtraViewHolder extends BaseViewHolder<Object> {
        public ExtraViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setViewData(Context mContext, Object item, int position, int viewType) {

        }
    }
    public void setSearchViewClickListener(SearchViewClickListener searchViewClickListener){
        this.searchViewClickListener=searchViewClickListener;
    }
    public void setMachineStateChangeLister(MachineStateClickListener machineStateChangeLister){
        this. machineStateChangeLister=machineStateChangeLister;
    }
    public void setMachineNumberClickListener(MachineNumberClickListener machineNumberClickListener){
        this.machineNumberClickListener=machineNumberClickListener;
    }
    public void setMachineGroupClickListener(MachineGroupClickListener machineGroupClcikListener){
        this.machineGroupClcikListener=machineGroupClcikListener;
    }
    public void setMachineMenuClickListener(MachineMenuClickListener machineMenuClickListener){
        this.machineMenuClickListener=machineMenuClickListener;
    }
    public void setOnMachinesItemClickListener(OnMachineItemClickListener onMachinesItemClickListener){
        this.onMachinesItemClickListener=onMachinesItemClickListener;
    }
}
