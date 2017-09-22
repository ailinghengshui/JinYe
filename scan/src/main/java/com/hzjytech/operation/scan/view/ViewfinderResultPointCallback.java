package com.hzjytech.operation.scan.view;


import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/**
 * Created by Hades on 2016/3/4.
 */
public class ViewfinderResultPointCallback implements ResultPointCallback {

    private final ViewfinderView viewfinderView;

    public ViewfinderResultPointCallback(ViewfinderView viewfinderView){
        this.viewfinderView=viewfinderView;
    }
    @Override
    public void foundPossibleResultPoint(ResultPoint point) {
        viewfinderView.addPossibleResultPoint(point);

    }
}
