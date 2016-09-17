package com.example.smartcity.pager.tabpager;

import javax.crypto.Mac;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public abstract class BaseTabpager {

	public View view;

	public Context mContext;
	public BaseTabpager(Context context){
		mContext = context;
		view = initView();
		
	}
	
	public abstract View initView();
	
	public void initData(){
		
	}
}
