package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/17.
 */
public class Groups {

    /**
     * id : 55
     * name : nwl7折组
     * subMachines : []
     * subGroups : [{"groupId":56,"groupName":"nwl6折组","subMachines":[{"machineId":311,"machineName":"lizhiming","franchiseeId":16,"franchiseeName":"1221","address":"地方撒"}]}]
     */

    private int id;
    private String name;
    private List<SubGroupsBean.SubMachinesBean> subMachines;
    private List<SubGroupsBean> subGroups;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubGroupsBean.SubMachinesBean> getSubMachines() {
        return subMachines;
    }

    public void setSubMachines(List<SubGroupsBean.SubMachinesBean> subMachines) {
        this.subMachines = subMachines;
    }

    public List<SubGroupsBean> getSubGroups() {
        return subGroups;
    }

    public void setSubGroups(List<SubGroupsBean> subGroups) {
        this.subGroups = subGroups;
    }

    public static class SubGroupsBean {
        /**
         * groupId : 56
         * groupName : nwl6折组
         * subMachines : [{"machineId":311,"machineName":"lizhiming","franchiseeId":16,"franchiseeName":"1221","address":"地方撒"}]
         */

        private int groupId;
        private String groupName;
        private List<SubMachinesBean> subMachines;

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public List<SubMachinesBean> getSubMachines() {
            return subMachines;
        }

        public void setSubMachines(List<SubMachinesBean> subMachines) {
            this.subMachines = subMachines;
        }

        public static class SubMachinesBean {
            /**
             * machineId : 311
             * machineName : lizhiming
             * franchiseeId : 16
             * franchiseeName : 1221
             * address : 地方撒
             */

            private int machineId;
            private String machineName;
            private int franchiseeId;
            private String franchiseeName;
            private String address;

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

            public int getFranchiseeId() {
                return franchiseeId;
            }

            public void setFranchiseeId(int franchiseeId) {
                this.franchiseeId = franchiseeId;
            }

            public String getFranchiseeName() {
                return franchiseeName;
            }

            public void setFranchiseeName(String franchiseeName) {
                this.franchiseeName = franchiseeName;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }
}
