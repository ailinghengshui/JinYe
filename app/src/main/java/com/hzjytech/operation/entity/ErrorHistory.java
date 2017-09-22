package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/4/28.
 */
public class ErrorHistory {

    /**
     * vendingMachines : [{"id":48,"location":"江干区白杨街道高科技企业孵化器","vmErrors":[{"id":292,"errorCode":609,"errorDescription":"没杯","occurTime":"2017-05-02 13:54:42.0","checked":true,"recoverTime":"2017-05-03 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":293,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:06:13.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":294,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:12:04.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":295,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:21:33.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":296,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:23:45.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":297,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:29:45.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":298,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:35:45.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":299,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:39:50.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":300,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:41:52.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":301,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:44:22.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":302,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:48:30.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":303,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:51:37.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":304,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:55:16.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":305,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 15:02:04.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":306,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 15:09:42.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":307,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 15:14:11.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":365,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-16 10:52:57.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":370,"errorCode":619,"errorDescription":"咖啡机在规定时间内还没做好","occurTime":"2016-06-16 14:20:30.0","checked":true,"recoverTime":"2016-10-18 10:26:35.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":371,"errorCode":619,"errorDescription":"咖啡机在规定时间内还没做好","occurTime":"2016-06-16 14:28:18.0","checked":true,"recoverTime":"2016-10-18 10:26:35.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":621,"errorCode":620,"errorDescription":"咖啡机正在预热","occurTime":"2016-07-02 14:07:51.0","checked":true,"recoverTime":"2016-10-18 10:26:35.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"}]}]
     * page : 2
     */

    private int page;
    private List<VendingMachinesBean> vendingMachines;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<VendingMachinesBean> getVendingMachines() {
        return vendingMachines;
    }

    public void setVendingMachines(List<VendingMachinesBean> vendingMachines) {
        this.vendingMachines = vendingMachines;
    }

    public static class VendingMachinesBean {
        /**
         * id : 48
         * location : 江干区白杨街道高科技企业孵化器
         * vmErrors : [{"id":292,"errorCode":609,"errorDescription":"没杯","occurTime":"2017-05-02 13:54:42.0","checked":true,"recoverTime":"2017-05-03 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":293,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:06:13.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":294,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:12:04.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":295,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:21:33.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":296,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:23:45.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":297,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:29:45.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":298,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:35:45.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":299,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:39:50.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":300,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:41:52.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":301,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:44:22.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":302,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:48:30.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":303,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:51:37.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":304,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 14:55:16.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":305,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 15:02:04.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":306,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 15:09:42.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":307,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-07 15:14:11.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":365,"errorCode":609,"errorDescription":"没杯","occurTime":"2016-06-16 10:52:57.0","checked":true,"recoverTime":"2016-06-16 10:54:21.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":370,"errorCode":619,"errorDescription":"咖啡机在规定时间内还没做好","occurTime":"2016-06-16 14:20:30.0","checked":true,"recoverTime":"2016-10-18 10:26:35.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":371,"errorCode":619,"errorDescription":"咖啡机在规定时间内还没做好","occurTime":"2016-06-16 14:28:18.0","checked":true,"recoverTime":"2016-10-18 10:26:35.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"},{"id":621,"errorCode":620,"errorDescription":"咖啡机正在预热","occurTime":"2016-07-02 14:07:51.0","checked":true,"recoverTime":"2016-10-18 10:26:35.0","machineId":48,"location":"江干区白杨街道高科技企业孵化器"}]
         */

        private int id;
        private String location;
        private List<VmErrorsBean> vmErrors;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<VmErrorsBean> getVmErrors() {
            return vmErrors;
        }

        public void setVmErrors(List<VmErrorsBean> vmErrors) {
            this.vmErrors = vmErrors;
        }

        @Override
        public String toString() {
            return "VendingMachinesBean{" +
                    "id=" + id +
                    ", location='" + location + '\'' +
                    ", vmErrors=" + vmErrors +
                    '}';
        }

        public static class VmErrorsBean {
            public VmErrorsBean(
                    int id,
                    int errorCode,
                    String errorDescription,
                    String occurTime,
                    boolean checked,
                    String recoverTime,
                    int machineId,
                    String name,
                    String note,
                    String location) {
                this.id = id;
                this.errorCode = errorCode;
                this.errorDescription = errorDescription;
                this.occurTime = occurTime;
                this.checked = checked;
                this.recoverTime = recoverTime;
                this.machineId = machineId;
                this.note=note;
                this.name=name;
                this.location = location;
            }

            /**
             * id : 292
             * errorCode : 609
             * errorDescription : 没杯
             * occurTime : 2017-05-02 13:54:42.0
             * checked : true
             * recoverTime : 2017-05-03 10:54:21.0
             * machineId : 48
             * location : 江干区白杨街道高科技企业孵化器
             */


            private int id;
            private int errorCode;
            private String errorDescription;
            private String occurTime;
            private boolean checked;
            private String recoverTime;
            private int machineId;
            private String note;
            private String location;
            private String name;
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getErrorCode() {
                return errorCode;
            }

            public void setErrorCode(int errorCode) {
                this.errorCode = errorCode;
            }

            public String getErrorDescription() {
                return errorDescription;
            }

            public void setErrorDescription(String errorDescription) {
                this.errorDescription = errorDescription;
            }

            public String getOccurTime() {
                return occurTime;
            }

            public void setOccurTime(String occurTime) {
                this.occurTime = occurTime;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public String getRecoverTime() {
                return recoverTime;
            }

            public void setRecoverTime(String recoverTime) {
                this.recoverTime = recoverTime;
            }

            public int getMachineId() {
                return machineId;
            }

            public void setMachineId(int machineId) {
                this.machineId = machineId;
            }

            public String getLocation() {
                return location;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            @Override
            public String toString() {
                return "VmErrorsBean{" + "id=" + id + ", errorCode=" + errorCode + ", " +
                        "errorDescription='" + errorDescription + '\'' + ", occurTime='" +
                        occurTime + '\'' + ", checked=" + checked + ", recoverTime='" +
                        recoverTime + '\'' + ", machineId=" + machineId + ", note='" + note +
                        '\'' + ", location='" + location + '\'' + ", name='" + name + '\'' + '}';
            }
        }
    }

    @Override
    public String toString() {
        return "ErrorHistory{" +
                "page=" + page +
                ", vendingMachines=" + vendingMachines +
                '}';
    }
}
