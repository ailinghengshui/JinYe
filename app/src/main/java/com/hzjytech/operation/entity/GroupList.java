package com.hzjytech.operation.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hehongcan on 2017/5/11.
 */
public class GroupList implements Parcelable{
    /**
     * id : 1
     * name : techGrou
     * isSuper : false
     */

    private int id;
    private String name;
    private boolean isSuper;
    private boolean isCheck;

    public GroupList() {
    }

    public GroupList(int id, String name, boolean isSuper) {
        this.id = id;
        this.name = name;
        this.isSuper = isSuper;
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

    public boolean isIsSuper() {
        return isSuper;
    }

    public void setIsSuper(boolean isSuper) {
        this.isSuper = isSuper;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.isSuper ? (byte) 1 : (byte) 0);
    }

    protected GroupList(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.isSuper = in.readByte() != 0;
    }

    public static final Creator<GroupList> CREATOR = new Creator<GroupList>() {
        @Override
        public GroupList createFromParcel(Parcel source) {return new GroupList(source);}

        @Override
        public GroupList[] newArray(int size) {return new GroupList[size];}
    };
}
