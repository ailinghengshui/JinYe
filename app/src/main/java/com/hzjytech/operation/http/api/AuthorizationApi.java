package com.hzjytech.operation.http.api;

import com.hzjytech.operation.entity.AdminInfo;
import com.hzjytech.operation.entity.LoginInfo;
import com.hzjytech.operation.entity.RoleInfo;
import com.hzjytech.operation.entity.UpdateInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.HttpResult;
import com.hzjytech.operation.http.HttpResultFunc;
import com.hzjytech.operation.http.RetrofitSingleton;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/4/27.
 */
public class AuthorizationApi {
    //用户登录
    public static Observable<LoginInfo> login(String login, String password, String line){
        return RetrofitSingleton.
                getApiService()
                .login(login, password,line)
                .map(new HttpResultFunc<LoginInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取用户信息
    public static Observable<User>getPersonalData(String token){
        return RetrofitSingleton.getApiService().getPersonalData(token)
                .map(new HttpResultFunc<User>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //修改电话号码
     public static Observable<HttpResult<Object>>changePhone(int adminId, String token, String phone){
         return RetrofitSingleton.getApiService().changePhone(adminId,token,phone)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread());
     }
    //修改密码
    public static Observable<HttpResult<Object>>changePassword(int adminId,String password,String newPassword,String token){
        return RetrofitSingleton.getApiService().changePassword(adminId,password,newPassword,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //app升级
    public static Observable<UpdateInfo>getAppInfo(String token){
        return RetrofitSingleton.getApiService().getAppInfo(token)
                .map(new HttpResultFunc<UpdateInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取角色列表
    public static Observable<List<RoleInfo>>getRoleList(String token){
        return RetrofitSingleton.getApiService().getRoleList(token)
                .map(new HttpResultFunc<List<RoleInfo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //获取责任人列表
    public static Observable<List<AdminInfo>>getAdminList(String token,int machineId,int roleId){
        return RetrofitSingleton.getApiService().getAdminList(token,machineId,roleId)
                .map(new HttpResultFunc<List<AdminInfo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
