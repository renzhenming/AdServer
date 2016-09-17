package com.example.smartcity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtils {
	
	private static final String SP_NAME = "smart_city_sp";
	
	public static SharedPreferences getSharePreferences(Context context){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp;
	}
	
	/**
	 * 读取boolean型值
	 * @param key
	 * @param context
	 * @param defValue
	 * @return
	 */
	public static boolean getBooleanValue(Context context ,String key ,boolean defValue){
		
		return getSharePreferences(context).getBoolean(key, defValue);
	}
	/**
	 * 保存boolean型值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setBooleanValue(Context context,String key,boolean value){
		
		Editor edit = getSharePreferences(context).edit();
		edit.putBoolean(key, value);
		edit.commit();
		
	}
	/**
	 * 获取String
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getStringValue(Context context ,String key ,String defValue){
		return getSharePreferences(context).getString(key, defValue);
	}
	/**
	 * 保存String
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setStringValue(Context context,String key,String value){
		Editor edit = getSharePreferences(context).edit();
		edit.putString(key, value);
		edit.commit();
	}
}





















