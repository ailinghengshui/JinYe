package com.hzjytech.operation.adapters.group.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.GroupList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hzjytech.operation.constants.Constants.EditType.EDIT_GROUP_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.EditType.EDIT_MACHINE_FROM_GROUP;

/**
 * Created by hehongcan on 2017/9/20.
 */

public class EditGroupItemViewHolder<T> extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_edit_group_id)
    TextView mTvEditGroupId;
    @BindView(R.id.iv_edit_group)
    ImageView mIvEditGroup;
    @BindView(R.id.tv_edit_machine)
    TextView mTvEditMachine;
    @BindView(R.id.tv_edit_group_name)
    TextView mTvEditGroupName;
    @BindView(R.id.tv_edit_group_note)
    TextView mTvEditGroupNote;
    @BindView(R.id.checkbox_group_isChecked)
    CheckBox checkbox_group_isChecked;


    public EditGroupItemViewHolder(
            View view) {
        super(view);
        //R.layout.edit_group_item
        ButterKnife.bind(this, view);
    }

    public void setData(
            final Context context,
            final ArrayList<T> data,
            final int position,
            final String fragsType) {
        mTvEditGroupId.setText(position + "");
        if (fragsType.equals(EDIT_GROUP_FROM_GROUP)) {
            mTvEditMachine.setVisibility(View.GONE);
            mIvEditGroup.setVisibility(View.VISIBLE);
            final GroupList bean = (GroupList) data.get(position - 1);
            mTvEditGroupName.setText(bean.getName());
            mTvEditGroupNote.setVisibility(View.GONE);
            mIvEditGroup.setImageResource(bean.isIsSuper() ? R.drawable.icon_groups : R.drawable
                    .icon_group);
            checkbox_group_isChecked.setChecked(bean.isCheck());
            checkbox_group_isChecked.setOnCheckedChangeListener(new CompoundButton
                    .OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    bean.setCheck(isChecked);
                }
            });
        } else if (fragsType.equals(EDIT_MACHINE_FROM_GROUP)) {
            mTvEditMachine.setVisibility(View.VISIBLE);
            mIvEditGroup.setVisibility(View.INVISIBLE);
            mTvEditGroupNote.setVisibility(View.VISIBLE);
            final GroupInfo.SubMachinesBean bean = (GroupInfo.SubMachinesBean) data.get(position
                    - 1);
            mTvEditMachine.setText(bean.getId() + "");
            mTvEditGroupName.setText(bean.getName());
            mTvEditGroupNote.setText(bean.getAddress());
            checkbox_group_isChecked.setChecked(bean.isCheck());
            checkbox_group_isChecked.setOnCheckedChangeListener(new CompoundButton
                    .OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    bean.setCheck(isChecked);
                }
            });

        }

    }
}
