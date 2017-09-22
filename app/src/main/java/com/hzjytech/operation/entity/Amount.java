package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/5/23.
 */
public class Amount {

    /**
     * time : 2017-05-20
     * sum : 0.03
     */

    private String time;
    private double sum;

    public Amount() {
    }

    public Amount(String time, double sum) {
        this.time = time;
        this.sum = sum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
