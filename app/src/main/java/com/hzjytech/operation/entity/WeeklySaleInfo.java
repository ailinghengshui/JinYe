package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/7/12.
 */
public class WeeklySaleInfo {

    /**
     * vmId : 357
     * vmName : 三金测试
     * iceSalesNum : 15
     * iceSalesSum : 1.6
     * hotSalesNum : 168
     */

    private int vmId;
    private String vmName;
    private int iceSalesNum;
    private double salesSum;
    private int hotSalesNum;

    public int getVmId() { return vmId;}

    public void setVmId(int vmId) { this.vmId = vmId;}

    public String getVmName() { return vmName;}

    public void setVmName(String vmName) { this.vmName = vmName;}

    public int getIceSalesNum() { return iceSalesNum;}

    public void setIceSalesNum(int iceSalesNum) { this.iceSalesNum = iceSalesNum;}

    public double getSalesSum() { return salesSum;}

    public void setSalesSum(double salesSum) { this.salesSum = salesSum;}

    public int getHotSalesNum() { return hotSalesNum;}

    public void setHotSalesNum(int hotSalesNum) { this.hotSalesNum = hotSalesNum;}
}
