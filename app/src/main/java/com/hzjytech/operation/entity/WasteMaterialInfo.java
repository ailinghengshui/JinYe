package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/24.
 */
public class WasteMaterialInfo {


    private List<MaterialsBean> materials;

    public List<MaterialsBean> getMaterials() { return materials;}

    public void setMaterials(List<MaterialsBean> materials) { this.materials = materials;}

    public static class MaterialsBean {
        /**
         * extraConsume : 0.0
         * name : 香芋
         * consume : 857.0
         * allConsume : 857.0
         */

        private double extraConsume;
        private String name;
        private double consume;
        private double allConsume;

        public double getExtraConsume() { return extraConsume;}

        public void setExtraConsume(double extraConsume) { this.extraConsume = extraConsume;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public double getConsume() { return consume;}

        public void setConsume(double consume) { this.consume = consume;}

        public double getAllConsume() { return allConsume;}

        public void setAllConsume(double allConsume) { this.allConsume = allConsume;}
    }
}
