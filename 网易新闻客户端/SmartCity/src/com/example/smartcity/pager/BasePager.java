package com.example.smartcity.pager;

import com.example.smartcity.R;
import com.example.smartcity.activity.SmartActivity;
import com.example.smartcity.fragment.LeftFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class BasePager {

	public Context context;
	//页面布局，可以返回
	public View rootView;
	public TextView title;
	public FrameLayout container;
	public ImageButton leftButton;

	public BasePager(Context context){
		this.context = context;
		rootView = initView();
	}
	
	public View initView(){
		View view = View.inflate(context, R.layout.content_basepager, null);
		title = (TextView) view.findViewById(R.id.basepager_center_title);
		container = (FrameLayout) view.findViewById(R.id.basepager_container);
		leftButton = (ImageButton) view.findViewById(R.id.basepager_left_button);
		leftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		return view;
	}
	
	//本方法通过调用设置数据，子类可以重写达到效果
	public void initData(){
		
	}
	//关闭侧边栏
	public void toggle() {
		SmartActivity activity = (SmartActivity)context;
		SlidingMenu menu = activity.getSlidingMenu();
		menu.toggle();
	}
	
}
