package com.hzjytech.operation.scan.decoding;

import android.app.Activity;
import android.content.DialogInterface;

/**
 * Created by Hades on 2016/3/3.
 */
public final class FinishListener implements DialogInterface.OnClickListener,DialogInterface.OnCancelListener,Runnable {

    private final Activity activityToFinish;

    public FinishListener(Activity activityToFinish){
        this.activityToFinish=activityToFinish;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        run();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        run();

    }

    @Override
    public void run() {
        activityToFinish.finish();

    }
}
