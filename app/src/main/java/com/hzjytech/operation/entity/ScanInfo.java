package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/6/26.
 */
public class ScanInfo {

    /**
     * admin : {"id":108,"name":"何洪灿","role":1,"recordId":2757}
     */

    private AdminBean admin;

    public AdminBean getAdmin() { return admin;}

    public void setAdmin(AdminBean admin) { this.admin = admin;}

    public static class AdminBean {
        /**
         * id : 108
         * name : 何洪灿
         * role : 1
         * recordId : 2757
         */

        private int id;
        private String name;
        private int role;
        private int recordId;

        public int getId() { return id;}

        public void setId(int id) { this.id = id;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public int getRole() { return role;}

        public void setRole(int role) { this.role = role;}

        public int getRecordId() { return recordId;}

        public void setRecordId(int recordId) { this.recordId = recordId;}
    }
}
