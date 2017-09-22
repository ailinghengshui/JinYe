package com.hzjytech.operation.adapters.data.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/16.
 */
public class HeadViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_saleList_isTenTop)
    public ImageView ivSaleListIsTenTop;
    @BindView(R.id.tv_isTenTop)
    public  TextView tvIsTenTop;

    public HeadViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);
    }
}
