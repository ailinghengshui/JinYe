package com.hzjytech.operation.widgets.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.Window;

import com.hzjytech.operation.R;


/**
 * Created by Hades on 2016/8/16.
 */
public abstract class BaseForceCustomDialog extends DialogFragment {

    @Override
    public void onResume() {

        onBaseResume(getDialog().getWindow());

        // Call super onResume after sizing
        super.onResume();
    }

    protected abstract void onBaseResume(Window window);

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_SEARCH||keyCode==KeyEvent.KEYCODE_BACK)
                {
                    return true;
                }
                else
                {
                    return false; //默认返回 false
                }
            }
        });
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations= R.style.CollectDialogAnimation;
    }
}
