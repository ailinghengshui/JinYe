package com.hzjytech.operation.adapters.scan;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.RecyclerViewAdapter;
import com.hzjytech.operation.adapters.data.viewholders.RecyclerViewHolder;
import com.hzjytech.operation.entity.MaterialInfo.InventoriesBean;
import com.hzjytech.operation.entity.StickyHeadEntity;
import com.hzjytech.operation.inter.OnButtonClickListener;

/**
 * Created by hehongcan on 2017/8/25.
 */

public class AddDosingAdapter extends RecyclerViewAdapter<InventoriesBean,StickyHeadEntity<InventoriesBean>>{
    private OnButtonClickListener mOnButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }

    public AddDosingAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        switch (viewType){
            case RecyclerViewAdapter.TYPE_STICKY_HEAD:
                return R.layout.item_sign_head;
            case RecyclerViewAdapter.TYPE_DATA:
                return R.layout.item_add_dosing_data;
            case RecyclerViewAdapter.TYPE_FOOTER:
                return R.layout.item_sign_footer;
        }
        return 0;
    }

    @Override
    public void bindData(
            RecyclerViewHolder holder, int viewType, int position, final InventoriesBean item) {
switch (viewType){
    case TYPE_STICKY_HEAD:
        holder.setText(R.id.tv_head,item.getMaterial_name());
        break;
    case TYPE_DATA:
        holder.setText(R.id.tv_add_dosing_name,item.getMaterial_name());
        if(item.getMaterial_name().equals("杯子")){
            holder.setText(R.id.tv_add_dosing_unit, "个");
        }else if(item.getMaterial_name().equals("水")){
            holder.setText(R.id.tv_add_dosing_unit,"ml");
        }else{
            holder.setText(R.id.tv_add_dosing_unit,"g");
        }
        EditText editText = holder.getEditText(R.id.et_add_dosing_value);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    item.setRemain(0);
                }else{
                    item.setRemain(Double.valueOf(s.toString()));
                }

            }
        });
        break;
    case TYPE_FOOTER:
        if(mOnButtonClickListener!=null){
            mOnButtonClickListener.click();
        }
        break;

}
    }
}
