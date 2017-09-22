package com.hzjytech.operation.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hehongcan on 2017/8/24.
 */

public class MaterialInfo {

    private List<InventoriesBean> inventories;
    private List<WorkContentBean> workContent;

    public List<InventoriesBean> getInventories() { return inventories;}

    public void setInventories(List<InventoriesBean> inventories) { this.inventories = inventories;}

    public List<WorkContentBean> getWorkContent() { return workContent;}

    public void setWorkContent(List<WorkContentBean> workContent) { this.workContent = workContent;}

    public static class InventoriesBean implements Parcelable{
        /**
         * material_id : 6
         * material_name : 巧克力粉
         * isSufficient : false
         * remain : 1111.0
         */

        private int material_id;
        private String material_name;
        private boolean isSufficient;
        private double remain;

        public InventoriesBean(String material_name) {
            this.material_name = material_name;
        }

        public int getMaterial_id() { return material_id;}

        public void setMaterial_id(int material_id) { this.material_id = material_id;}

        public String getMaterial_name() { return material_name;}

        public void setMaterial_name(String material_name) { this.material_name = material_name;}

        public boolean isIsSufficient() { return isSufficient;}

        public void setIsSufficient(boolean isSufficient) { this.isSufficient = isSufficient;}

        public double getRemain() { return remain;}

        public void setRemain(double remain) { this.remain = remain;}

        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.material_id);
            dest.writeString(this.material_name);
            dest.writeByte(this.isSufficient ? (byte) 1 : (byte) 0);
            dest.writeDouble(this.remain);
        }

        protected InventoriesBean(Parcel in) {
            this.material_id = in.readInt();
            this.material_name = in.readString();
            this.isSufficient = in.readByte() != 0;
            this.remain = in.readDouble();
        }

        public static final Creator<InventoriesBean> CREATOR = new Creator<InventoriesBean>() {
            @Override
            public InventoriesBean createFromParcel(Parcel source) {
                return new InventoriesBean(source);
            }

            @Override
            public InventoriesBean[] newArray(int size) {return new InventoriesBean[size];}
        };
    }

    public static class WorkContentBean {
        /**
         * value : 2
         * name : 意式浓缩称重
         */

        private int value;
        private String name;

        public int getValue() { return value;}

        public void setValue(int value) { this.value = value;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}
    }
}
