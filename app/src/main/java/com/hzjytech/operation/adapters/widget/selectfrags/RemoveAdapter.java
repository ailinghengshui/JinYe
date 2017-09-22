package com.hzjytech.operation.adapters.widget.selectfrags;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.group.viewholders.EditGroupHeadViewHolder;
import com.hzjytech.operation.adapters.group.viewholders.EditGroupItemViewHolder;
import com.hzjytech.operation.inter.OnInfoClickListener;

import java.util.ArrayList;

import static com.hzjytech.operation.constants.Constants.EditType.EDIT_GROUP_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.EditType.EDIT_MACHINE_FROM_GROUP;

/**
 * Created by hehongcan on 2017/9/22.
 */

public class RemoveAdapter<T> extends RecyclerView.Adapter {


    private final FragmentActivity context;
    private final LayoutInflater mInflater;
    private final ArrayList<T> list;
    private final String fragsType;
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ITEM = 1;
    private OnInfoClickListener mOnItemClickListener;

    public RemoveAdapter(
            FragmentActivity activity, ArrayList list, String fragsType) {
        this.context=activity;
        this.mInflater = LayoutInflater.from(activity);
        this.list=list;
        this.fragsType=fragsType;}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEAD){
            View view = mInflater.inflate(R.layout.edit_group_head,parent,false);
            return new EditGroupHeadViewHolder(view);
        }else{
                View view = mInflater.inflate(R.layout.edit_group_item,parent,false);
                return new EditGroupItemViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof EditGroupHeadViewHolder){
            if(fragsType.equals(EDIT_GROUP_FROM_GROUP)){
               ((EditGroupHeadViewHolder) holder).mTvAddSomething.setText(R.string.add_group);
            }else if(fragsType.equals(EDIT_MACHINE_FROM_GROUP)){
                ((EditGroupHeadViewHolder) holder).mTvAddSomething.setText(R.string.add_machine);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener!=null){
                        mOnItemClickListener.click(0);
                    }
                }
            });

        }else if(holder instanceof EditGroupItemViewHolder){
            ((EditGroupItemViewHolder)holder).setData(context,list,position,fragsType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size()+1;
    }
    public void setOnItemClickListener(OnInfoClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
