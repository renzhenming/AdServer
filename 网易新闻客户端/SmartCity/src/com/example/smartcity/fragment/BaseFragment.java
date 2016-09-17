package com.example.smartcity.fragment;

import com.example.smartcity.activity.SmartActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * framgment基类
 * @author Administrator
 *
 */
public abstract class BaseFragment extends Fragment {

	public FragmentActivity mActivity;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = initView();
		
		return view;
	}
	

	public abstract View initView();
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}
	//供子类重写用于获取数据
	public void initData() {
		
	}
	
	//关闭侧边栏
	public void toggle() {
		SmartActivity activity = (SmartActivity)mActivity;
		SlidingMenu menu = activity.getSlidingMenu();
		menu.toggle();
	}

	
}
