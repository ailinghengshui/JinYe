package com.hzjytech.qiniutoken.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Hades on 2016/2/24.
 */
public final class Json {

    private Json(){

    }

    public static String encode(StringMap map){
        return new Gson().toJson(map.map());
    }

    public static <T> T decode(String json,Class<T> classOfT){
        return new Gson().fromJson(json, classOfT);
    }

    public static StringMap decode(String json){
        Type type = new TypeToken<Map<String,Object>>(){
        }.getType();
        Map<String,Object> x=new Gson().fromJson(json,type);
        return new StringMap(x);
    }
}
