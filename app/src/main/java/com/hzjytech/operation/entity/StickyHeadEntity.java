package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/7/21.
 */
public class StickyHeadEntity<T> {

    private  int itemType;

    private T data;

    private String stickyHeadName;

    public StickyHeadEntity(T data, int itemType, String stickyHeadName) {
        this.data = data;
        this.itemType = itemType;
        this.stickyHeadName = stickyHeadName;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStickyHeadName(String stickyHeadName) {
        this.stickyHeadName = stickyHeadName;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public T getData() {
        return data;
    }

    public String getStickyHeadName() {
        return stickyHeadName;
    }

    public int getItemType() {
        return itemType;
    }
}
