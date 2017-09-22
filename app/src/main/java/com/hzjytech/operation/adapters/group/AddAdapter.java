package com.hzjytech.operation.adapters.group;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.group.viewholders.AddGroupItemViewHolder;
import com.hzjytech.operation.adapters.group.viewholders.EditGroupItemViewHolder;
import com.hzjytech.operation.entity.AddGroupInfo;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.GroupList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.hzjytech.operation.constants.Constants.EditType.EDIT_GROUP_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.EditType.EDIT_MACHINE_FROM_GROUP;

/**
 * Created by hehongcan on 2017/9/20.
 */

public class AddAdapter<T> extends RecyclerView.Adapter {
    private final FragmentActivity mContext;
    private final String mFragsType;
    private ArrayList<T> mData;
    private final LayoutInflater mInflater;

    public AddAdapter(FragmentActivity context, ArrayList<T> data, String fragsType) {
        this.mContext=context;
        this.mData=data;
        this.mFragsType=fragsType;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.edit_group_item,parent,false);
        return new EditGroupItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((EditGroupItemViewHolder)holder).setData(mContext,mData,position+1,mFragsType);
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public void setData(ArrayList<T> data) {
       this.mData=data;
       notifyDataSetChanged();
    }
}
