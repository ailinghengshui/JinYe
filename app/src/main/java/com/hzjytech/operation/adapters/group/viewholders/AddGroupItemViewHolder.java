package com.hzjytech.operation.adapters.group.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.entity.GroupList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/9/20.
 */

public class AddGroupItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.view_group_top_grey)
    View mViewGroupTopGrey;
    @BindView(R.id.tv_add_group_id)
    TextView mTvAddGroupId;
    @BindView(R.id.iv_add_group)
    ImageView mIvAddGroup;
    @BindView(R.id.tv_add_group_name)
    TextView mTvAddGroupName;
    @BindView(R.id.tv_add_group_note)
    TextView mTvAddGroupNote;
    @BindView(R.id.checkbox_group_isChecked)
    CheckBox mCheckboxGroupIsChecked;
    @BindView(R.id.ll_more_item_group)
    LinearLayout mLlMoreItemGroup;

    public AddGroupItemViewHolder(
            View view) {
        super(view);
        //R.layout.item_add_group
        ButterKnife.bind(this, view);
    }

    public void setData(ArrayList<GroupList> list, int position) {
        if(position==0){
            mViewGroupTopGrey.setVisibility(View.VISIBLE);
        }else {
            mViewGroupTopGrey.setVisibility(View.GONE);
        }
        final GroupList groupBean = list.get(position);
        mTvAddGroupId.setText(position+1+"");
        mIvAddGroup.setImageResource(groupBean.isIsSuper()?R.drawable.icon_groups:R.drawable.icon_group);
        mTvAddGroupName.setText(groupBean.getName());
        mTvAddGroupNote.setVisibility(View.GONE);
        mCheckboxGroupIsChecked.setChecked(groupBean.isCheck());
        mCheckboxGroupIsChecked.setOnCheckedChangeListener(new CompoundButton
                .OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                groupBean.setCheck(isChecked);
            }
        });
    }
}
