package com.hzjytech.operation.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.hzjytech.operation.entity.WeeklySaleInfo;

import java.util.ArrayList;

/**
 * Created by hehongcan on 2017/7/13.
 */
public class MixAxisXValueFormatter implements IAxisValueFormatter {

    private  ArrayList<WeeklySaleInfo> list;

    public MixAxisXValueFormatter(ArrayList<WeeklySaleInfo> list) {
        this.list=list;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return list.get((int) value).getVmName();
    }
}
