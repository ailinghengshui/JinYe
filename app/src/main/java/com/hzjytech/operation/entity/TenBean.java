package com.hzjytech.operation.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hehongcan on 2017/5/16.
 */
public  class TenBean implements Parcelable {
    /**
     * machineId : 288
     * machineName : 浆液型机器测试
     * vmTypeName : jijia-3
     * location : 江干区白杨街道保利·江语海
     * groupName : 明炜浆液型
     * longitude : 120.377805
     * latitude : 30.310303
     * time : 2017-02-21 15:07:08.0
     * sum : 0.25
     * num : 28
     * itemType:用于粘性控件的type
     */

    private int machineId;
    private String machineName;
    private String vmTypeName;
    private String location;
    private String groupName;
    private String longitude;
    private String latitude;
    private String time;
    private double sum;
    private int num;
    private int itemType;
    public String stickyHeadName;
    public TenBean() {}

    public TenBean(int itemType, String stickyHeadName) {
        this.itemType = itemType;
        this.stickyHeadName = stickyHeadName;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getStickyHeadName() {
        return stickyHeadName;
    }

    public void setStickyHeadName(String stickyHeadName) {
        this.stickyHeadName = stickyHeadName;
    }

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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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


    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.machineId);
        dest.writeString(this.machineName);
        dest.writeString(this.vmTypeName);
        dest.writeString(this.location);
        dest.writeString(this.groupName);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.time);
        dest.writeDouble(this.sum);
        dest.writeInt(this.num);
        dest.writeInt(this.itemType);
    }


    protected TenBean(Parcel in) {
        this.machineId = in.readInt();
        this.machineName = in.readString();
        this.vmTypeName = in.readString();
        this.location = in.readString();
        this.groupName = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.time = in.readString();
        this.sum = in.readDouble();
        this.num = in.readInt();
        this.itemType = in.readInt();
    }

    public static final Creator<TenBean> CREATOR = new Creator<TenBean>() {
        @Override
        public TenBean createFromParcel(Parcel source) {return new TenBean(source);}

        @Override
        public TenBean[] newArray(int size) {return new TenBean[size];}
    };
}

