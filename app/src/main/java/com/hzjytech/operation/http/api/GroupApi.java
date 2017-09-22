package com.hzjytech.operation.http.api;

import com.hzjytech.operation.entity.AddGroupInfo;
import com.hzjytech.operation.entity.AddMachineInfo;
import com.hzjytech.operation.entity.GroupList;
import com.hzjytech.operation.http.HttpResultFunc;
import com.hzjytech.operation.http.RetrofitSingleton;


import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/5/11.
 * 咖啡机组相关api
 */
public class GroupApi {
    //获取单台管理列表
    public static Observable<List<GroupList>> getSingleGroupList(
            String token,
            String queryCondition) {
        return RetrofitSingleton.getApiService()
                .getSingleGroupList(token, queryCondition)
                .map(new HttpResultFunc<List<GroupList>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //修改促销信息
    public static Observable<Object> commitPromotionText(String token, int groupId, String text) {
        return RetrofitSingleton.getApiService()
                .commitPromotionText(token, groupId, text)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //修改打折和满减信息
    public static Observable<Object> commitGroupModify(
            String token,
            int groupId,
            String groupName,
            double discount,
            double discountM,
            double discountJ,
            String TimeSet) {
        return RetrofitSingleton.getApiService()
                .commitModifyGroup(token,
                        groupId,
                        groupName,
                        discount == 0 ? null : discount,
                        discountM == 0 ? null : discountM,
                        discountJ == 0 ? null : discountJ,
                        TimeSet)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //删除分组中包含的分组
    public static Observable<Object> detachGroup(String token, int groupId, String subGroupIds) {
        return RetrofitSingleton.getApiService()
                .detachGroup(token,subGroupIds,groupId)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //删除分组中包含的机器
    public static Observable<Object> detachMachine(String token, int groupId, String machineIds) {
        return RetrofitSingleton.getApiService()
                .detachMachine(token,machineIds,groupId)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取可添加的分组
    public static Observable<List<AddGroupInfo>>getAddGroups(String token, int groupId, int vmTypeId) {
        return RetrofitSingleton.getApiService()
                .getAddGroupList(token,groupId,vmTypeId)
                .map(new HttpResultFunc<List<AddGroupInfo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取可添加的机器
    public static Observable<List<AddMachineInfo>>getAddMachines(String token, int groupId, int vmTypeId) {
        return RetrofitSingleton.getApiService()
                .getAddMachineList(token,groupId,vmTypeId)
                .map(new HttpResultFunc<List<AddMachineInfo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //添加分组
    public static Observable<Object> addGroup(String token, int groupId, String subGroupIds) {
        return RetrofitSingleton.getApiService()
                .addGroup(token,subGroupIds,groupId)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //添加咖啡机
    public static Observable<Object> addMachine(String token, int groupId, String subMachineIds) {
        return RetrofitSingleton.getApiService()
                .addMachine(token,subMachineIds,groupId)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
