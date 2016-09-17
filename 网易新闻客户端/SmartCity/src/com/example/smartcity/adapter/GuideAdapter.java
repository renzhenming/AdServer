package com.example.smartcity.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuideAdapter extends PagerAdapter {

	private ArrayList<ImageView> image_list;

	public GuideAdapter(ArrayList<ImageView> image_list) {
		this.image_list = image_list;
	}

	@Override
	public int getCount() {
		
		return image_list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = image_list.get(position);
		container.addView(imageView);
		return imageView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		
		container.removeView((View) object);
	}

}
