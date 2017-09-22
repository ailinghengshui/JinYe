package com.hzjytech.operation.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/**
 * Created by hehongcan on 2017/7/12.
 */
public class MyAxisXValueFormatter implements IAxisValueFormatter {
    private  ArrayList<String> times;

    public MyAxisXValueFormatter(ArrayList<String>times) {
        this.times=times;
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return times.get((int)value);
    }
}
