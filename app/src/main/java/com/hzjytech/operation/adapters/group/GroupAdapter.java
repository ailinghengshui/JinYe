package com.hzjytech.operation.adapters.group;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.group.viewholders.GroupBasicInfoViewHolder;
import com.hzjytech.operation.adapters.group.viewholders.GroupContainerViewHolder;
import com.hzjytech.operation.adapters.group.viewholders.GroupPicViewHolder;
import com.hzjytech.operation.adapters.group.viewholders.GroupSettingViewHolder;
import com.hzjytech.operation.adapters.group.viewholders.MachiesContainerViewHolder;
import com.hzjytech.operation.adapters.group.viewholders.PromotionBeingViewHolder;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.inter.MoreGroupClickListener;
import com.hzjytech.operation.inter.MoreMachineClickListener;
import com.hzjytech.operation.inter.OnInfoClickListener;
import com.hzjytech.operation.utils.TimeUtil;

/**
 * Created by hehongcan on 2017/5/2.
 */
public class GroupAdapter extends RecyclerView.Adapter {
    private static final int TYPE_BASIC_INFO = 1;
    private static final int TYPE_BEING_PROMOTION_GROUP = 2;
    private static final int TYPE_CONTAIN_GROUP = 3;
    private static final int TYPE_CONTAIN_MACHINE = 4;
    private static final int TYPE_SETTING = 5;
    private static final int TYPE_PICTURE = 6;
    private Context context;
    private GroupInfo groupInfo;
    private final LayoutInflater inflater;
    private MoreMachineClickListener moreMachineClickListener;
    private MoreGroupClickListener moreGroupClickListener;
    private OnInfoClickListener mOnInfoClickListener;

    public GroupAdapter(Context context, GroupInfo groupInfo) {
        this.context = context;
        this.groupInfo = groupInfo;
        inflater = LayoutInflater.from(context);
    }

    public void setGroupData(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_BASIC_INFO:
                View view = inflater.inflate(R.layout.item_group_basic_info, parent, false);
                return new GroupBasicInfoViewHolder(view);
            case TYPE_SETTING:
                View view4 = inflater.inflate(R.layout.item_group_setting, parent, false);
                return new GroupSettingViewHolder(view4);
            case TYPE_BEING_PROMOTION_GROUP:
                View view1 = inflater.inflate(R.layout.being_promotion_container, parent, false);
                return new PromotionBeingViewHolder(view1);
            case TYPE_CONTAIN_GROUP:
                View view2 = inflater.inflate(R.layout.item_group_container, parent, false);
                return new GroupContainerViewHolder(view2);
            case TYPE_CONTAIN_MACHINE:
                View view3 = inflater.inflate(R.layout.item_machines_container, parent, false);
                return new MachiesContainerViewHolder(view3);
            case TYPE_PICTURE:
                View view5 = inflater.inflate(R.layout.item_group_picture, parent, false);
                return new GroupPicViewHolder(view5);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupBasicInfoViewHolder) {
            GroupBasicInfoViewHolder basicInfoHolder = (GroupBasicInfoViewHolder) holder;
            if (groupInfo.getBeingPromotion() != null && groupInfo.getBeingPromotion()
                    .size() > 0) {
                basicInfoHolder.mLlPromotionTitle.setVisibility(View.VISIBLE);
            } else {
                basicInfoHolder.mLlPromotionTitle.setVisibility(View.GONE);
            }
            basicInfoHolder.tvType.setText(groupInfo.getBasicInfo()
                    .getVmTypeName());
            String createAt = groupInfo.getBasicInfo()
                    .getCreateAt();
            basicInfoHolder.tvCreateTime.setText(TimeUtil.getCorrectTimeString(createAt));
        } else if (holder instanceof PromotionBeingViewHolder) {
            PromotionBeingViewHolder promotionBeingViewHolder = (PromotionBeingViewHolder) holder;
            promotionBeingViewHolder.setData(context, groupInfo,mOnInfoClickListener);
        } else if (holder instanceof GroupContainerViewHolder) {
            GroupContainerViewHolder groupContainerViewHolder = (GroupContainerViewHolder) holder;
            groupContainerViewHolder.setData(context,
                    groupInfo.getSubGroups(),
                    mOnInfoClickListener);
        } else if (holder instanceof MachiesContainerViewHolder) {
            MachiesContainerViewHolder machiesContainerViewHolder = (MachiesContainerViewHolder)
                    holder;
            machiesContainerViewHolder.setData(context,
                    groupInfo.getSubMachines(),
                    mOnInfoClickListener);
        } else if (holder instanceof GroupSettingViewHolder) {
            GroupSettingViewHolder groupSettingViewHolder = (GroupSettingViewHolder) holder;
            groupSettingViewHolder.setData(context, groupInfo,mOnInfoClickListener);
        } else if (holder instanceof GroupPicViewHolder) {
            GroupPicViewHolder picViewHolder = (GroupPicViewHolder) holder;
            picViewHolder.setData(context, groupInfo.getAdPics(),mOnInfoClickListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BASIC_INFO;
        } else if (position == 1) {
            return TYPE_BEING_PROMOTION_GROUP;
        } else if (position == 2) {
            return TYPE_SETTING;
        } else if (position == 3) {
            return TYPE_PICTURE;
        } else if (position == 4) {
            return TYPE_CONTAIN_GROUP;
        } else {
            return TYPE_CONTAIN_MACHINE;
        }
    }

    @Override
    public int getItemCount() {
        return groupInfo == null ? 0 : 6;
    }
   public void setOnEditInfoClickListener(OnInfoClickListener onInfoClickListener){
       this.mOnInfoClickListener=onInfoClickListener;
   }

}
