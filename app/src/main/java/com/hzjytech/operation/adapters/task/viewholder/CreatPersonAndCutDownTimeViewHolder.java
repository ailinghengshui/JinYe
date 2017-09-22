package com.hzjytech.operation.adapters.task.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.inter.OnCutDownTimeClickListener;
import com.hzjytech.operation.utils.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/8/3.
 */

public class CreatPersonAndCutDownTimeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_creat_person)
    TextView mTvCreatPerson;
    @BindView(R.id.tv_cut_down_time)
    TextView mTvCutDownTime;
    @BindView(R.id.ll_cut_down_time)
    LinearLayout mLlCutDownTime;

    public CreatPersonAndCutDownTimeViewHolder(
            View view7) {
        super(view7);
        ButterKnife.bind(this, view7);
    }

    public void setData(
            String createrName,
            String cutDownTime,
            final OnCutDownTimeClickListener onCutDownTimeClickListener) {
        if(createrName!=null){
            mTvCreatPerson.setText(createrName);
        }
        if(cutDownTime!=null){
            mTvCutDownTime.setText(TimeUtil.getLong4TimeFromLong(cutDownTime));
        }
        mLlCutDownTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCutDownTimeClickListener!=null){
                    onCutDownTimeClickListener.changeCutDownTimeClick();
                }
            }
        });
    }
}
