package com.hzjytech.operation.adapters.scan.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hzjytech.operation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/6/7.
 */
public class PollingFooterViewHoler extends RecyclerView.ViewHolder {
    @BindView(R.id.bt_polling_confirm)
   public Button mBtPollingConfirm;

    public PollingFooterViewHoler(View view) {
        super(view);
        ButterKnife.bind(this,view);
    }
}
