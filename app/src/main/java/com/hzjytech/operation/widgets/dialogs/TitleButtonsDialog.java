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
 * Created by Hades on 2016/8/16.
 */
public class TitleButtonsDialog extends BaseCustomDialog {

    private static final String TITLE = "title";
    private static final String CANCELSTR = "cancelstr";
    private static final String OKSTR = "okstr";
    private static TitleButtonsDialog mInstance;
    private TextView btnUpdateDialogOpleft;
    private TextView btnUpdateDialogOpright;
    private TextView tvUpdateDialogTitle;
    private ITwoButtonClick iTwoButtonClick;

    public TitleButtonsDialog(){

    }

    public static TitleButtonsDialog getInstance (){
        if(mInstance==null){
            mInstance=new TitleButtonsDialog();
        }

        return mInstance;

    }


    public static TitleButtonsDialog newInstance(String title) {
        return newInstance(title,"忽略该版本","更新");
    }

    public static TitleButtonsDialog newInstance(String title, String cancelStr, String okStr) {
        TitleButtonsDialog updateDialog = getInstance();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CANCELSTR,cancelStr);
        args.putString(OKSTR,okStr);
        updateDialog.setArguments(args);
        return updateDialog;
    }

    public void setOnTwoClickListener(ITwoButtonClick iTwoButtonClick) {
        this.iTwoButtonClick = iTwoButtonClick;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_circle);
        return inflater.inflate(R.layout.dialog_title_buttons, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            tvUpdateDialogTitle = (TextView) view.findViewById(R.id.tvUpdateDialogTitle);
            btnUpdateDialogOpleft = (TextView) view.findViewById(R.id.btnUpdateDialogOpleft);
            btnUpdateDialogOpright = (TextView) view.findViewById(R.id.btnUpdateDialogOpright);


            if (getArguments() != null) {
                tvUpdateDialogTitle.setText(getArguments().getString(TITLE));

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
                            dismiss();
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
        window.setLayout((int) (size.x * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
    }
}
