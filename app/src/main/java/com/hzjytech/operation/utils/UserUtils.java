package com.hzjytech.operation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hzjytech.operation.entity.User;

public class UserUtils {
	private static final String USER_INFO = "USER_INFO";
	private static final String MOBILE = "MOBILE";
	private static final String PWD = "PWD";

	private static Context context = null;
	private static User userInfo = null;
	
	public static void init(Context context){
		UserUtils.context = context;
	}

	
	public static void saveLocalMobileAndPwd(String mobile,String pwd){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		try {
			mobile = DES3Util.encrypt(mobile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			pwd = DES3Util.encrypt(pwd);
		} catch (Exception e) {
			e.printStackTrace();
			pwd = "";
		}
		Editor edit = sp.edit();
		edit.putString(MOBILE, mobile);
		edit.putString(PWD, pwd);
		edit.commit();
	}

	public static String getMobile(){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		String mobile = sp.getString(MOBILE, null);
		try {
			mobile = DES3Util.decrypt(mobile);
		} catch (Exception e) {
			e.printStackTrace();
			mobile = "";
		}
		return mobile;
	}
	
	public static String getPwd(){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		String pwd = sp.getString(PWD, null);
		try {
			pwd = DES3Util.decrypt(pwd);
		} catch (Exception e) {
			e.printStackTrace();
			pwd = "";
		}
		return pwd;
	}
	
	public static void logout(){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString(PWD, "");
		edit.commit();
	}
	
	public static String getUserCacheFileName(){
		return "userInfo";
	}
	
	public static void saveUserInfo(User data) {
		userInfo = data;
		SharedPreferences sp = context.getSharedPreferences(
				getUserCacheFileName(), Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		if(null == data){
			edit.putString("userInfo", "");
		}else{
			edit.putString("userInfo", GsonUtils.objToJson(data));
		}
		edit.commit();
	}

	
	
	public static User getUserInfo() {
		if (null == userInfo) {
			String ss = null;
			SharedPreferences sp = context.getSharedPreferences(
					getUserCacheFileName(), Context.MODE_PRIVATE);
			ss = (String) sp.getString("userInfo", null);
			User ret = null;

			ret = GsonUtils.jsonToObj(ss, User.class);
			userInfo = ret;
		}
		return userInfo;
	}
}
