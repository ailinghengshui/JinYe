package com.hzjytech.operation.http;

import com.hzjytech.operation.R;
import com.hzjytech.operation.module.common.MyApplication;

/**
 * 网络常量
 * Created by hehongcan on 2017/4/17.
 */
public class NetConstants {
    //public static  final  String API_URL =
    // "https://192.168.0.102:8080/CoffeeWebServer/CoffeeWebServer/api/v1/";
    //public static  final  String API_URL = "https://ywtest.hzjytech.com/CoffeeWebServer/api/v1/";
    //public static  final  String API_URL = "https://yunwei.hzjytech.com/CoffeeWebServer/api/v1/";
    public static final String API_URL = MyApplication.getInstance()
            .getString(R.string.IP);
    //public static  final  String IS_ONLINE="jijia_testline";
    // public static final  String IS_ONLINE="jijia_online";
    public static final String IS_ONLINE = MyApplication.getInstance()
            .getString(R.string.isOnline);
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String LINE = "line";
    public static final String RED = "reg_id";
    public static final String TIMESTAMP = "timestamp";
    public static final String DEVICE_ID = "device_id";
    public static final String TOKEN = "token";
    public static final String LOGIN = "login";
    public static final String ADMIN_ID = "adminId";
    public static final String NEWPASSWORD = "newPassword";
    public static final String STATUS = "queryStatus";
    public static final String PAGESIZE = "pageSize";
    public static final String PAGENUMBER = "pageNumber";
    public static final String MACHINEID = "machineId";
    public static final String STARTTIME = "startTime";
    public static final String ENDTIME = "endTime";
    public static final String GROUPID = "groupId";
    public static final String MENUID = "menuId";
    public static final String MACHINEIDS = "machineIds";
    public static final String NEARLY = "nearly";
    public static final String PAGENO = "pageNo";
    public static final String QUERY_CONDITION = "queryCondition";
    public static final String CONDITION = "condition";
    public static final String STATU = "status";
    public static final String ISMINE = "isMine";
    public static final String KEYWORD = "keyword";
    //qiniu保存空间名称
    public static final String QINIU_BUCKET = "coffees";
    public static final String DUTYIDS = "dutyIds";
    public static final String COMMENT = "comment";
    public static final String URL = "url";
    public static final String TYPE = "type";
    public static final String PAGE = "page";
    public static final String TASKID = "taskId";
    public static final String ISLOGIN = "isLogin";
    public static final String QCCODE = "QRCode";
    public static final String RECORDID = "recordId";
    public static final String FINISHCONTENT = "finishContent";
    public static final String UNFINISHCONTENT = "unfinishedContent";
    public static final String CUTOFFTIME = "cutOffTime";
    public static final String ADDORSUBTRACT = "addOrSubtract";
    public static final String CONCENTRATIONINFO = "concentrationInfo";
    public static final String MACHINETYPE = "machineType";
    public static final String VENDINGMACHINEID = "vendingMachineId";
    public static final String DEFAULTCONTENT = "defaultContent";
    public static final String CHECKCONTENT = "checkContent";
    public static final String BASICINFO = "basicInfo";
    public static final String STAFFINFO = "staffInfo";
    public static final String ALERT = "alert";
    public static final String OTHERINFO = "otherInfo";
    public static final String COSTINFO = "costInfo";
    public static final String ROLEID = "roleId";
    public static final String TEXT = "text";
    public static final String GROUPNAME = "groupName";
    public static final String DISCOUNT = "discount";
    public static final String DISCOUNTM = "discountM";
    public static final String DISCOUNTJ = "discountJ";
    public static final String TIMESET = "TimeSet";
    public static final String SUBGROUPIDS = "subGroupIds";
    public static final String VMTYPEID = "vmTypeId";
}
