package com.hzjytech.operation.adapters.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.viewholders.ErrorListViewHolder;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.ErrorHistory;
import com.hzjytech.operation.utils.TimeUtil;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/24.
 */
public class ErrorListAdapter extends RecyclerView.Adapter {
    private  Context context;
    private List<ErrorHistory.VendingMachinesBean.VmErrorsBean> data;
    private final LayoutInflater inflater;

    public ErrorListAdapter(Context context, List<ErrorHistory.VendingMachinesBean.VmErrorsBean> data) {
           this.context=context;
            this.data=data;
        inflater = LayoutInflater.from(context);
    }
    public void setData(List<ErrorHistory.VendingMachinesBean.VmErrorsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_error_list, parent, false);
        return new ErrorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ErrorListViewHolder errorListViewHolder = (ErrorListViewHolder) holder;
        ErrorHistory.VendingMachinesBean.VmErrorsBean errorsBean = data.get(position);
        errorListViewHolder.tvErrorId.setText(errorsBean.getErrorCode()+"错误");
        errorListViewHolder.tvErrorOccureTime.setText(TimeUtil.getCorrectTimeString(errorsBean.getOccurTime()));
        errorListViewHolder.tvErrorContent.setText(errorsBean.getErrorDescription());
        errorListViewHolder.tvErrorResetTime.setText(TimeUtil.getLong2TimeFromLong(errorsBean.getRecoverTime()));
        errorListViewHolder.tv_error_machine_code.setText(errorsBean.getName());
        errorListViewHolder.tvErrorSolve.setText(errorsBean.getNote()==null?"无":errorsBean.getNote());
        errorListViewHolder.tv_error_machine_location.setText(errorsBean.getLocation());
        errorListViewHolder.ll_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity activity = (BaseActivity) ErrorListAdapter.this.context;
                activity.showTip(R.string.check_task_isnot_usable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }


}
