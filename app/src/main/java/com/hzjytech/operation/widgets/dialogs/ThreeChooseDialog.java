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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 三个选择项的dialog
 * Created by hehongcan on 2017/8/23.
 */

public class ThreeChooseDialog extends BaseCustomDialog {
    private static final String TITLE = "title";
    private static final String CANCELSTR = "cancelStr";
    private static final String OKSTR = "okStr";
    private static final String NOSTR = "noStr";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.btnLeft)
    TextView mBtnLeft;
    @BindView(R.id.btnMiddle)
    TextView mBtnMiddle;
    @BindView(R.id.btnRight)
    TextView mBtnRight;
    private IThreeButtonClick iThreeButtonClick;

    public static ThreeChooseDialog newInstance(
            String title,
            String cancelStr,
            String okStr,
            String noStr) {
        ThreeChooseDialog dialog = new ThreeChooseDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CANCELSTR, cancelStr);
        args.putString(OKSTR, okStr);
        args.putString(NOSTR, noStr);
        dialog.setArguments(args);
        return dialog;
    }
   public void setOnThreeClcikListener(IThreeButtonClick iThreeButtonClick){
       this.iThreeButtonClick=iThreeButtonClick;
   }
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_three_choose, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {

            if (getArguments() != null) {
                mTvTitle.setText(getArguments().getString(TITLE));
                mBtnLeft.setText(getArguments().getString(CANCELSTR));
                mBtnRight.setText(getArguments().getString(OKSTR));
                mBtnMiddle.setText(getArguments().getString(NOSTR));
                mBtnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(iThreeButtonClick!=null){
                            iThreeButtonClick.leftClick();
                        }
                    }
                });
                mBtnMiddle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(iThreeButtonClick!=null){
                            iThreeButtonClick.middleClick();
                        }
                    }
                });
                mBtnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(iThreeButtonClick!=null){
                            iThreeButtonClick.rightClick();
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
        Display display = window.getWindowManager()
                .getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setBackgroundDrawableResource(R.drawable.dialog_circle);
        window.setLayout((int) (size.x * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
    }
}
