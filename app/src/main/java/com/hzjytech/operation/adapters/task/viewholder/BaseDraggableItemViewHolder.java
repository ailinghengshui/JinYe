package com.hzjytech.operation.adapters.task.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by chen_bin on 2017/3/18 0018.
 */
public abstract class BaseDraggableItemViewHolder<T> extends RecyclerView.ViewHolder {

    private T item;

    public BaseDraggableItemViewHolder(View itemView) {
        super(itemView);
    }

    public void setView(Context context, T item, int position, int viewType) {
        this.item = item;
        setViewData(context, item, position, viewType);

    }

    public T getItem() {
        return item;
    }

    public abstract void setViewData(Context context, T item, int position, int viewType);

}
