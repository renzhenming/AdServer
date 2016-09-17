package com.example.smartcity.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context) {
		super(context);
	}
	
	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	//不拦截事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}
	/**
	 * 禁用滑动效果
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return true;
	}

}
