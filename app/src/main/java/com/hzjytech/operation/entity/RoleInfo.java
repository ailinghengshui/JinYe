package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/9/5.
 */

public class RoleInfo {

    /**
     * id : 1
     * name : 管理员
     */

    private int id;
    private String name;

    public RoleInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}
}
