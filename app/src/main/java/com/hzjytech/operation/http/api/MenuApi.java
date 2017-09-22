package com.hzjytech.operation.http.api;

import com.hzjytech.operation.entity.MenuList;
import com.hzjytech.operation.http.HttpResultFunc;
import com.hzjytech.operation.http.RetrofitSingleton;


import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/5/11.
 */
public class MenuApi {
    //获取菜单列表
    public static Observable<List<MenuList>> getSingleMenuList(String token, String queryCondition){
        return RetrofitSingleton.getApiService().getSingleMenuList(token,queryCondition)
                .map(new HttpResultFunc<List<MenuList>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
