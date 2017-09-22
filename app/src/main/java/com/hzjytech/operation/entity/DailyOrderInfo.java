package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/7/12.
 */
public class DailyOrderInfo {

    private List<VolumeBean> volume;
    private List<AmountBean> amount;
    private List<OrderOrigin>mOrderOrigins;
    private List<String>strs;

    public List<OrderOrigin> getOrderOrigins() {
        return mOrderOrigins;
    }

    public void setOrderOrigins(List<OrderOrigin> orderOrigins) {
        mOrderOrigins = orderOrigins;
    }

    public List<String> getStrs() {
        return strs;
    }

    public void setStrs(List<String> strs) {
        this.strs = strs;
    }

    public List<VolumeBean> getVolume() { return volume;}

    public void setVolume(List<VolumeBean> volume) { this.volume = volume;}

    public List<AmountBean> getAmount() { return amount;}

    public void setAmount(List<AmountBean> amount) { this.amount = amount;}

    public static class VolumeBean {
        /**
         * time : 2017-03-14
         * orderNum : 1056
         * cupNum : 1380
         */

        private String time;
        private int orderNum;
        private int cupNum;

        public String getTime() { return time;}

        public void setTime(String time) { this.time = time;}

        public int getOrderNum() { return orderNum;}

        public void setOrderNum(int orderNum) { this.orderNum = orderNum;}

        public int getCupNum() { return cupNum;}

        public void setCupNum(int cupNum) { this.cupNum = cupNum;}

        @Override
        public String toString() {
            return "VolumeBean{" +
                    "time='" + time + '\'' +
                    ", orderNum=" + orderNum +
                    ", cupNum=" + cupNum +
                    '}';
        }
    }

    public static class AmountBean {
        /**
         * time : 2017-03-14
         * sum : 6607.91
         */

        private String time;
        private double sum;

        public String getTime() { return time;}

        public void setTime(String time) { this.time = time;}

        public double getSum() { return sum;}

        public void setSum(double sum) { this.sum = sum;}

        @Override
        public String toString() {
            return "AmountBean{" +
                    "time='" + time + '\'' +
                    ", sum=" + sum +
                    '}';
        }
    }
    public static class OrderOrigin{

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

        public void setDailyOrigin(List<DailyOriginBean> dailyOrigin) { this.dailyOrigin =
                dailyOrigin;}

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

            @Override
            public String toString() {
                return "DailyOriginBean{" +
                        "origin='" + origin + '\'' +
                        ", num=" + num +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "OrderOrigin{" +
                    "time='" + time + '\'' +
                    ", dailyOrigin=" + dailyOrigin +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DailyOrderInfo{" +
                "volume=" + volume +
                ", amount=" + amount +
                ", mOrderOrigins=" + mOrderOrigins +
                ", strs=" + strs +
                '}';
    }
}
