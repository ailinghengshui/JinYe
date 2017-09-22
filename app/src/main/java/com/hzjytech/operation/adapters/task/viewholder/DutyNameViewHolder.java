package com.hzjytech.operation.adapters.task.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.task.PersonAdapter;
import com.hzjytech.operation.inter.OnRemoveClickListener;
import com.hzjytech.operation.inter.OnViewItemClickListener;
import com.hzjytech.operation.widgets.LinearLayout.LinearListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/6/19.
 */
public class DutyNameViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ll_more_person)
    LinearLayout mLlMorePerson;
    @BindView(R.id.ll_person_container)
    LinearListView mLlPersonContainer;
    private PersonAdapter mAdapter;

    public DutyNameViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setData(Context context, List<String> dutyName, final OnViewItemClickListener onViewItemClickListener,OnRemoveClickListener onRemoveClickListener) {
           mLlPersonContainer.setVisibility(View.VISIBLE);
           mLlPersonContainer.setOrientation(LinearLayout.VERTICAL);
           mAdapter = new PersonAdapter(context, dutyName,true);
           mLlPersonContainer.setAdapter(mAdapter);

       mAdapter.setOnRemoveClickListener(onRemoveClickListener);
        //添加联系人点击监听
      mLlMorePerson.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(onViewItemClickListener!=null){
                  onViewItemClickListener.click();
              }
          }
      });
    }

}
