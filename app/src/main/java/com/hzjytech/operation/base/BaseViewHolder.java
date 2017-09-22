package com.hzjytech.operation.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Suncloud on 2015/9/25.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private T item;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void setView(Context mContext, T item, int position, int viewType) {
        this.item = item;
        setViewData(mContext, item, position, viewType);
    }

    public T getItem() {
        return item;
    }

    public abstract void setViewData(Context mContext, T item, int position, int viewType);
}
