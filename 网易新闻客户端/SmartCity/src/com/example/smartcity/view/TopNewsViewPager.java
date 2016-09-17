package com.example.smartcity.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TopNewsViewPager extends ViewPager {
	int startX;
	private int startY;
	private int currentItem;
	public TopNewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TopNewsViewPager(Context context) {
		super(context);
	}

	/****
	 * 上下滑动，属于所在listview的滑动，需要由父控件拦截 向右滑动，并且是第一个需要拦截 想左滑动，并且是最后一个需要被拦截
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) ev.getX();
			startY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int endX = (int) ev.getX();
			int endY = (int) ev.getY();
			
			int dx = endX - startX;
			int dy = endY - startY;
			
			//判断是左滑还是右滑
			if (Math.abs(dx) > Math.abs(dy)) {
				currentItem  = getCurrentItem();
				if (dx < 0) {
					//左滑
					if (currentItem == getAdapter().getCount() - 1 ) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}else{
					//右滑
					if (currentItem == 0 ) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
				
			}else{
				//是上下滑动,请求拦截，也就是被listview拦截
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_UP:

			break;

		default:
			break;
		}

		return super.dispatchTouchEvent(ev);

	}

}
