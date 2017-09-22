package com.hzjytech.operation.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hehongcan on 2017/4/26.
 */
public class MachineInfo {

    /**
     * auth : ["1.0","1.1","1.2","1.3","1.4","1.5"]
     * staffInfo : [{"userId":7,"roleId":8,"userName":"yuqifeng-jialiao","roleName":"加盟商"},{"userId":25,"roleId":8,"userName":"郑昊","roleName":"加盟商"},{"userId":22,"roleId":5,"userName":"yun ","roleName":"品质主管"},{"userId":32,"roleId":1,"userName":"沈杰","roleName":"管理员"},{"userId":8,"roleId":1,"userName":"sulili","roleName":"管理员"},{"userId":27,"roleId":1,"userName":"张玲","roleName":"管理员"},{"userId":4,"roleId":1,"userName":"maotianyi","roleName":"管理员"},{"userId":20,"roleId":5,"userName":"chengmingwei","roleName":"品质主管"},{"userId":30,"roleId":1,"userName":"李志明","roleName":"管理员"},{"userId":6,"roleId":4,"userName":"yuqifeng","roleName":"维修师"},{"userId":5,"roleId":1,"userName":"波波","roleName":"管理员"}]
     * alert : [{"materialName":"糖","materialId":3,"materialType":"Material","transFactor":1.1,"alertValue":111,"magazineNumber":4},{"materialName":"奶粉","materialId":16,"materialType":"Material","transFactor":1.5,"alertValue":111,"magazineNumber":3},{"materialName":"巧克力粉","materialId":6,"materialType":"Material","transFactor":1.3,"alertValue":111,"magazineNumber":2},{"materialName":"咖啡豆","materialId":4,"materialType":"Material","transFactor":1.1,"alertValue":115,"magazineNumber":9},{"materialName":"奶茶","materialId":8,"materialType":"Material","transFactor":1.1,"alertValue":0,"magazineNumber":1},{"materialName":"水","materialId":1,"materialType":"Material","transFactor":2,"alertValue":1111,"magazineNumber":-1},{"materialName":"杯子","materialId":2,"materialType":"Material","transFactor":1,"alertValue":111,"magazineNumber":-1},{"materialName":"椰子粉","materialId":10,"materialType":"Material","transFactor":1.1,"alertValue":111,"magazineNumber":5}]
     * shelf : [{"materialName":"红茶","materialId":23,"email":0,"status":1,"shelfDays":5,"lastSet":"2016-11-09 16:10:32","magazineNumber":1},{"materialName":"糖","materialId":3,"email":0,"status":0,"lastSet":"2016-06-02 15:58:59","magazineNumber":4},{"materialName":"奶粉","materialId":16,"email":0,"status":0,"lastSet":"2016-06-02 15:58:59","magazineNumber":3},{"materialName":"巧克力粉","materialId":6,"email":0,"status":0,"lastSet":"2016-06-02 15:58:59","magazineNumber":2},{"materialName":"咖啡豆","materialId":4,"email":0,"status":0,"lastSet":"2016-06-02 15:58:59","magazineNumber":9},{"materialName":"奶茶","materialId":8,"email":0,"status":0,"lastSet":"2016-06-02 15:58:59","magazineNumber":1},{"materialName":"水","materialId":1,"email":0,"status":0,"lastSet":"2016-11-09 16:10:32","magazineNumber":-1},{"materialName":"杯子","materialId":2,"email":0,"status":0,"lastSet":"2016-06-02 15:58:59","magazineNumber":-1},{"materialName":"椰子粉","materialId":10,"email":0,"status":0,"lastSet":"","magazineNumber":5}]
     * basicInfo : {"region":"江干区","groupName":"技术部测试","phone":"17767181630","operationStatus":false,"isLocked":false,"menuId":6,"beginTime":"2015-06-29 00:00:00","version":"4.3.3","country":"中国","city":"杭州市","typeName":"jijia-3","groupId":6,"address":"江干区下沙街道规划支路580号","magazineNum":4,"franchiseeName":"pctest","province":"浙江省","brand":"极伽","franchiseeId":14,"capCaliber":80,"longitude":"120.321782","latitude":"30.302911","typeId":4,"note":"1.1.1","beansWeight":8.5,"menuName":"'技术部测试'组菜单","paymentMethod":[0,1,0]}
     * otherInfo : {"keepTemperature":95,"workingTemperature":98,"washTime":["12:00","13:20"]}
     * addMaterial : {"maintainDays":5,"basisDays":7,"status":true,"beginTime":"2016-08-11 00:00:00","record":[{"need":35,"aver":5,"materialId":13,"materialName":"糖","bag":21,"number":2}]}
     *costInfo:{"createdAt":{"date":18,"hours":17,"seconds":55,"month":4,"nanos":0,"timezoneOffset":-480,"year":117,"minutes":12,"time":1495098775000,"day":4},"groundCost":0.0,"machineCost":0.0,"deprecitionYear":2.0,"franchiseeRoyaltyRate":0.0,"operationCost":0.0,"id":6,"vendingMachineId":3,"fourGCost":0.0,"updatedAt":{"date":18,"hours":17,"seconds":48,"month":4,"nanos":0,"timezoneOffset":-480,"year":117,"minutes":20,"time":1495099248000,"day":4}}
     *  consume : ["2016-08-11 09:17:14","2016-08-11 09:17:14","2016-08-11 09:17:14","2016-08-11 09:17:14","2016-08-11 09:17:14","2016-08-11 09:17:14","2016-08-11 09:17:14","2016-08-11 09:17:14","2016-08-11 09:17:23","2016-08-11 09:17:23","2016-08-11 09:17:23","2016-08-11 09:17:23","2016-08-11 09:17:23","2016-08-11 09:17:23","2016-08-11 09:17:23","2016-08-11 09:17:23","2016-08-11 09:17:32","2016-08-11 09:17:32","2016-08-11 09:17:32","2016-08-11 09:17:32","2016-08-11 09:17:32","2016-08-11 09:17:32","2016-08-11 09:17:32","2016-08-11 09:17:32","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:34","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:35","2016-08-11 09:17:37","2016-08-11 09:17:37","2016-08-11 09:17:37","2016-08-11 09:17:37","2016-08-11 09:17:37","2016-08-11 09:17:37","2016-08-11 09:17:37","2016-08-11 09:17:37","2016-08-11 09:54:12","2016-08-11 09:54:12","2016-08-11 09:54:12","2016-08-11 09:54:12","2016-08-11 09:54:12","2016-08-11 09:54:12","2016-08-11 09:54:12","2016-08-11 09:54:12","2016-08-11 09:54:19","2016-08-11 09:54:19","2016-08-11 09:54:19","2016-08-11 09:54:19","2016-08-11 09:54:19","2016-08-11 09:54:19","2016-08-11 09:54:19","2016-08-11 09:54:19","2016-09-09 12:44:00","2016-09-09 12:44:00","2016-09-09 12:44:00","2016-09-09 12:44:00","2016-09-09 12:44:00","2016-09-09 12:44:00","2016-09-09 12:44:00","2016-09-09 12:44:00"]
     */


    private BasicInfoBean basicInfo;
    private OtherInfoBean otherInfo;
    private AddMaterialBean addMaterial;
    private List<String> auth;
    private List<StaffInfoBean> staffInfo;
    @SerializedName(value = "alert", alternate = {"alertInfo"})
    private List<AlertBean> alert;
    private List<ShelfBean> shelf;
    private List<String> consume;
    private CostInfoBean costInfo;

    public BasicInfoBean getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfoBean basicInfo) {
        this.basicInfo = basicInfo;
    }

    public OtherInfoBean getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(OtherInfoBean otherInfo) {
        this.otherInfo = otherInfo;
    }

    public AddMaterialBean getAddMaterial() {
        return addMaterial;
    }

    public void setAddMaterial(AddMaterialBean addMaterial) {
        this.addMaterial = addMaterial;
    }

    public List<String> getAuth() {
        return auth;
    }

    public void setAuth(List<String> auth) {
        this.auth = auth;
    }

    public List<StaffInfoBean> getStaffInfo() {
        return staffInfo;
    }

    public void setStaffInfo(List<StaffInfoBean> staffInfo) {
        this.staffInfo = staffInfo;
    }

    public List<AlertBean> getAlert() {
        return alert;
    }

    public void setAlert(List<AlertBean> alert) {
        this.alert = alert;
    }

    public List<ShelfBean> getShelf() {
        return shelf;
    }

    public void setShelf(List<ShelfBean> shelf) {
        this.shelf = shelf;
    }

    public List<String> getConsume() {
        return consume;
    }

    public void setConsume(List<String> consume) {
        this.consume = consume;
    }

    public CostInfoBean getCostInfo() {
        return costInfo;
    }

    public void setCostInfo(CostInfoBean costInfo) {
        this.costInfo = costInfo;
    }

    public static class BasicInfoBean implements Parcelable{
        /**
         * region : 江干区
         * groupName : 技术部测试
         * phone : 17767181630
         * operationStatus : false
         * isLocked : false
         * menuId : 6
         * beginTime : 2015-06-29 00:00:00
         * version : 4.3.3
         * country : 中国
         * city : 杭州市
         * typeName : jijia-3
         * groupId : 6
         * address : 江干区下沙街道规划支路580号
         * magazineNum : 4
         * franchiseeName : pctest
         * province : 浙江省
         * brand : 极伽
         * franchiseeId : 14
         * capCaliber : 80
         * longitude : 120.321782
         * latitude : 30.302911
         * typeId : 4
         * note : 1.1.1
         * beansWeight : 8.5
         * menuName : '技术部测试'组菜单
         * paymentMethod : [0,1,0]
         * isSupportCoffeeMe
         * openingTime
         * closingTime
         * isClose:false
         * defaultSet:0
         */

        private String region;
        private String groupName;
        private String phone;
        private boolean operationStatus;
        private boolean isLocked;
        private int menuId;
        private String name;
        private String beginTime;
        private String version;
        private String country;
        private String city;
        private String typeName;
        private int groupId;
        private String address;
        private int magazineNum;
        private String franchiseeName;
        private String province;
        private String brand;
        private int franchiseeId;
        private double capCaliber;
        private String longitude;
        private String latitude;
        private int typeId;
        private String note;
        private double beansWeight;
        private String menuName;
        private boolean isSupportCoffeeMe;
        private String openingTime;
        private String closingTime;
        private boolean isClose;
        private int defaultSet;
        @SerializedName(value = "payMethod", alternate = {"paymentMethod"})
        private List<Integer> paymentMethod;

        public int getDefaultSet() {
            return defaultSet;
        }

        public void setDefaultSet(int defaultSet) {
            this.defaultSet = defaultSet;
        }

        public static Creator<BasicInfoBean> getCREATOR() {
            return CREATOR;
        }

        public boolean isLocked() {
            return isLocked;
        }

        public void setLocked(boolean locked) {
            isLocked = locked;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public boolean isOperationStatus() {
            return operationStatus;
        }

        public void setOperationStatus(boolean operationStatus) {
            this.operationStatus = operationStatus;
        }

        public int getMenuId() {
            return menuId;
        }

        public void setMenuId(int menuId) {
            this.menuId = menuId;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getMagazineNum() {
            return magazineNum;
        }

        public void setMagazineNum(int magazineNum) {
            this.magazineNum = magazineNum;
        }

        public String getFranchiseeName() {
            return franchiseeName;
        }

        public void setFranchiseeName(String franchiseeName) {
            this.franchiseeName = franchiseeName;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getFranchiseeId() {
            return franchiseeId;
        }

        public void setFranchiseeId(int franchiseeId) {
            this.franchiseeId = franchiseeId;
        }

        public double getCapCaliber() {
            return capCaliber;
        }

        public void setCapCaliber(double capCaliber) {
            this.capCaliber = capCaliber;
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

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public double getBeansWeight() {
            return beansWeight;
        }

        public void setBeansWeight(double beansWeight) {
            this.beansWeight = beansWeight;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public List<Integer> getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(List<Integer> paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public boolean isSupportCoffeeMe() {
            return isSupportCoffeeMe;
        }

        public void setSupportCoffeeMe(boolean supportCoffeeMe) {
            isSupportCoffeeMe = supportCoffeeMe;
        }

        public String getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(String openingTime) {
            this.openingTime = openingTime;
        }

        public String getClosingTime() {
            return closingTime;
        }

        public void setClosingTime(String closingTime) {
            this.closingTime = closingTime;
        }

        public boolean isClose() {
            return isClose;
        }

        public void setClose(boolean close) {
            isClose = close;
        }



        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.region);
            dest.writeString(this.groupName);
            dest.writeString(this.phone);
            dest.writeByte(this.operationStatus ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isLocked ? (byte) 1 : (byte) 0);
            dest.writeInt(this.menuId);
            dest.writeString(this.name);
            dest.writeString(this.beginTime);
            dest.writeString(this.version);
            dest.writeString(this.country);
            dest.writeString(this.city);
            dest.writeString(this.typeName);
            dest.writeInt(this.groupId);
            dest.writeString(this.address);
            dest.writeInt(this.magazineNum);
            dest.writeString(this.franchiseeName);
            dest.writeString(this.province);
            dest.writeString(this.brand);
            dest.writeInt(this.franchiseeId);
            dest.writeDouble(this.capCaliber);
            dest.writeString(this.longitude);
            dest.writeString(this.latitude);
            dest.writeInt(this.typeId);
            dest.writeString(this.note);
            dest.writeDouble(this.beansWeight);
            dest.writeString(this.menuName);
            dest.writeByte(this.isSupportCoffeeMe ? (byte) 1 : (byte) 0);
            dest.writeString(this.openingTime);
            dest.writeString(this.closingTime);
            dest.writeByte(this.isClose ? (byte) 1 : (byte) 0);
            dest.writeInt(this.defaultSet);
            dest.writeList(this.paymentMethod);
        }

        public BasicInfoBean() {}

        protected BasicInfoBean(Parcel in) {
            this.region = in.readString();
            this.groupName = in.readString();
            this.phone = in.readString();
            this.operationStatus = in.readByte() != 0;
            this.isLocked = in.readByte() != 0;
            this.menuId = in.readInt();
            this.name = in.readString();
            this.beginTime = in.readString();
            this.version = in.readString();
            this.country = in.readString();
            this.city = in.readString();
            this.typeName = in.readString();
            this.groupId = in.readInt();
            this.address = in.readString();
            this.magazineNum = in.readInt();
            this.franchiseeName = in.readString();
            this.province = in.readString();
            this.brand = in.readString();
            this.franchiseeId = in.readInt();
            this.capCaliber = in.readDouble();
            this.longitude = in.readString();
            this.latitude = in.readString();
            this.typeId = in.readInt();
            this.note = in.readString();
            this.beansWeight = in.readDouble();
            this.menuName = in.readString();
            this.isSupportCoffeeMe = in.readByte() != 0;
            this.openingTime = in.readString();
            this.closingTime = in.readString();
            this.isClose = in.readByte() != 0;
            this.defaultSet = in.readInt();
            this.paymentMethod = new ArrayList<Integer>();
            in.readList(this.paymentMethod, Integer.class.getClassLoader());
        }

        public static final Creator<BasicInfoBean> CREATOR = new Creator<BasicInfoBean>() {
            @Override
            public BasicInfoBean createFromParcel(Parcel source) {return new BasicInfoBean(source);}

            @Override
            public BasicInfoBean[] newArray(int size) {return new BasicInfoBean[size];}
        };
    }

    public static class OtherInfoBean {
        /**
         * keepTemperature : 95
         * workingTemperature : 98
         * washTime : ["12:00","13:20"]
         */

        private int keepTemperature;
        private int workingTemperature;
        private Object washTime;

        public int getKeepTemperature() {
            return keepTemperature;
        }

        public void setKeepTemperature(int keepTemperature) {
            this.keepTemperature = keepTemperature;
        }

        public int getWorkingTemperature() {
            return workingTemperature;
        }

        public void setWorkingTemperature(int workingTemperature) {
            this.workingTemperature = workingTemperature;
        }

        public Object getWashTime() {
            return washTime;
        }

        public void setWashTime(Object washTime) {
            this.washTime = washTime;
        }
    }

    public static class AddMaterialBean {
        /**
         * maintainDays : 5
         * basisDays : 7
         * status : true
         * beginTime : 2016-08-11 00:00:00
         * record : [{"need":35,"aver":5,"materialId":13,"materialName":"糖","bag":21,"number":2}]
         */

        private int maintainDays;
        private int basisDays;
        private boolean status;
        private String beginTime;
        private List<RecordBean> record;

        public int getMaintainDays() {
            return maintainDays;
        }

        public void setMaintainDays(int maintainDays) {
            this.maintainDays = maintainDays;
        }

        public int getBasisDays() {
            return basisDays;
        }

        public void setBasisDays(int basisDays) {
            this.basisDays = basisDays;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public List<RecordBean> getRecord() {
            return record;
        }

        public void setRecord(List<RecordBean> record) {
            this.record = record;
        }

        public static class RecordBean {
            /**
             * need : 35
             * aver : 5
             * materialId : 13
             * materialName : 糖
             * bag : 21
             * number : 2
             */

            private int need;
            private int aver;
            private int materialId;
            private String materialName;
            private int bag;
            private int number;

            public int getNeed() {
                return need;
            }

            public void setNeed(int need) {
                this.need = need;
            }

            public int getAver() {
                return aver;
            }

            public void setAver(int aver) {
                this.aver = aver;
            }

            public int getMaterialId() {
                return materialId;
            }

            public void setMaterialId(int materialId) {
                this.materialId = materialId;
            }

            public String getMaterialName() {
                return materialName;
            }

            public void setMaterialName(String materialName) {
                this.materialName = materialName;
            }

            public int getBag() {
                return bag;
            }

            public void setBag(int bag) {
                this.bag = bag;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            @Override
            public String toString() {
                return "RecordBean{" +
                        "need=" + need +
                        ", aver=" + aver +
                        ", materialId=" + materialId +
                        ", materialName='" + materialName + '\'' +
                        ", bag=" + bag +
                        ", number=" + number +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "AddMaterialBean{" +
                    "maintainDays=" + maintainDays +
                    ", basisDays=" + basisDays +
                    ", status=" + status +
                    ", beginTime='" + beginTime + '\'' +
                    ", record=" + record +
                    '}';
        }
    }

    public static class StaffInfoBean implements Parcelable {
        /**
         * userId : 7
         * roleId : 8
         * userName : yuqifeng-jialiao
         * roleName : 加盟商
         */

        private int userId;
        private int roleId;
        private String userName;
        private String roleName;

        public StaffInfoBean(int userId, int roleId, String userName, String roleName) {
            this.userId = userId;
            this.roleId = roleId;
            this.userName = userName;
            this.roleName = roleName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        @Override
        public String toString() {
            return "StaffInfoBean{" +
                    "userId=" + userId +
                    ", roleId=" + roleId +
                    ", userName='" + userName + '\'' +
                    ", roleName='" + roleName + '\'' +
                    '}';
        }

        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.userId);
            dest.writeInt(this.roleId);
            dest.writeString(this.userName);
            dest.writeString(this.roleName);
        }

        protected StaffInfoBean(Parcel in) {
            this.userId = in.readInt();
            this.roleId = in.readInt();
            this.userName = in.readString();
            this.roleName = in.readString();
        }

        public static final Creator<StaffInfoBean> CREATOR = new Creator<StaffInfoBean>() {
            @Override
            public StaffInfoBean createFromParcel(Parcel source) {return new StaffInfoBean(source);}

            @Override
            public StaffInfoBean[] newArray(int size) {return new StaffInfoBean[size];}
        };
    }

    public static class AlertBean {
        /**
         * materialName : 糖
         * materialId : 3
         * materialType : Material
         * transFactor : 1.1
         * alertValue : 111
         * magazineNumber : 4
         */

        private String materialName;
        private int materialId;
        private String materialType;
        private double transFactor;
        private int alertValue;
        private int magazineNumber;

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public String getMaterialType() {
            return materialType;
        }

        public void setMaterialType(String materialType) {
            this.materialType = materialType;
        }

        public double getTransFactor() {
            return transFactor;
        }

        public void setTransFactor(double transFactor) {
            this.transFactor = transFactor;
        }

        public int getAlertValue() {
            return alertValue;
        }

        public void setAlertValue(int alertValue) {
            this.alertValue = alertValue;
        }

        public int getMagazineNumber() {
            return magazineNumber;
        }

        public void setMagazineNumber(int magazineNumber) {
            this.magazineNumber = magazineNumber;
        }

        @Override
        public String toString() {
            return "AlertBean{" +
                    "materialName='" + materialName + '\'' +
                    ", materialId=" + materialId +
                    ", materialType='" + materialType + '\'' +
                    ", transFactor=" + transFactor +
                    ", alertValue=" + alertValue +
                    ", magazineNumber=" + magazineNumber +
                    '}';
        }
    }
    public static class CostInfoBean{

        /**
         * createdAt : {"date":18,"hours":17,"seconds":55,"month":4,"nanos":0,
         * "timezoneOffset":-480,"year":117,"minutes":12,"time":1495098775000,"day":4}
         * groundCost : 0.0
         * machineCost : 0.0
         * deprecitionYear : 2.0
         * franchiseeRoyaltyRate : 0.0
         * operationCost : 0.0
         * id : 6
         * vendingMachineId : 3
         * fourGCost : 0.0
         * updatedAt : {"date":18,"hours":17,"seconds":48,"month":4,"nanos":0,
         * "timezoneOffset":-480,"year":117,"minutes":20,"time":1495099248000,"day":4}
         */

        private CreatedAtBean createdAt;
        private double groundCost;
        private double machineCost;
        private double deprecitionYear;
        private double franchiseeRoyaltyRate;
        private double operationCost;
        private int id;
        private int vendingMachineId;
        private double fourGCost;
        private UpdatedAtBean updatedAt;

        public CreatedAtBean getCreatedAt() { return createdAt;}

        public void setCreatedAt(CreatedAtBean createdAt) { this.createdAt = createdAt;}

        public double getGroundCost() { return groundCost;}

        public void setGroundCost(double groundCost) { this.groundCost = groundCost;}

        public double getMachineCost() { return machineCost;}

        public void setMachineCost(double machineCost) { this.machineCost = machineCost;}

        public double getDeprecitionYear() { return deprecitionYear;}

        public void setDeprecitionYear(double deprecitionYear) { this.deprecitionYear =
                deprecitionYear;}

        public double getFranchiseeRoyaltyRate() { return franchiseeRoyaltyRate;}

        public void setFranchiseeRoyaltyRate(double franchiseeRoyaltyRate) { this
                .franchiseeRoyaltyRate = franchiseeRoyaltyRate;}

        public double getOperationCost() { return operationCost;}

        public void setOperationCost(double operationCost) { this.operationCost = operationCost;}

        public int getId() { return id;}

        public void setId(int id) { this.id = id;}

        public int getVendingMachineId() { return vendingMachineId;}

        public void setVendingMachineId(int vendingMachineId) { this.vendingMachineId =
                vendingMachineId;}

        public double getFourGCost() { return fourGCost;}

        public void setFourGCost(double fourGCost) { this.fourGCost = fourGCost;}

        public UpdatedAtBean getUpdatedAt() { return updatedAt;}

        public void setUpdatedAt(UpdatedAtBean updatedAt) { this.updatedAt = updatedAt;}

        public static class CreatedAtBean {
            /**
             * date : 18
             * hours : 17
             * seconds : 55
             * month : 4
             * nanos : 0
             * timezoneOffset : -480
             * year : 117
             * minutes : 12
             * time : 1495098775000
             * day : 4
             */

            private int date;
            private int hours;
            private int seconds;
            private int month;
            private int nanos;
            private int timezoneOffset;
            private int year;
            private int minutes;
            private long time;
            private int day;

            public int getDate() { return date;}

            public void setDate(int date) { this.date = date;}

            public int getHours() { return hours;}

            public void setHours(int hours) { this.hours = hours;}

            public int getSeconds() { return seconds;}

            public void setSeconds(int seconds) { this.seconds = seconds;}

            public int getMonth() { return month;}

            public void setMonth(int month) { this.month = month;}

            public int getNanos() { return nanos;}

            public void setNanos(int nanos) { this.nanos = nanos;}

            public int getTimezoneOffset() { return timezoneOffset;}

            public void setTimezoneOffset(int timezoneOffset) { this.timezoneOffset =
                    timezoneOffset;}

            public int getYear() { return year;}

            public void setYear(int year) { this.year = year;}

            public int getMinutes() { return minutes;}

            public void setMinutes(int minutes) { this.minutes = minutes;}

            public long getTime() { return time;}

            public void setTime(long time) { this.time = time;}

            public int getDay() { return day;}

            public void setDay(int day) { this.day = day;}
        }

        public static class UpdatedAtBean {
            /**
             * date : 18
             * hours : 17
             * seconds : 48
             * month : 4
             * nanos : 0
             * timezoneOffset : -480
             * year : 117
             * minutes : 20
             * time : 1495099248000
             * day : 4
             */

            private int date;
            private int hours;
            private int seconds;
            private int month;
            private int nanos;
            private int timezoneOffset;
            private int year;
            private int minutes;
            private long time;
            private int day;

            public int getDate() { return date;}

            public void setDate(int date) { this.date = date;}

            public int getHours() { return hours;}

            public void setHours(int hours) { this.hours = hours;}

            public int getSeconds() { return seconds;}

            public void setSeconds(int seconds) { this.seconds = seconds;}

            public int getMonth() { return month;}

            public void setMonth(int month) { this.month = month;}

            public int getNanos() { return nanos;}

            public void setNanos(int nanos) { this.nanos = nanos;}

            public int getTimezoneOffset() { return timezoneOffset;}

            public void setTimezoneOffset(int timezoneOffset) { this.timezoneOffset = timezoneOffset;}

            public int getYear() { return year;}

            public void setYear(int year) { this.year = year;}

            public int getMinutes() { return minutes;}

            public void setMinutes(int minutes) { this.minutes = minutes;}

            public long getTime() { return time;}

            public void setTime(long time) { this.time = time;}

            public int getDay() { return day;}

            public void setDay(int day) { this.day = day;}
        }
    }

    public static class ShelfBean {
        /**
         * materialName : 红茶
         * materialId : 23
         * email : 0
         * status : 1
         * shelfDays : 5
         * lastSet : 2016-11-09 16:10:32
         * magazineNumber : 1
         */

        private String materialName;
        private int materialId;
        private int email;
        private int status;
        private int shelfDays;
        private String lastSet;
        private int magazineNumber;

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public int getEmail() {
            return email;
        }

        public void setEmail(int email) {
            this.email = email;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getShelfDays() {
            return shelfDays;
        }

        public void setShelfDays(int shelfDays) {
            this.shelfDays = shelfDays;
        }

        public String getLastSet() {
            return lastSet;
        }

        public void setLastSet(String lastSet) {
            this.lastSet = lastSet;
        }

        public int getMagazineNumber() {
            return magazineNumber;
        }

        public void setMagazineNumber(int magazineNumber) {
            this.magazineNumber = magazineNumber;
        }

        @Override
        public String toString() {
            return "ShelfBean{" +
                    "materialName='" + materialName + '\'' +
                    ", materialId=" + materialId +
                    ", email=" + email +
                    ", status=" + status +
                    ", shelfDays=" + shelfDays +
                    ", lastSet='" + lastSet + '\'' +
                    ", magazineNumber=" + magazineNumber +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MachieInfo{" +
                "basicInfo=" + basicInfo +
                ", otherInfo=" + otherInfo +
                ", addMaterial=" + addMaterial +
                ", auth=" + auth +
                ", staffInfo=" + staffInfo +
                ", alert=" + alert +
                ", shelf=" + shelf +
                ", consume=" + consume +
                '}';
    }
}
