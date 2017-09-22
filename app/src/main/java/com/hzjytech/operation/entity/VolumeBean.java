package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/5/22.
 */



    public  class VolumeBean {
        /**
         * time : 2017-04-12
         * orderNum : 10
         * cupNum : 83
         */

        private String time;
        private int orderNum;
        private int cupNum;

    public VolumeBean() {
    }

    public VolumeBean(String time, int orderNum, int cupNum) {
        this.time = time;
        this.orderNum = orderNum;
        this.cupNum = cupNum;
    }

    public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public int getCupNum() {
            return cupNum;
        }

        public void setCupNum(int cupNum) {
            this.cupNum = cupNum;
        }

}
