package com.hzjytech.operation.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hehongcan on 2017/5/2.
 */
public class GroupInfo implements Serializable {

    /**
     * basicInfo : {"name":"group2","createAt":"2016-03-15 10:12:32.0","vmTypeId":2,"vmTypeName":"jijia-2"}
     * subGroups : []
     * subMachines : [{"id":24,"name":"maotest1","address":"江干区白杨街道浙江工商大学（学正街）"}]
     * discount : 0.05
     * discountM : 10
     * discountJ : 1
     * adPics : []
     * planPromotion : []
     * beingPromotion : []
     * promotionText:"逛花市"
     * auth : ["1.0","1.1","1.2","1.3","1.4","1.5","1.6"]
     */

    private BasicInfoBean basicInfo;
    private double discount;
    private double discountM;
    private double discountJ;
    private List<SubGroupBean> subGroups;
    private List<SubMachinesBean> subMachines;
    private List<String> adPics;
    private List<PlanPromotionBean> planPromotion;
    private List<BeingPromotionBean> beingPromotion;
    private List<String> auth;
    private String promotionText;


    public BasicInfoBean getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfoBean basicInfo) {
        this.basicInfo = basicInfo;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountM() {
        return discountM;
    }

    public void setDiscountM(double discountM) {
        this.discountM = discountM;
    }

    public double getDiscountJ() {
        return discountJ;
    }

    public void setDiscountJ(double discountJ) {
        this.discountJ = discountJ;
    }

    public List<SubGroupBean> getSubGroups() {
        return subGroups;
    }

    public void setSubGroups(List<SubGroupBean> subGroups) {
        this.subGroups = subGroups;
    }

    public List<SubMachinesBean> getSubMachines() {
        return subMachines;
    }

    public void setSubMachines(List<SubMachinesBean> subMachines) {
        this.subMachines = subMachines;
    }

    public List<String> getAdPics() {
        return adPics;
    }

    public void setAdPics(List<String> adPics) {
        this.adPics = adPics;
    }

    public List<PlanPromotionBean> getPlanPromotion() {
        return planPromotion;
    }

    public void setPlanPromotion(List<PlanPromotionBean> planPromotion) {
        this.planPromotion = planPromotion;
    }

    public List<BeingPromotionBean> getBeingPromotion() {
        return beingPromotion;
    }

    public void setBeingPromotion(List<BeingPromotionBean> beingPromotion) {
        this.beingPromotion = beingPromotion;
    }

    public List<String> getAuth() {
        return auth;
    }

    public void setAuth(List<String> auth) {
        this.auth = auth;
    }


    public static class BasicInfoBean implements Serializable {
        /**
         * name : group2
         * createAt : 2016-03-15 10:12:32.0
         * vmTypeId : 2
         * vmTypeName : jijia-2
         * discountEndTime:"2017-07-22 10:10:00.0"
         * discountStartTime:"2017-07-21 10:10:00.0"
         * moneyOfEndTime:"2017-07-22 10:09:00.0"
         * moneyOfStartTime:"2017-07-21 10:09:00.0"
         */

        private String name;
        private String createAt;
        private int vmTypeId;
        private String vmTypeName;
        private String discountEndTime;
        private String discountStartTime;
        private String moneyOfEndTime;
        private String moneyOfStartTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public int getVmTypeId() {
            return vmTypeId;
        }

        public void setVmTypeId(int vmTypeId) {
            this.vmTypeId = vmTypeId;
        }

        public String getVmTypeName() {
            return vmTypeName;
        }

        public void setVmTypeName(String vmTypeName) {
            this.vmTypeName = vmTypeName;
        }

        public String getDiscountEndTime() {
            return discountEndTime;
        }

        public void setDiscountEndTime(String discountEndTime) {
            this.discountEndTime = discountEndTime;
        }

        public String getDiscountStartTime() {
            return discountStartTime;
        }

        public void setDiscountStartTime(String discountStartTime) {
            this.discountStartTime = discountStartTime;
        }

        public String getMoneyOfEndTime() {
            return moneyOfEndTime;
        }

        public void setMoneyOfEndTime(String moneyOfEndTime) {
            this.moneyOfEndTime = moneyOfEndTime;
        }

        public String getMoneyOfStartTime() {
            return moneyOfStartTime;
        }

        public void setMoneyOfStartTime(String moneyOfStartTime) {
            this.moneyOfStartTime = moneyOfStartTime;
        }
    }
  public static class PlanPromotionBean implements Serializable{

      /**
       * name : ["测试菜单1"]
       * price : [1]
       * promotionPrice : 0.01
       * needSet : true
       * id : 342
       * type : 固定数量促销
       * beginTime : 2017-09-30 09:05:00.0
       * endTime : 2017-10-05 09:05:00.0
       * count : 0
       * number : 5
       * extraNumber : 0
       */

      private double promotionPrice;
      private boolean needSet;
      private int id;
      private String type;
      private String beginTime;
      private String endTime;
      private int count;
      private int number;
      private int extraNumber;
      private List<String> name;
      private List<Double> price;

      public double getPromotionPrice() { return promotionPrice;}

      public void setPromotionPrice(double promotionPrice) { this.promotionPrice = promotionPrice;}

      public boolean isNeedSet() { return needSet;}

      public void setNeedSet(boolean needSet) { this.needSet = needSet;}

      public int getId() { return id;}

      public void setId(int id) { this.id = id;}

      public String getType() { return type;}

      public void setType(String type) { this.type = type;}

      public String getBeginTime() { return beginTime;}

      public void setBeginTime(String beginTime) { this.beginTime = beginTime;}

      public String getEndTime() { return endTime;}

      public void setEndTime(String endTime) { this.endTime = endTime;}

      public int getCount() { return count;}

      public void setCount(int count) { this.count = count;}

      public int getNumber() { return number;}

      public void setNumber(int number) { this.number = number;}

      public int getExtraNumber() { return extraNumber;}

      public void setExtraNumber(int extraNumber) { this.extraNumber = extraNumber;}

      public List<String> getName() { return name;}

      public void setName(List<String> name) { this.name = name;}

      public List<Double> getPrice() { return price;}

      public void setPrice(List<Double> price) { this.price = price;}
  }
    public static class BeingPromotionBean implements Serializable {

        /**
         * name : 热巧克力,玛琪雅朵,卡布奇诺
         * needSet : false
         * id : 162
         * type : 每日定点节日促销
         * beginTime : 2016-09-28 14:56:00
         * endTime : 2016-09-28 15:06:00
         * promotionPrice : 0.01
         * price : 2.00,1.00,1.00
         */

        private List<String> name;
        private boolean needSet;
        private int id;
        private String type;
        private String beginTime;
        private String endTime;
        private double promotionPrice;
        private List<String> price;

        public BeingPromotionBean(List<String> name, boolean needSet, int id, String type, String beginTime, String endTime, double promotionPrice, List<String> price) {
            this.name = name;
            this.needSet = needSet;
            this.id = id;
            this.type = type;
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.promotionPrice = promotionPrice;
            this.price = price;
        }

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public boolean isNeedSet() {
            return needSet;
        }

        public void setNeedSet(boolean needSet) {
            this.needSet = needSet;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public double getPromotionPrice() {
            return promotionPrice;
        }

        public void setPromotionPrice(double promotionPrice) {
            this.promotionPrice = promotionPrice;
        }

        public List<String> getPrice() {
            return price;
        }

        public void setPrice(List<String> price) {
            this.price = price;
        }
    }
    public static class SubMachinesBean implements Serializable,Parcelable{
        /**
         * id : 24
         * name : maotest1
         * address : 江干区白杨街道浙江工商大学（学正街）
         */

        private int id;
        private String name;
        private String address;
        private boolean isCheck;

        public SubMachinesBean(int id, String name, String address) {
            this.id = id;
            this.name = name;
            this.address = address;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
            dest.writeString(this.address);
            dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        }

        protected SubMachinesBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.address = in.readString();
            this.isCheck = in.readByte() != 0;
        }

        public static final Creator<SubMachinesBean> CREATOR = new Creator<SubMachinesBean>() {
            @Override
            public SubMachinesBean createFromParcel(Parcel source) {
                return new SubMachinesBean(source);
            }

            @Override
            public SubMachinesBean[] newArray(int size) {return new SubMachinesBean[size];}
        };
    }
    public static class SubGroupBean implements Serializable{
        /**
         * id:
         * name:
         */
        private int id;
        private String name;

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
    }

    public String getPromotionText() {
        return promotionText;
    }

    public void setPromotionText(String promotionText) {
        this.promotionText = promotionText;
    }
}
