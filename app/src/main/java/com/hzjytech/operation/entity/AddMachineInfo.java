package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/9/22.
 */

public class AddMachineInfo {

    /**
     * id : 93
     * name : test123
     * subMachines : [{"machineId":23,"machineName":"test1","address":"江干区白杨街道杭州市高科技企业孵化园区（地铁）"}]
     */

    private int id;
    private String name;
    private List<SubMachinesBean> subMachines;

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public List<SubMachinesBean> getSubMachines() { return subMachines;}

    public void setSubMachines(List<SubMachinesBean> subMachines) { this.subMachines = subMachines;}

    public static class SubMachinesBean {
        /**
         * machineId : 23
         * machineName : test1
         * address : 江干区白杨街道杭州市高科技企业孵化园区（地铁）
         */

        private int machineId;
        private String machineName;
        private String address;

        public int getMachineId() { return machineId;}

        public void setMachineId(int machineId) { this.machineId = machineId;}

        public String getMachineName() { return machineName;}

        public void setMachineName(String machineName) { this.machineName = machineName;}

        public String getAddress() { return address;}

        public void setAddress(String address) { this.address = address;}
    }
}
