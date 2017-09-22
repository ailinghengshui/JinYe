package com.hzjytech.operation.scan.dialogs;
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

import com.hzjytech.operation.scan.R;

/**
 * Created by Hades on 2016/8/16.
 */
public class TitleButtonsDialog extends BaseCustomDialog {

    private static final String TITLE = "title";
    private static final String OKSTR = "okstr";
    private static TitleButtonsDialog mInstance;
    private TextView btnUpdateDialogOpright;
    private TextView tvUpdateDialogTitle;
    private IButtonClick iButtonClick;

    public TitleButtonsDialog(){

    }

    public static TitleButtonsDialog getInstance (){
        if(mInstance==null){
            mInstance=new TitleButtonsDialog();
        }

        return mInstance;

    }


    public static TitleButtonsDialog newInstance(String title) {
        return newInstance(title,"扫一扫");
    }

    public static TitleButtonsDialog newInstance(String title, String okStr) {
        TitleButtonsDialog updateDialog = getInstance();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(OKSTR,okStr);
        updateDialog.setArguments(args);
        return updateDialog;
    }

    public void setOnButtonClickListener(IButtonClick iButtonClick) {
        this.iButtonClick = iButtonClick;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_circle);
        return inflater.inflate(R.layout.dialog_title_button, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            tvUpdateDialogTitle = (TextView) view.findViewById(R.id.tvUpdateDialogTitle);
            btnUpdateDialogOpright = (TextView) view.findViewById(R.id.btnUpdateDialogOpright);
            if (getArguments() != null) {
                tvUpdateDialogTitle.setText(getArguments().getString(TITLE));
                btnUpdateDialogOpright.setText(getArguments().getString(OKSTR));
                btnUpdateDialogOpright.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iButtonClick != null) {
                            iButtonClick.onButtonClick();
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
