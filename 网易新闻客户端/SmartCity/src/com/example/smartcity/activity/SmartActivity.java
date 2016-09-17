package com.example.smartcity.activity;

import com.example.smartcity.R;
import com.example.smartcity.R.id;
import com.example.smartcity.R.layout;
import com.example.smartcity.R.menu;
import com.example.smartcity.fragment.ContentFragment;
import com.example.smartcity.fragment.LeftFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class SmartActivity extends SlidingFragmentActivity {
	
	/**framgent的tag用于获取指定的fragment*/
	private static final String FRAGMENT_LEFT = "fragment_left";
	private static final String FRAGMENT_CONTENT = "fragment_content";
	private SlidingMenu slidingMenu;
	private FragmentManager manager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart);
		setBehindContentView(R.layout.activity_left);
		
		slidingMenu = getSlidingMenu();
		//设置全屏触摸
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//设置侧边栏覆盖之外的宽度
		slidingMenu.setBehindOffset(400);
		//初始化fragment
		initFragment();
	}

	private void initFragment() {
		manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.activity_left, new LeftFragment(), FRAGMENT_LEFT);
		transaction.add(R.id.activity_smart, new ContentFragment(), FRAGMENT_CONTENT);
		transaction.commit();
	}
	
	//获取侧边栏对象
	public LeftFragment getLeftFragment(){
		if (manager != null) {
			Fragment fragment = manager.findFragmentByTag(FRAGMENT_LEFT);
			return (LeftFragment) fragment;
		}
		return null;
	}
	//获取主栏对象
	public ContentFragment getContentFragment(){
		if (manager != null) {
			Fragment fragment = manager.findFragmentByTag(FRAGMENT_CONTENT);
			return (ContentFragment) fragment;
		}
		return null;
	}

}























