package com.hzjytech.operation.http.api;

import com.hzjytech.operation.entity.DetailTaskInfo;
import com.hzjytech.operation.entity.PersonInfo;
import com.hzjytech.operation.entity.TaskList;
import com.hzjytech.operation.entity.TaskListInfo;
import com.hzjytech.operation.http.HttpResultFunc;
import com.hzjytech.operation.http.RetrofitSingleton;


import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/6/14.
 */

public class TaskApi {
    /**
     * 获取负责人列表
     * @param token
     * @param machineId 机器id
     * @return
     */
    public static Observable<List<PersonInfo>> getPersonList(String token, int machineId){
        return RetrofitSingleton.getApiService().getPersonList(token,machineId)
                .map(new HttpResultFunc<List<PersonInfo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取任务列表
      * @param token
     * @param isMine 是否是属于我的任务
     * @param status 任务状态
     * @param keyWord
     * @return
     */
    public static Observable<TaskList> getTaskList(String token, boolean isMine, int status, String keyWord, int page, int pageSize){
        return RetrofitSingleton.getApiService().getTaskList(token,isMine,status,keyWord,page,pageSize)
                .map(new HttpResultFunc<TaskList>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 创建新的任务
     * @param token
     * @param machineId 机器id
     * @param dutyIds 负责人id
     * @param comment 评论文字
     * @param urls 评论图片集合
     * @param type 任务类型
     * @param cutOffTime 截止时间
     * @return
     */
    public static Observable<Boolean> creatNewTask(String token, int machineId,ArrayList<Integer>dutyIds,String comment,ArrayList<String>urls,int type,String cutOffTime){
        return RetrofitSingleton.getApiService().createTask(token,machineId,dutyIds,comment,urls,type,cutOffTime)
                .map(new HttpResultFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取任务详情
     * @param token
     * @param taskId
     * @return
     */
    public static Observable<DetailTaskInfo> getDetailTaskInfo(String token, int taskId){
        return RetrofitSingleton.getApiService().getTaskInfo(token,taskId)
                .map(new HttpResultFunc<DetailTaskInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 添加评论
     * @param token
     * @param taskId
     * @return
     */
    public static Observable<Boolean> addComment(String token, String comment,ArrayList<String>url,int taskId){
        return RetrofitSingleton.getApiService().addComment(token,comment,url,taskId)
                .map(new HttpResultFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 修改任务状态
     * @param token
     * @param status
     * @param taskId
     * @return
     */
    public static Observable<Boolean> changeTaskStatus(String token, int status,int taskId){
        return RetrofitSingleton.getApiService().changeTaskStatus(token,status,taskId)
                .map(new HttpResultFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 修改截止时间
     * @param token
     * @param cutOffTime
     * @param taskId
     * @return
     */
    public static Observable<Boolean> changeCutOffTime(String token, int taskId,String cutOffTime){
        return RetrofitSingleton.getApiService().changeCutOffTime(token,taskId,cutOffTime)
                .map(new HttpResultFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 修改任务联系人
     * @param token
     * @param dutyIds 联系人 id
     * @param taskId 任务id
     * @param addOrSubtract 添加或删除联系人
     * @return
     */
    public static Observable<Boolean> changeTaskDutyNames(String token, int taskId,int dutyIds,boolean addOrSubtract){
        return RetrofitSingleton.getApiService().changeTaskDutyNames(token,taskId,dutyIds,addOrSubtract)
                .map(new HttpResultFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
