package com.hzjytech.operation.adapters.scan;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.RecyclerViewAdapter;
import com.hzjytech.operation.adapters.data.viewholders.RecyclerViewHolder;
import com.hzjytech.operation.entity.SignInfo;
import com.hzjytech.operation.entity.StickyHeadEntity;
import com.hzjytech.operation.inter.OnButtonClickListener;
import com.hzjytech.operation.inter.OnItemClickListener;

/**
 * Created by hehongcan on 2017/8/23.
 */

public class SignAdapter extends RecyclerViewAdapter<SignInfo,StickyHeadEntity<SignInfo>> {
    private OnButtonClickListener onCommitClickListener;
    private String mButtonText;

    public SignAdapter(Context context) {
        super(context);
        mButtonText=context.getString(R.string.click_sign);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        Log.e("viewType",viewType+"");
        switch (viewType){
            case TYPE_STICKY_HEAD:
                return R.layout.item_sign_head;
            case TYPE_DATA:
                return R.layout.item_sign_data;
            case TYPE_CHECK_DATA:
                return R.layout.item_sign_check_data;
            case TYPE_FOOTER:
                return R.layout.item_sign_footer;

        }
        return 0;
    }

    @Override
    public void bindData(
            final RecyclerViewHolder holder, int viewType, int position, final SignInfo item) {
        int type = holder.getItemViewType();
        switch (type){
            case TYPE_STICKY_HEAD:
                holder.setText(R.id.tv_head,item.getName());
                break;
            case TYPE_DATA:
                holder.setText(R.id.tv_sign_data_name,item.getName());
                switch (item.getId()){
                    case 1020:
                        holder.setText(R.id.tv_sign_unit,"g");
                        break;
                    case 1021:
                        holder.setText(R.id.tv_sign_unit,"个");
                        break;
                    case 1022:
                        holder.setText(R.id.tv_sign_unit,"个");
                        break;
                }
                LinearLayout llSignData = holder.getLinearLayout(R.id.ll_sign_data);
               /* llSignData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.getEditText(R.id.et_sign_data).requestFocus();
                    }
                });*/

                holder.getEditText(R.id.et_sign_data).addTextChangedListener(new TextWatcher
                        () {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                           item.setValue(s.toString());
                    }
                });
                break;
            case TYPE_CHECK_DATA:
                setViewData(holder,item);
                break;
            case TYPE_FOOTER:
                Button button = holder.getButton(R.id.bt_sign_confirm);
                button.setText(mButtonText);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onCommitClickListener!=null){
                            onCommitClickListener.click();
                        }
                    }
                });
        }
    }
    private void setViewData(final RecyclerViewHolder holder, final SignInfo item) {
        holder.setText(R.id.tv_sign_name, item.getName());
        holder.getCheckBox(R.id.checkbox_sign)
                .setChecked(item.isSigned());
        holder.getLinearLayout(R.id.ll_check_sign)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setSigned(!item.isSigned());
                        holder.getCheckBox(R.id.checkbox_sign)
                                .setChecked(item.isSigned());
                    }
                });
    }
    public void setOnCommitClickListener(OnButtonClickListener onCommitClickListener){
        this.onCommitClickListener=onCommitClickListener;
    }

    public void setButtonText(String buttonText) {
        mButtonText = buttonText;
        notifyDataSetChanged();
    }
}
