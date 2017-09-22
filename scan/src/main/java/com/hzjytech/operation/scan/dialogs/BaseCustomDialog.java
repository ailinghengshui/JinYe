package com.hzjytech.operation.scan.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;

import com.hzjytech.operation.scan.R;


/**
 * Created by Hades on 2016/8/16.
 */
public abstract class BaseCustomDialog extends DialogFragment {

    @Override
    public void onResume() {

        onBaseResume(getDialog().getWindow());

//        Window window = getDialog().getWindow();
//        Point size = new Point();
//        // Store dimensions of the screen in `size`
//        Display display = window.getWindowManager().getDefaultDisplay();
//        display.getSize(size);
//        // Set the width of the dialog proportional to 75% of the screen width
//        window.setLayout((int) (size.x * 0.875), WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.CENTER);
//        // Call super onResume after sizing
        super.onResume();
    }

    protected abstract void onBaseResume(Window window);

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations= R.style.CollectDialogAnimation;
    }
}
