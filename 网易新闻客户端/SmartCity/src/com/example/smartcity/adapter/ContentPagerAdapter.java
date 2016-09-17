package com.example.smartcity.adapter;

import java.util.ArrayList;

import com.example.smartcity.pager.BasePager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
/**
 * 主页适配器
 * @author rzm
 * 2016/6/19
 *
 */
public class ContentPagerAdapter extends PagerAdapter {

	private ArrayList<BasePager> mList;
	public BasePager basePager;
	public ContentPagerAdapter(ArrayList<BasePager> mList) {
		this.mList = mList;
	}

	@Override
	public int getCount() {
		return mList.size();
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		basePager = mList.get(position);
		container.addView(basePager.rootView);
		/**为了防止viewpager自动初始化下一个页面，加载数据不再放在这里，而是监听viewpager的滑动，滑动到哪一个页面再初始化当前页面的数据*/
//		//当前页面数据
		//basePager.initData();
		return basePager.rootView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
