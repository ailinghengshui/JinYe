package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/6/14.
 */
public class PersonInfo {

    /**
     * updatedAt : date-time,更新时间
     * id : 1
     * phone : 1
     * duty : string,职责
     * createdAt : date-time,创建时间
     * name : string,名字
     */

    private String updatedAt;
    private int id;
    private String phone;
    private String duty;
    private String createdAt;
    private String name;

    public String getUpdatedAt() { return updatedAt;}

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt;}

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getPhone() { return phone;}

    public void setPhone(String phone) { this.phone = phone;}

    public String getDuty() { return duty;}

    public void setDuty(String duty) { this.duty = duty;}

    public String getCreatedAt() { return createdAt;}

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}
}
