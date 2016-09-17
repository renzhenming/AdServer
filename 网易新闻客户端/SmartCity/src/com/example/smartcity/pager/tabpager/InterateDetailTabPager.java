package com.example.smartcity.pager.tabpager;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class InterateDetailTabPager extends BaseTabpager {

	public InterateDetailTabPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		TextView view = new TextView(mContext);
		view.setText(getClass().getSimpleName());
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		view.setTextColor(Color.WHITE);
		return view;
	}

}
