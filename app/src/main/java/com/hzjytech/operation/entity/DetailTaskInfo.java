package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/6/19.
 */
public class DetailTaskInfo {

    /**
     * taskCommentDOList : [{"id":"string,评论id","createdAt":"string,评论时间","createrName":"string,
     * 姓名","comment":"string,内容","url":"string,URL"}]
     * status : 1
     * creatAt:
     * id:
     * createrName : string,发布者
     * dutyName : []
     * type : 1
     * cutOffTime:null
     * taskHistoryDOList : [{"updatedAt":"date-time,修改时间","status":1,"name":"string,修改任务状态人name",
     * "createrId":"integer,修改任务状态人id"}]
     * vendingMachineDo : {"groupName":"string,促销分组","address":"string,地址","name":"string,咖啡机编号",
     * "longitude":"string,经度","latitude":"string,纬度","version":"string,当前版本","menuName":"string,菜单"}
     * "duty":[{"dutyId":"103","dutyName":"刘蔺"}]
     */
    private int id;
    private String createdAt;
    private int status;
    private String createrName;
    private String cutOffTime;
    private int type;
    private VendingMachineBeanDO vendingMachineDO;
    private List<TaskCommentDOListBean> taskCommentDOList;
    private List<String> dutyNames;
    private List<TaskHistoryDOListBean> taskHistoryDOList;
    private List<Duty>duty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(String cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    public int getStatus() { return status;}

    public void setStatus(int status) { this.status = status;}

    public String getCreaterName() { return createrName;}

    public void setCreaterName(String createrName) { this.createrName = createrName;}

    public int getType() { return type;}

    public void setType(int type) { this.type = type;}

    public VendingMachineBeanDO getVendingMachineDO() { return vendingMachineDO;}

    public void setVendingMachineDO(VendingMachineBeanDO vendingMachineDO) { this.vendingMachineDO =
            vendingMachineDO;}

    public List<TaskCommentDOListBean> getTaskCommentDOList() { return taskCommentDOList;}

    public void setTaskCommentDOList(List<TaskCommentDOListBean> taskCommentDOList) { this
            .taskCommentDOList = taskCommentDOList;}

    public List<String> getDutyNames() { return dutyNames;}

    public void setDutyNames(List<String> dutyNames) { this.dutyNames = dutyNames;}

    public List<TaskHistoryDOListBean> getTaskHistoryDOList() { return taskHistoryDOList;}

    public void setTaskHistoryDOList(List<TaskHistoryDOListBean> taskHistoryDOList) { this
            .taskHistoryDOList = taskHistoryDOList;}

    public List<Duty> getDuty() {
        return duty;
    }

    public void setDuty(List<Duty> duty) {
        this.duty = duty;
    }

    public static class VendingMachineBeanDO {
        /**
         * "id":311
         * groupName : string,促销分组
         * address : string,地址
         * name : string,咖啡机编号
         * longitude : string,经度
         * latitude : string,纬度
         * version : string,当前版本
         * menuName : string,菜单
         */
        private int id;
        private String groupName;
        private String address;
        private String name;
        private String longitude;
        private String latitude;
        private String version;
        private String menuName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGroupName() { return groupName;}

        public void setGroupName(String groupName) { this.groupName = groupName;}

        public String getAddress() { return address;}

        public void setAddress(String address) { this.address = address;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public String getLongitude() { return longitude;}

        public void setLongitude(String longitude) { this.longitude = longitude;}

        public String getLatitude() { return latitude;}

        public void setLatitude(String latitude) { this.latitude = latitude;}

        public String getVersion() { return version;}

        public void setVersion(String version) { this.version = version;}

        public String getMenuName() { return menuName;}

        public void setMenuName(String menuName) { this.menuName = menuName;}
    }
   public static class Duty{

       /**
        * dutyId : 103
        * dutyName : 刘蔺
        */

       private String dutyId;
       private String dutyName;

       public String getDutyId() { return dutyId;}

       public void setDutyId(String dutyId) { this.dutyId = dutyId;}

       public String getDutyName() { return dutyName;}

       public void setDutyName(String dutyName) { this.dutyName = dutyName;}

       @Override
       public String toString() {
           return "Duty{" + "dutyId='" + dutyId + '\'' + ", dutyName='" + dutyName + '\'' + '}';
       }
   }
    public static class TaskCommentDOListBean {
        /**
         * id : string,评论id
         * createdAt : string,评论时间
         * createrName : string,姓名
         * comment : string,内容
         * url : string,URL
         */

        private int id;
        private String createdAt;
        private String createrName;
        private String comment;
        private String url;

        public int getId() { return id;}

        public void setId(int id) { this.id = id;}

        public String getCreatedAt() { return createdAt;}

        public void setCreatedAt(String createdAt) { this.createdAt = createdAt;}

        public String getCreaterName() { return createrName;}

        public void setCreaterName(String createrName) { this.createrName = createrName;}

        public String getComment() { return comment;}

        public void setComment(String comment) { this.comment = comment;}

        public String getUrl() { return url;}

        public void setUrl(String url) { this.url = url;}
    }
    public static class TaskHistoryDOListBean {


        /**
         * createrId : integer,修改任务状态人id
         * status : integer,修改后的状态
         * updatedAt : date-time,修改时间
         * name : string,修改任务状态人name
         * createAt : date-time,创建时间
         * cutOffTime : date-time,截止时间（如果为空代表不是截止时间，具有标识性）
         * type : integer,历史记录类型（1 新建任务状态修改,2 截止时间修改,3 联系人被改变）
         * dutyName : string,责任人姓名
         * addOrSubtract:true,责任人是删除还是添加
         */

        private int createrId;
        private int status;
        private String updatedAt;
        private String name;
        private String createAt;
        private String cutOffTime;
        private int type;
        private String dutyName;
        private boolean addOrSubtract;

        public int getCreaterId() { return createrId;}

        public void setCreaterId(int createrId) { this.createrId = createrId;}

        public int getStatus() { return status;}

        public void setStatus(int status) { this.status = status;}

        public String getUpdatedAt() { return updatedAt;}

        public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt;}

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public String getCreateAt() { return createAt;}

        public void setCreateAt(String createAt) { this.createAt = createAt;}

        public String getCutOffTime() { return cutOffTime;}

        public void setCutOffTime(String cutOffTime) { this.cutOffTime = cutOffTime;}

        public int getType() { return type;}

        public void setType(int type) { this.type = type;}

        public String getDutyName() { return dutyName;}

        public void setDutyName(String dutyName) { this.dutyName = dutyName;}

        public boolean isAddOrSubtract() {
            return addOrSubtract;
        }

        public void setAddOrSubtract(boolean addOrSubtract) {
            this.addOrSubtract = addOrSubtract;
        }
    }

}
