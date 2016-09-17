package com.example.smartcity.activity;

import java.util.ArrayList;

import com.example.smartcity.R;
import com.example.smartcity.R.id;
import com.example.smartcity.R.layout;
import com.example.smartcity.R.menu;
import com.example.smartcity.adapter.GuideAdapter;
import com.example.smartcity.global.Constants;
import com.example.smartcity.utils.SharePreferenceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class GuideActivity extends Activity implements OnPageChangeListener,
		OnClickListener {

	// 维护向导页的几张图片de id
	private int[] GUIDE_IMAGES = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };
	// 维护向导页图片对象
	private ArrayList<ImageView> image_list = new ArrayList<ImageView>();

	private ViewPager pager;
	private Button guide_confirm;
	private LinearLayout guide_container;
	private ImageView guide_red_dot;
	
	//圆点间的距离
	private int guide_dot_distance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		initView();
		initData();
		initListener();
	}

	private void initListener() {
		// viewpager的滑动事件
		pager.setOnPageChangeListener(this);
		// 按钮点击事件
		guide_confirm.setOnClickListener(this);
	}

	private void initData() {
		for (int i = 0; i < GUIDE_IMAGES.length; i++) {
			// 添加viewpager图片
			ImageView view = new ImageView(getApplicationContext());
			view.setBackgroundResource(GUIDE_IMAGES[i]);
			image_list.add(view);
			// 添加圆点指示器

			ImageView pointView = new ImageView(getApplicationContext());
			pointView.setImageResource(R.drawable.dot_normal);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					15,
					15);
			if (i != 0) {
				params.leftMargin = 15;
			}
			
			guide_container.addView(pointView,params);
		}
		pager.setAdapter(new GuideAdapter(image_list));
	}

	private void initView() {
		pager = (ViewPager) findViewById(R.id.guilde_viewpager);
		guide_confirm = (Button) findViewById(R.id.guide_confirm);
		guide_container = (LinearLayout) findViewById(R.id.guide_container);
		guide_red_dot = (ImageView) findViewById(R.id.guide_dotred);
		
		//在界面绘制完成时获取距离
		guide_red_dot.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {


			@Override
			public void onGlobalLayout() {
				guide_dot_distance = guide_container.getChildAt(1).getLeft() - guide_container.getChildAt(0).getLeft();
			}
		});
	}

	/**
	 * viewpager滑动监听
	 * 
	 * @param arg0
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int position, float percent, int value) {
		RelativeLayout.LayoutParams redParams = (RelativeLayout.LayoutParams) guide_red_dot.getLayoutParams();
		redParams.leftMargin = (int) (guide_dot_distance*percent+position*guide_dot_distance);
		
		guide_red_dot.setLayoutParams(redParams);
	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 == image_list.size() - 1) {
			guide_confirm.setVisibility(View.VISIBLE);

		}else{
			guide_confirm.setVisibility(View.GONE);
		}
	}

	/**
	 * 点击事件进入主页
	 */
	@Override
	public void onClick(View v) {
		// 保存向导状态
		SharePreferenceUtils.setBooleanValue(getApplicationContext(),
				Constants.HAS_GUIDE, true);
		startActivity(new Intent(getApplicationContext(), SmartActivity.class));
		finish();
	}

}
