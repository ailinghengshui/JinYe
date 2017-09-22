package com.hzjytech.operation.widgets.dialogs;

import android.graphics.Color;
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
import com.hzjytech.operation.inter.OnSelectClickListener;

/**
 * Created by hehongcan on 2017/7/20.
 */
public class SelectDialog extends BaseCustomDialog {

    private TextView tvGaoDe;
    private TextView tvBaidu;
    private TextView tvCancelMap;
    private OnSelectClickListener onSelectClickListener;


    public static SelectDialog newInstance() {
        return new SelectDialog();
    }

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        this.onSelectClickListener=onSelectClickListener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_select, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            tvGaoDe = (TextView) view.findViewById(R.id.tv_gaode);
            tvBaidu = (TextView) view.findViewById(R.id.tv_baidu);
            tvCancelMap = (TextView) view.findViewById(R.id.tv_cancel_map);
            tvGaoDe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelectClickListener != null) {
                        onSelectClickListener.select(0);
                    }
                }
            });
            tvBaidu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelectClickListener != null) {
                        onSelectClickListener.select(1);
                    }
                }
            });
            tvCancelMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelectClickListener != null) {
                        onSelectClickListener.select(2);
                    }
                }
            });

        }
    }

    @Override
    protected void onBaseResume(Window window) {
        //        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setBackgroundDrawableResource(R.drawable.dialog_circle);
        window.setLayout((int) (size.x * 0.65), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
    }
}
