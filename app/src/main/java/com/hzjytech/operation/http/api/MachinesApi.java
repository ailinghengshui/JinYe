package com.hzjytech.operation.http.api;

import com.hzjytech.operation.entity.CheckContent;
import com.hzjytech.operation.entity.DensityInfo;
import com.hzjytech.operation.entity.ErrorHistory;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.Machies;
import com.hzjytech.operation.entity.MachineInfo;
import com.hzjytech.operation.entity.MachineList;
import com.hzjytech.operation.entity.MaterialInfo;
import com.hzjytech.operation.entity.MenuInfo;
import com.hzjytech.operation.entity.PollingInfo;
import com.hzjytech.operation.entity.ScanAuthInfo;
import com.hzjytech.operation.entity.ScanInfo;
import com.hzjytech.operation.entity.SignInfo;
import com.hzjytech.operation.http.HttpResultFunc;
import com.hzjytech.operation.http.RetrofitSingleton;
import com.hzjytech.operation.scan.decoding.Intents;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/4/27.
 */
public class MachinesApi {
    //获取咖啡机列表
    public static Observable<Machies> getMachines(
            String queryStatus, String queryCondition, int pageSize, int pageNumber, String token) {
        return RetrofitSingleton.getApiService()
                .getMachines(queryStatus, queryCondition, pageSize, pageNumber, token)
                .map(new HttpResultFunc<Machies>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取单台咖啡机的具体信息
    public static Observable<MachineInfo> getSingleMachieDetail(String token, int machineId) {
        return RetrofitSingleton.getApiService()
                .getSingleMachieDetail(token, machineId)
                .map(new HttpResultFunc<MachineInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取咖啡机错误历史记录
    public static Observable<ErrorHistory> getErrorHistory(
            long startTime,
            long endTime,
            ArrayList<Integer> machineIds,
            int nearly,
            String token,
            int pageNumber,
            int pageSize) {
        return RetrofitSingleton.getApiService()
                .getErrorHistory(startTime, endTime, machineIds, nearly, token, pageNumber, pageSize)
                .map(new HttpResultFunc<ErrorHistory>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取咖啡机组信息
    public static Observable<GroupInfo> getGroupInfo(String token, int groupId) {
        return RetrofitSingleton.getApiService()
                .getGroupInfo(token, groupId)
                .map(new HttpResultFunc<GroupInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取菜单信息
    public static Observable<MenuInfo> getMenuInfo(String token, int menuId) {
        return RetrofitSingleton.getApiService()
                .getMenuInfo(token, menuId)
                .map(new HttpResultFunc<MenuInfo>())
                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取咖啡机余料
    public static Observable<MaterialInfo> getInventoriest(String token, int machineId) {
        return RetrofitSingleton.getApiService()
                .getInventoriest(token, machineId)
                .map(new HttpResultFunc<MaterialInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取单台管理列表
    public static Observable<MachineList> getSingleMachineList(
            String token, String queryCondition, int pageNumber, int pageSize) {
        return RetrofitSingleton.getApiService()
                .getSingleMachineList(token, queryCondition, pageNumber, pageSize)
                .map(new HttpResultFunc<MachineList>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }   //登录咖啡机

    public static Observable<ScanInfo> loginMachine(
            String token, int machineId, boolean isLogin, String QCCode, Integer recordId) {
        return RetrofitSingleton.getApiService()
                .loginMachine(token, machineId, isLogin, QCCode, recordId)
                .map(new HttpResultFunc<ScanInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //扫描二维码获取machineId
    public static Observable<ScanAuthInfo> getMachineIdByQRCode(String token, String QCCode) {
        return RetrofitSingleton.getApiService()
                .getMachineIdByQRCode(token, QCCode)
                .map(new HttpResultFunc<ScanAuthInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取巡检内容
    public static Observable<List<PollingInfo>> getPollingContent(String token) {
        return RetrofitSingleton.getApiService()
                .getPollingContent(token)
                .map(new HttpResultFunc<List<PollingInfo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //提交巡检结果
    public static Observable<Boolean> upPollingAnser(
            String token, int machineId, String finishContent, String unfinishContent) {
        return RetrofitSingleton.getApiService()
                .addInfo(token, machineId, finishContent, unfinishContent)
                .map(new HttpResultFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //提交浓度记录
    public static Observable<Object> addDensityInfo(
            String token, int machineId, String densityInfos) {
        return RetrofitSingleton.getApiService()
                .addDensityInfo(token,machineId,densityInfos)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取签到内容
    public static Observable<CheckContent> getSignInfo(
            String token, String  machineType) {
        return RetrofitSingleton.getApiService()
                .getSignContent(token, machineType)
                .map(new HttpResultFunc<CheckContent>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //提交签到内容
    public static Observable<Object> addSignInfo(
            String token, int machineId,int login,String defaultContent,String checkContent) {
        return RetrofitSingleton.getApiService()
                .addSignInfo(token, machineId,login,defaultContent,checkContent)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //提交咖啡机基础信息
    public static Observable<Object> uploadMachineInfo(
            String token,String basicInfo,String staffInfo,String alert,String otherInfo,int machineId,String costInfo ) {
        return RetrofitSingleton.getApiService()
                .upLoadMachineInfo(basicInfo,staffInfo,alert,otherInfo,machineId,costInfo,token)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
