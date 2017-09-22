package com.hzjytech.operation.http.api;

import com.hzjytech.operation.entity.AddMaterialInfo;
import com.hzjytech.operation.entity.CurrentDataInfo;
import com.hzjytech.operation.entity.DailyOrderInfo;
import com.hzjytech.operation.entity.DetailSaleMachine;
import com.hzjytech.operation.entity.Groups;
import com.hzjytech.operation.entity.RepeatInfo;
import com.hzjytech.operation.entity.UserCommentInfo;
import com.hzjytech.operation.entity.WasteMaterialInfo;
import com.hzjytech.operation.entity.WeeklySaleInfo;
import com.hzjytech.operation.http.HttpResultFunc;
import com.hzjytech.operation.http.RetrofitSingleton;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/5/16.
 */
public class DataApi {
    //获取咖啡机列表
    public static Observable<CurrentDataInfo> getCurrentData(String token,String startTime,String endTime){
        return RetrofitSingleton.getApiService().getCurrentData(token,startTime,endTime)
                .map(new HttpResultFunc<CurrentDataInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取提供选择的咖啡机列表
   public static Observable<List<Groups>> getSelectMachies(String token){
        return RetrofitSingleton.getApiService().getSelectMachines(token)
                .map(new HttpResultFunc<List<Groups>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取销售统计表格
   public static Observable<DetailSaleMachine> getDetailSaleMachine(String token, long startTime, long endTime, int nearly, ArrayList<Integer> machineIds){
        return RetrofitSingleton.getApiService().getDetailSaleMachine(token,startTime,endTime,nearly,machineIds)
                .map(new HttpResultFunc<DetailSaleMachine>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取每日销售统计
   public static Observable<Object> getDailyData(String token, long startTime, long endTime, int nearly, ArrayList<Integer> machineIds){
        return RetrofitSingleton.getApiService().getDailyData(token,startTime,endTime,nearly,machineIds)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
   //加料统计
   public static Observable<AddMaterialInfo> getAddMaterial(String token, long startTime, long endTime, int nearly, ArrayList<Integer> machineIds,int pageNo,int pageSize){
        return RetrofitSingleton.getApiService().getAddMaterial(token,startTime,endTime,nearly,machineIds,pageNo,pageSize)
                .map(new HttpResultFunc<AddMaterialInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //消耗的物料
   public static Observable<WasteMaterialInfo> getWasteMaterial(String token, long startTime, long endTime, int nearly, ArrayList<Integer> machineIds){
        return RetrofitSingleton.getApiService().getWasteMaterial(token,startTime,endTime,nearly,machineIds)
                .map(new HttpResultFunc<WasteMaterialInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取每日销售统计
    public static Observable<List<DailyOrderInfo>> getNewDailyData(String token, long startTime, long endTime, int nearly, ArrayList<Integer> machineIds){
        return RetrofitSingleton.getApiService().getNewDailyData(token,startTime,endTime,nearly,machineIds)
                .map(new HttpResultFunc<List<DailyOrderInfo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取每日销售统计
    public static Observable<Object> getWeeklySaleData(String token, long startTime, long endTime, int nearly, ArrayList<Integer> machineIds){
        return RetrofitSingleton.getApiService().getWeeklyData(token,startTime,endTime,nearly,machineIds)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<UserCommentInfo> getUserEvaluation(
            String token, long startTime, long endTime, int nearly, ArrayList<Integer> machineIds) {
        return RetrofitSingleton.getApiService().getUserComment(token,startTime,endTime,nearly,machineIds)
                .map(new HttpResultFunc<UserCommentInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
