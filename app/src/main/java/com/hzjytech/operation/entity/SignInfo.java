package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/8/23.
 */

public class SignInfo {
    private int id;
    private String name;
    private String value;
    private boolean isSigned;

    public SignInfo(int id,String name, boolean isSigned) {
        this.id = id;
        this.name = name;
        this.isSigned = isSigned;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

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

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }

    @Override
    public String toString() {
        return "SignInfo{" + "id=" + id + ", name='" + name + '\'' + ", isSigned=" + isSigned + '}';
    }
}
