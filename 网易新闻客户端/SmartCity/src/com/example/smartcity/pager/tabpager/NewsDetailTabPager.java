package com.example.smartcity.pager.tabpager;

import java.util.ArrayList;

import com.example.smartcity.R;
import com.example.smartcity.activity.SmartActivity;
import com.example.smartcity.bean.TabBean.LeftTabBean.SlideTabBean;
import com.example.smartcity.pager.tabpager.slidetabpager.BaseSlideTabPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsDetailTabPager extends BaseTabpager {

	private ViewPager viewpager;
	
	private ArrayList<BaseSlideTabPager> mList;

	private ArrayList<SlideTabBean> list;

	private TabPageIndicator indicator;
	public NewsDetailTabPager(Context context, ArrayList<SlideTabBean> list) {
		
		super(context);
		this.list = list;
	}
	@Override
	public void initData() {
		super.initData();
		mList = new ArrayList<BaseSlideTabPager>();
		for (int i = 0; i < list.size(); i++) {
			BaseSlideTabPager pager = new BaseSlideTabPager(mContext,list.get(i));
			mList.add(pager);
		}
		viewpager.setAdapter(new NewsDetailTabAdapter());
		indicator.setViewPager(viewpager);
	}

	@Override
	public View initView() {
		View view = View.inflate(mContext, R.layout.news_detail_tab, null);
		viewpager = (ViewPager) view.findViewById(R.id.news_tab_viewpager);
		indicator =(TabPageIndicator)view.findViewById(R.id.indicator);
	    indicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if (arg0 > 1) {
					//禁用侧边栏
					setSlidingMenuEnable(false);
				}else{
					//启用侧边栏
					setSlidingMenuEnable(true);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		return view;
	}
	
	//启动或者禁用侧边栏
	public void setSlidingMenuEnable(boolean enable){
		SmartActivity activity = (SmartActivity)mContext;
		SlidingMenu menu = activity.getSlidingMenu();
		if (enable) {
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
	
	class NewsDetailTabAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return list.size();
		}
		@Override
		public CharSequence getPageTitle(int position) {
			return list.get(position).getTitle();
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BaseSlideTabPager tabPager = mList.get(position);
			container.addView(tabPager.view);
			tabPager.initData();
			return tabPager.view;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
	}

}
