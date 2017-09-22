package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/7/14.
 */
public class RepeatInfo {

    /**
     * cupNum : 1
     * peopleNum : 2
     */

    private int cupNum;
    private int peopleNum;

    public int getCupNum() { return cupNum;}

    public void setCupNum(int cupNum) { this.cupNum = cupNum;}

    public int getPeopleNum() { return peopleNum;}

    public void setPeopleNum(int peopleNum) { this.peopleNum = peopleNum;}

    @Override
    public String toString() {
        return "RepeatInfo{" +
                "cupNum=" + cupNum +
                ", peopleNum=" + peopleNum +
                '}';
    }
}
