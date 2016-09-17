package com.example.smartcity.fragment;

import java.util.ArrayList;

import com.example.smartcity.R;
import com.example.smartcity.activity.SmartActivity;
import com.example.smartcity.adapter.ContentPagerAdapter;
import com.example.smartcity.pager.BasePager;
import com.example.smartcity.pager.GoverPager;
import com.example.smartcity.pager.HomePager;
import com.example.smartcity.pager.NewsCenterPager;
import com.example.smartcity.pager.SettingPager;
import com.example.smartcity.pager.SmartServicePager;
import com.example.smartcity.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContentFragment extends BaseFragment implements
		OnCheckedChangeListener, OnPageChangeListener {

	private NoScrollViewPager mViewPager;
	private ArrayList<BasePager> mList;
	private RadioGroup mRadioGroup;
	private ContentPagerAdapter adapter;
	private HomePager hPager;
	private NewsCenterPager nPager;
	private SmartServicePager sPager;
	private GoverPager gPager;
	private SettingPager settingPager;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		mRadioGroup = (RadioGroup) view
				.findViewById(R.id.fragment_content_radiogroup);
		mRadioGroup.setOnCheckedChangeListener(this);
		mViewPager = (NoScrollViewPager) view
				.findViewById(R.id.fragment_content_viewpager);
		mViewPager.setOnPageChangeListener(this);
		
		// 初始化点击的按钮
		RadioButton homeButton = (RadioButton) mRadioGroup.getChildAt(0);
		homeButton.setChecked(true);
		return view;
	}

	// 页面初始化之后获取数据
	@Override
	public void initData() {
		super.initData();
		mList = new ArrayList<BasePager>();
		hPager = new HomePager(mActivity);
		mList.add(hPager);
		nPager = new NewsCenterPager(mActivity);
		mList.add(nPager);
		sPager = new SmartServicePager(mActivity);
		mList.add(sPager);
		gPager = new GoverPager(mActivity);
		mList.add(gPager);
		settingPager = new SettingPager(mActivity);
		mList.add(settingPager);
		adapter = new ContentPagerAdapter(mList);
		mViewPager.setAdapter(adapter);
		//初始化首页数据
		hPager.initData();
		//首页禁用侧边栏
		setSlidingMenuEnable(false);

	}

	/**
	 * 监听底栏点击事件切换页面
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.fragment_content_home:
			mViewPager.setCurrentItem(0, false);

			break;

		case R.id.fragment_content_newscenter:
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.fragment_content_smartservice:
			mViewPager.setCurrentItem(2, false);
			break;

		case R.id.fragment_content_gov:
			mViewPager.setCurrentItem(3, false);
			break;

		case R.id.fragment_content_setting:
			mViewPager.setCurrentItem(4, false);
			break;

		default:
			break;
		}

	}

	/**
	 * 监听viewpager页面的切换
	 * 
	 * @param arg0
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}
	//启动或者禁用侧边栏
	public void setSlidingMenuEnable(boolean enable){
		SmartActivity activity = (SmartActivity)mActivity;
		SlidingMenu menu = activity.getSlidingMenu();
		if (enable) {
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	/**
	 * 切换到哪个页面，那个页面加载数据，防止viewpager预加载
	 * @param arg0
	 */
	@Override
	public void onPageSelected(int arg0) {
		// 当前页面数据
		switch (arg0) {
		case 0:
			setSlidingMenuEnable(false);
			hPager.initData();
			break;
		case 1:
			setSlidingMenuEnable(true);

			nPager.initData();
			break;
		case 2:
			setSlidingMenuEnable(true);

			sPager.initData();
			break;
		case 3:
			setSlidingMenuEnable(true);
			gPager.initData();
			break;
		case 4:
			setSlidingMenuEnable(false);
			settingPager.initData();
			break;

		default:
			break;
		}

	}
	
	//获取新闻中心对象
	public NewsCenterPager getNewsCenterPager(){
		return (NewsCenterPager)mList.get(1);
	}

}
