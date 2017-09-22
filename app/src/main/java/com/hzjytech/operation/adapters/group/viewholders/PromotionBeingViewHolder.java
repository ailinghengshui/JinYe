package com.hzjytech.operation.adapters.group.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.inter.OnInfoClickListener;
import com.hzjytech.operation.utils.DensityUtil;
import com.hzjytech.operation.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/2.
 */
public class PromotionBeingViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ll_being_promotion_container)
    public LinearLayout ll_container;

    public PromotionBeingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(Context context, GroupInfo info, OnInfoClickListener onInfoClickListener) {
        List<GroupInfo.BeingPromotionBean> beingPromotion = info.getBeingPromotion();
        LayoutInflater inflater = LayoutInflater.from(context);
        //每次加入条目前需要清除以前加入的条目
        View head = ll_container.getChildAt(0);
        ll_container.removeAllViews();
        if (beingPromotion == null || beingPromotion.size() == 0 || beingPromotion.get(0)
                .getName() == null || beingPromotion.get(0)
                .getName()
                .size() == 0) {
            ll_container.removeAllViews();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0);
            ll_container.setLayoutParams(layoutParams);
            ll_container.setVisibility(View.GONE);
        } else {
            ll_container.addView(head);
            View headView = inflater.inflate(R.layout.promotion_head, null, false);
            ll_container.addView(headView, 1);
            for (int i = 0; i < beingPromotion.size(); i++) {
                List<String> nameList = beingPromotion.get(i)
                        .getName();
                for (int j = 0; j < nameList.size() - 1; j++) {
                    View view = inflater.inflate(R.layout.item_other_role, null, false);
                    ll_container.addView(view, 2);
                }

            }
            View lastView = inflater.inflate(R.layout.item_last_role, null, false);
            ll_container.addView(lastView);
            for (int i = 0; i < beingPromotion.size(); i++) {
                List<String> nameList = beingPromotion.get(i)
                        .getName();
                for (int j = 0; j < nameList.size() - 1; j++) {
                    View view = ll_container.getChildAt(j+(i*nameList.size()) + 2);
                    LinearLayout ll_other_container = (LinearLayout) view.findViewById(R.id
                            .ll_other_container);
                    ViewGroup.LayoutParams layoutParams = ll_other_container.getLayoutParams();
                    layoutParams.height = DensityUtil.dp2px(context, 66);
                    ll_other_container.setLayoutParams(layoutParams);
                    TextView tv_order = (TextView) view.findViewById(R.id.tv_order);
                    TextView tv_role = (TextView) view.findViewById(R.id.tv_role);
                    TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                    tv_order.setText(beingPromotion.get(i)
                            .getName()
                            .get(j));
                    tv_role.setText(beingPromotion.get(i)
                            .getType());
                    String beginTime = beingPromotion.get(i)
                            .getBeginTime();
                    String endTime = beingPromotion.get(i)
                            .getEndTime();
                    tv_name.setText(TimeUtil.getShort5FromLong(beginTime) + "\n" + "-" + "\n" +
                            TimeUtil.getShort5FromLong(
                            endTime));
                }

            }

            View view = ll_container.getChildAt(ll_container.getChildCount() - 1);
            LinearLayout role_container = (LinearLayout) view.findViewById(R.id.ll_role_container);
            ViewGroup.LayoutParams layoutParams = role_container.getLayoutParams();
            layoutParams.height = DensityUtil.dp2px(context, 66);
            role_container.setLayoutParams(layoutParams);
            TextView tvLastName = (TextView) view.findViewById(R.id.tv_last_name);
            TextView tvLastRole = (TextView) view.findViewById(R.id.tv_last_role);
            TextView tvLastOrder = (TextView) view.findViewById(R.id.tv_last_order);
            GroupInfo.BeingPromotionBean lastBeing = beingPromotion.get(beingPromotion.size() - 1);
            tvLastOrder.setText(lastBeing.getName()
                    .get(lastBeing.getName()
                            .size() - 1));
            tvLastRole.setText(lastBeing.getType());
            String beginTime = lastBeing.getBeginTime();
            String endTime = lastBeing.getEndTime();
            tvLastName.setText(TimeUtil.getShort5FromLong(beginTime) + "\n" + "-" + "\n" + TimeUtil.getShort5FromLong(
                    endTime));
        }
    }
}
