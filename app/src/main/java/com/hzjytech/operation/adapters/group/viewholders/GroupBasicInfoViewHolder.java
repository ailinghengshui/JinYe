package com.hzjytech.operation.adapters.group.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/2.
 */
public class GroupBasicInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_type)
        public TextView tvType;
        @BindView(R.id.tv_create_time)
        public TextView tvCreateTime;
       @BindView(R.id.ll_promotion_title)
       public LinearLayout mLlPromotionTitle;
    public GroupBasicInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
