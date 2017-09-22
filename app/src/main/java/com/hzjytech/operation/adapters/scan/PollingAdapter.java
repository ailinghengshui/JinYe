package com.hzjytech.operation.adapters.scan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.scan.viewholder.PollingFooterViewHoler;
import com.hzjytech.operation.adapters.scan.viewholder.PollingViewHolder;
import com.hzjytech.operation.entity.PollingInfo;
import com.hzjytech.operation.inter.OnAddPicClickListener;
import com.hzjytech.operation.inter.OnImageDelClickListener;
import com.hzjytech.operation.inter.OnPollingClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hehongcan on 2017/6/7.
 */
public class PollingAdapter extends RecyclerView.Adapter {
    private static final int TYPE_FOOTER =1;
    private static final int TYPE_NORMAL = 0;
    private  List<PollingInfo> list;
    private  Context context;
    private LayoutInflater mInflater;
    private OnPollingClickListener onPollingClickListener;
    private OnAddPicClickListener onAddPicClickListener;
    private List<PollingInfo> mData;
    private OnImageDelClickListener onImageDeletClickListener;

    public PollingAdapter(Context context, ArrayList<PollingInfo>mData) {
        this.context=context;
        this.mData=mData;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_NORMAL){
            View view = mInflater.inflate(R.layout.item_polling, parent, false);
            return new PollingViewHolder(view);
        }
        if(viewType==TYPE_FOOTER){
            View view = mInflater.inflate(R.layout.item_polling_footer, parent, false);
            return new PollingFooterViewHoler(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if(holder instanceof PollingViewHolder ){
        PollingViewHolder pollingViewHolder = (PollingViewHolder) holder;
        pollingViewHolder.setData(context,position,mData,onAddPicClickListener,onImageDeletClickListener);
        return;
    }
        if(holder instanceof PollingFooterViewHoler){
            PollingFooterViewHoler footerViewHoler = (PollingFooterViewHoler) holder;
            footerViewHoler.mBtPollingConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onPollingClickListener!=null){
                        onPollingClickListener.onClick();
                    }
                }
            });
            return;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mData==null||position==mData.size()){
            return TYPE_FOOTER;
        }else{
            return TYPE_NORMAL;
        }

    }

    @Override
    public int getItemCount() {
        return mData==null?1:mData.size()+1;
    }
    public void setOnPollingClickListener(OnPollingClickListener onPollingClickListener){
        this.onPollingClickListener=onPollingClickListener;
    }
    public void setOnAddPicClickListener(OnAddPicClickListener onAddPicClickListener){
        this.onAddPicClickListener=onAddPicClickListener;
    };
    public void setOnImageDeletClickListener(OnImageDelClickListener onImageDeletClickListener){
        this.onImageDeletClickListener=onImageDeletClickListener;
    }

    public void setData(List<PollingInfo> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
