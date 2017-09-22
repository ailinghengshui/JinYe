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


/**
 * Created by Hades on 2016/8/16.
 */
public class UpdateDialog extends BaseCustomDialog {

    private static final String TITLE = "title";
    private static final String DESC ="desc" ;
    private static final String CANCELSTR = "cancelstr";
    private static final String OKSTR = "okstr";
    private TextView tvUpdateDialogDescContainer;
    private TextView btnUpdateDialogOpleft;
    private TextView btnUpdateDialogOpright;
    private TextView tvUpdateDialogTitle;
    private ITwoButtonClick iTwoButtonClick;
    private FlikerProgressBar pb_down;


    public static UpdateDialog newInstance(String title, String desc) {
        return newInstance(title,desc,"忽略该版本","更新");
    }

    public static UpdateDialog newInstance(String title, String desc, String cancelStr, String okStr) {
        UpdateDialog updateDialog = new UpdateDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(DESC, desc);
        args.putString(CANCELSTR,cancelStr);
        args.putString(OKSTR,okStr);
        updateDialog.setArguments(args);
        return updateDialog;
    }

    public void setOnTwoClickListener(ITwoButtonClick iTwoButtonClick) {
        this.iTwoButtonClick = iTwoButtonClick;
    }

    public void setProgress(float progress){
        pb_down.setVisibility(View.VISIBLE);
        pb_down.setProgress(progress);
        btnUpdateDialogOpright.setText("后台下载");
        btnUpdateDialogOpright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnUpdateDialogOpleft.setTextColor(Color.GRAY);
        btnUpdateDialogOpleft.setClickable(false);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_update, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            tvUpdateDialogTitle = (TextView) view.findViewById(R.id.tvUpdateDialogTitle);
            tvUpdateDialogDescContainer = (TextView) view.findViewById(R.id.tvUpdateDialogDescContainer);
            btnUpdateDialogOpleft = (TextView) view.findViewById(R.id.btnUpdateDialogOpleft);
            btnUpdateDialogOpright = (TextView) view.findViewById(R.id.btnUpdateDialogOpright);
            pb_down = (FlikerProgressBar) view.findViewById(R.id.pb_down);

            if (getArguments() != null) {
                tvUpdateDialogTitle.setText(getArguments().getString(TITLE));
                tvUpdateDialogDescContainer.setText(getArguments().getString(DESC));
                pb_down.setVisibility(View.GONE);
                btnUpdateDialogOpleft.setText(getArguments().getString(CANCELSTR));
                btnUpdateDialogOpright.setText(getArguments().getString(OKSTR));
                btnUpdateDialogOpleft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iTwoButtonClick != null) {
                            iTwoButtonClick.onLeftButtonClick();
                            dismiss();
                        }
                    }
                });
                btnUpdateDialogOpright.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iTwoButtonClick != null) {
                            iTwoButtonClick.onRightButtonClick();
                        }
                    }
                });
            }

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
        window.setLayout((int) (size.x * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
    }
}
