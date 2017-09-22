package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/4/21.
 */
public class MachiesAmountInfo  {
    private String machiesStatu;
    private int amount;

    public MachiesAmountInfo(String machiesStatu, int amount) {
        this.machiesStatu = machiesStatu;
        this.amount = amount;
    }

    public String getMachiesStatu() {
        return machiesStatu;
    }

    public void setMachiesStatu(String machiesStatu) {
        this.machiesStatu = machiesStatu;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MachiesAmountInfo{" +
                "machiesStatu='" + machiesStatu + '\'' +
                ", amount=" + amount +
                '}';
    }
}
