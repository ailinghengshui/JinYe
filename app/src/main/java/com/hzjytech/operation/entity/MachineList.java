package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/10.
 */
public class MachineList {

    /**
     * total : 311
     * machineList : [{"id":1,"name":"test001","address":"江干区下沙街道规划支路580号"},{"id":2,"name":"test0002","address":"技术部公用测试机"},{"id":3,"name":"jijia02","address":"江干区下沙街道中心路c102号"},{"id":4,"name":"jijia0101","address":"海宁市长安镇杭州九桥高尔夫俱乐部"},{"id":7,"name":"jj05710001","address":"江干区白杨街道杭州市高科技企业孵化园区"},{"id":8,"name":"jj05710002","address":"江干区白杨街道文海南路(地铁站)"},{"id":9,"name":"jj05710003","address":"江干区白杨街道杭州市高科技企业孵化园区"},{"id":10,"name":"jj05710004","address":"俞其峰测试专用"},{"id":12,"name":"jijia234","address":"江干区白杨街道科技园路31号"},{"id":13,"name":"jj05710009","address":"江干区白杨街道浙江理工大学"}]
     */

    private int total;
    private List<GroupInfo.SubMachinesBean> machineList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GroupInfo.SubMachinesBean> getMachineList() {
        return machineList;
    }

    public void setMachineList(List<GroupInfo.SubMachinesBean> machineList) {
        this.machineList = machineList;
    }

}
