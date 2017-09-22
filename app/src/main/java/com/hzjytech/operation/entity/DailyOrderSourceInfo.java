package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/7/12.
 */
public class DailyOrderSourceInfo {

    /**
     * time : 2017-03-15
     * dailyOrigin : [{"origin":"咖啡机","num":1141},{"origin":"手机端","num":30},{"origin":"活动订单",
     * "num":0}]
     */

    private String time;
    private List<DailyOriginBean> dailyOrigin;

    public String getTime() { return time;}

    public void setTime(String time) { this.time = time;}

    public List<DailyOriginBean> getDailyOrigin() { return dailyOrigin;}

    public void setDailyOrigin(List<DailyOriginBean> dailyOrigin) { this.dailyOrigin = dailyOrigin;}

    public static class DailyOriginBean {
        /**
         * origin : 咖啡机
         * num : 1141
         */

        private String origin;
        private int num;

        public String getOrigin() { return origin;}

        public void setOrigin(String origin) { this.origin = origin;}

        public int getNum() { return num;}

        public void setNum(int num) { this.num = num;}

    }

    @Override
    public String toString() {
        return "DailyOrderSourceInfo{" +
                "time='" + time + '\'' +
                ", dailyOrigin=" + dailyOrigin +
                '}';
    }
}
