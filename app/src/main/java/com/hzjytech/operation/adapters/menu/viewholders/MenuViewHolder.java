package com.hzjytech.operation.adapters.menu.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/12.
 */
    public class MenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_menu_id)
        public TextView tvMenuId;
        @BindView(R.id.iv_menu)
        public ImageView ivMenu;
        @BindView(R.id.tv_menu_name)
        public TextView tvMenuName;
        @BindView(R.id.ll_more_item_menu)
        public LinearLayout llMoreItemMenu;

        public MenuViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

}
