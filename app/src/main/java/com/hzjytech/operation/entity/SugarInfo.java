package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/7/17.
 */
public class SugarInfo {

    /**
     * sugarFree : 14687
     * sugar : {"total":20,"detail":[{"sugarContent":"1份糖","num":0},{"sugarContent":"1份糖",
     * "num":20},{"sugarContent":"3份糖","num":0}]}
     */

    private int sugarFree;
    private SugarBean sugar;

    public int getSugarFree() { return sugarFree;}

    public void setSugarFree(int sugarFree) { this.sugarFree = sugarFree;}

    public SugarBean getSugar() { return sugar;}

    public void setSugar(SugarBean sugar) { this.sugar = sugar;}

    public static class SugarBean {
        /**
         * total : 20
         * detail : [{"sugarContent":"1份糖","num":0},{"sugarContent":"1份糖","num":20},
         * {"sugarContent":"3份糖","num":0}]
         */

        private int total;
        private List<DetailBean> detail;

        public int getTotal() { return total;}

        public void setTotal(int total) { this.total = total;}

        public List<DetailBean> getDetail() { return detail;}

        public void setDetail(List<DetailBean> detail) { this.detail = detail;}

        public static class DetailBean {
            /**
             * sugarContent : 1份糖
             * num : 0
             */

            private String sugarContent;
            private int num;

            public String getSugarContent() { return sugarContent;}

            public void setSugarContent(String sugarContent) { this.sugarContent = sugarContent;}

            public int getNum() { return num;}

            public void setNum(int num) { this.num = num;}
        }
    }
}
