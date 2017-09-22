package com.hzjytech.operation.entity;


/**
 * Created by hehongcan on 2017/8/23.
 */

public class DensityInfo  {


        /**
         * no : 1
         * weight : 20
         * time : 30s
         * concentration : 22
         * degreeOfGrinding : 10
         * powderWeight : 20
         */

        private Integer no;
        private Float weight;
        private Float time;
        private Float concentration;
        private Float degreeOfGrinding;
        private Float powderWeight;

    public DensityInfo(
            Integer no,
            Float weight,
            Float time,
            Float concentration,
            Float degreeOfGrinding,
            Float powderWeight) {
        this.no = no;
        this.weight = weight;
        this.time = time;
        this.concentration = concentration;
        this.degreeOfGrinding = degreeOfGrinding;
        this.powderWeight = powderWeight;
    }

    @Override
        public String toString() {
            return "{" + "no=" + no + ", weight=" + weight + ", time=" + time + ", " +
                    "concentration=" + concentration + ", degreeOfGrinding=" + degreeOfGrinding +
                    ", powderWeight=" + powderWeight + '}';
        }


}
