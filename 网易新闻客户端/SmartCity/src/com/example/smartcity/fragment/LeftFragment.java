package com.example.smartcity.fragment;

import java.util.ArrayList;

import com.example.smartcity.R;
import com.example.smartcity.activity.SmartActivity;
import com.example.smartcity.bean.TabBean;
import com.example.smartcity.bean.TabBean.LeftTabBean;
import com.example.smartcity.pager.BasePager;
import com.example.smartcity.pager.NewsCenterPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LeftFragment extends BaseFragment implements OnItemClickListener {

	private ArrayList<LeftTabBean> mList;
	private ListView listview;
	
	//记录当前点击item的位置
	private int mCurrentPosi;
	private LeftAdapter adapter;
	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left, null);
		listview = (ListView) view.findViewById(R.id.left_listview);
		listview.setOnItemClickListener(this);
		return view;
	}
	
	//接收数据
	public void setData(ArrayList<TabBean.LeftTabBean> list){
		mCurrentPosi = 0;
		mList = list;
		adapter = new LeftAdapter();
		listview.setAdapter(adapter);
	}
	
	class LeftAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public LeftTabBean getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = View.inflate(mActivity, R.layout.item_left, null);
			TextView text1 = (TextView) convertView.findViewById(R.id.text1);
			
			if (position == mCurrentPosi) {
				text1.setEnabled(true);
			}else{
				text1.setEnabled(false);
			}
			LeftTabBean item = getItem(position);
			text1.setText(item.getTitle());
			return convertView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mCurrentPosi = position;
		adapter.notifyDataSetChanged();
		//关闭侧边栏
		toggle();
		//切换页面显示
		setCurrentPager(position);
	}

	private void setCurrentPager(int position) {
		//获取到新闻中心对象
		SmartActivity acitivity = (SmartActivity)mActivity;
		ContentFragment contentFragment = acitivity.getContentFragment();
		NewsCenterPager centerPager = contentFragment.getNewsCenterPager();
		//切换
		centerPager.setCurrentTabPager(position);
	}

}
