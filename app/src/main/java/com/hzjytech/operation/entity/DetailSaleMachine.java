package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/22.
 */
public class DetailSaleMachine {

    /**
     * vendingMachines : [{"machineId":332,"machineName":"咖啡浓度测试机","vmTypeName":"jijia-3","location":"122","groupName":"test11修改名字","sum":0.1,"num":83}]
     * total : {"sum":0.1,"num":83}
     * time : 2017-04-12 09:36:01.0/2017-05-22 13:49:29.421
     */

    private TotalBean total;
    private String time;
    private List<VendingMachinesBean> vendingMachines;

    public TotalBean getTotal() {
        return total;
    }

    public void setTotal(TotalBean total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<VendingMachinesBean> getVendingMachines() {
        return vendingMachines;
    }

    public void setVendingMachines(List<VendingMachinesBean> vendingMachines) {
        this.vendingMachines = vendingMachines;
    }

    public static class TotalBean {
        /**
         * sum : 0.1
         * num : 83
         */

        private double sum;
        private int num;

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static class VendingMachinesBean {
        /**
         * machineId : 332
         * machineName : 咖啡浓度测试机
         * vmTypeName : jijia-3
         * location : 122
         * groupName : test11修改名字
         * sum : 0.1
         * num : 83
         */

        private int machineId;
        private String machineName;
        private String vmTypeName;
        private String location;
        private String groupName;
        private double sum;
        private int num;

        public int getMachineId() {
            return machineId;
        }

        public void setMachineId(int machineId) {
            this.machineId = machineId;
        }

        public String getMachineName() {
            return machineName;
        }

        public void setMachineName(String machineName) {
            this.machineName = machineName;
        }

        public String getVmTypeName() {
            return vmTypeName;
        }

        public void setVmTypeName(String vmTypeName) {
            this.vmTypeName = vmTypeName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
