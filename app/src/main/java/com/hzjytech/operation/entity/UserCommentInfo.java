package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/8/4.
 */

public class UserCommentInfo {

    private List<UserEvaluationRateBean> userEvaluationRate;

    public List<UserEvaluationRateBean> getUserEvaluationRate() { return userEvaluationRate;}

    public void setUserEvaluationRate(List<UserEvaluationRateBean> userEvaluationRate) { this
            .userEvaluationRate = userEvaluationRate;}

    public static class UserEvaluationRateBean {
        /**
         * very_satisfied : integer,非常满意
         * keep_trying : integer,继续努力
         * not_very_satisfied : integer,不太满意
         * machineCode : string,咖啡机编码
         */

        private int very_satisfied;
        private int keep_trying;
        private int not_very_satisfied;
        private String machineCode;

        public int getVery_satisfied() { return very_satisfied;}

        public void setVery_satisfied(int very_satisfied) { this.very_satisfied = very_satisfied;}

        public int getKeep_trying() { return keep_trying;}

        public void setKeep_trying(int keep_trying) { this.keep_trying = keep_trying;}

        public int getNot_very_satisfied() { return not_very_satisfied;}

        public void setNot_very_satisfied(int not_very_satisfied) { this.not_very_satisfied =
                not_very_satisfied;}

        public String getMachineCode() { return machineCode;}

        public void setMachineCode(String machineCode) { this.machineCode = machineCode;}
    }
}
