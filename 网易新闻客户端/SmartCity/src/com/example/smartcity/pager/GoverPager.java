package com.example.smartcity.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.TextView;

public class GoverPager extends BasePager {

	public GoverPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initData() {
		super.initData();
		TextView view = new TextView(context);
		view.setText(getClass().getSimpleName()+"初始化了");
		System.out.println(getClass().getSimpleName()+"初始化了");
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		view.setTextColor(Color.WHITE);
		
		//添加到容器中
		container.addView(view);
	}
}
