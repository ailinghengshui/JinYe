package com.hzjytech.operation.widgets.dialogs;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hzjytech.operation.R;


/**
 * Created by Hades on 2016/6/2.
 */
public class TipDingDingDialog extends BaseCustomDialog{

    private String mText;
    private TextView mTv_tip_text;

    @Override
    protected void onBaseResume(Window window) {
//        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
    }
    public TipDingDingDialog(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_circle);
        return inflater.inflate(R.layout.dialog_tip_dingding,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            mTv_tip_text = (TextView) view.findViewById(R.id.tv_tip_text);
            if(mText!=null){
                mTv_tip_text.setText(mText);
            }
            TextView bt_confirm= (TextView) view.findViewById(R.id.bt_confirm);
            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    public void setText(String text) {
        mText = text;
    }
}
