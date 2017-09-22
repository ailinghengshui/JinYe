package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/9/20.
 */

public class AddGroupInfo {

    /**
     * id : 5
     * name : techTest
     * haveSub : true
     * subGroups : [{"id":7,"name":"3.16"},{"id":8,"name":"123"}]
     */

    private int id;
    private String name;
    private boolean haveSub;
    private List<SubGroupsBean> subGroups;

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public boolean isHaveSub() { return haveSub;}

    public void setHaveSub(boolean haveSub) { this.haveSub = haveSub;}

    public List<SubGroupsBean> getSubGroups() { return subGroups;}

    public void setSubGroups(List<SubGroupsBean> subGroups) { this.subGroups = subGroups;}

    public static class SubGroupsBean {
        /**
         * id : 7
         * name : 3.16
         */

        private int id;
        private String name;

        public int getId() { return id;}

        public void setId(int id) { this.id = id;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}
    }
}
