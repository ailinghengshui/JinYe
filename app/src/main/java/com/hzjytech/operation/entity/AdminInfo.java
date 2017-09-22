package com.hzjytech.operation.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hehongcan on 2017/9/6.
 */

public class AdminInfo implements Parcelable{

    /**
     * adminId : 1
     * adminName : 管理员
     */

    private int adminId;
    private String adminName;

    public AdminInfo(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
    }

    public int getAdminId() { return adminId;}

    public void setAdminId(int adminId) { this.adminId = adminId;}

    public String getAdminName() { return adminName;}

    public void setAdminName(String adminName) { this.adminName = adminName;}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.adminId);
        dest.writeString(this.adminName);
    }

    protected AdminInfo(Parcel in) {
        this.adminId = in.readInt();
        this.adminName = in.readString();
    }

    public static final Creator<AdminInfo> CREATOR = new Creator<AdminInfo>() {
        @Override
        public AdminInfo createFromParcel(Parcel source) {return new AdminInfo(source);}

        @Override
        public AdminInfo[] newArray(int size) {return new AdminInfo[size];}
    };
}
