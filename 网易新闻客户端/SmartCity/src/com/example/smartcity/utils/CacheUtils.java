package com.example.smartcity.utils;

import android.content.Context;

public class CacheUtils {
	
	/**
	 * 写缓存
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setCache(Context context,String key,String value){
		SharePreferenceUtils.setStringValue(context, key, value);
	}
	
	/**
	 * 读缓存
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getCache(Context context,String key,String defValue){
		return SharePreferenceUtils.getStringValue(context, key, defValue);
	}
}
