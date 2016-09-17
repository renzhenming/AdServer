package com.example.smartcity.pager;

import java.util.ArrayList;

import com.example.smartcity.activity.SmartActivity;
import com.example.smartcity.bean.TabBean;
import com.example.smartcity.fragment.LeftFragment;
import com.example.smartcity.pager.tabpager.BaseTabpager;
import com.example.smartcity.pager.tabpager.InterateDetailTabPager;
import com.example.smartcity.pager.tabpager.NewsDetailTabPager;
import com.example.smartcity.pager.tabpager.PictureDetailTabPager;
import com.example.smartcity.pager.tabpager.TopicDetailTabPager;
import com.example.smartcity.utils.CacheUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.TextView;

public class NewsCenterPager extends BasePager {

	final String url = "http://10.0.3.2:8080/zhbj/categories.json";
	
	private ArrayList<BaseTabpager> mList;

	private TabBean tabBean;
	public NewsCenterPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initData() {
		super.initData();
//		System.out.println(getClass().getSimpleName()+"初始化了");
//		TextView view = new TextView(context);
//		view.setText(getClass().getSimpleName());
//		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//		view.setTextColor(Color.WHITE);
		
		//添加到容器中
//		container.addView(view);
		String cache = CacheUtils.getCache(context, url, null);
		if (cache != null) {
			System.out.println("读取到了缓存："+cache);
			parseJson(cache);
		}
		//请求服务器数据
		getDataFromServer();
		
	}
	private void getDataFromServer() {
		HttpUtils util = new HttpUtils();
		
		util.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				//写入缓存
				CacheUtils.setCache(context, url, result);
				System.out.println("数据："+result);
				parseJson(result);
				//初始化
				initTabPager();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				System.out.println("error:"+msg);
				error.printStackTrace();
			}
		});
	}
	protected void initTabPager() {
		mList = new ArrayList<BaseTabpager>();
		mList.add(new NewsDetailTabPager(context,tabBean.getData().get(0).getChildren()));
		mList.add(new TopicDetailTabPager(context));
		mList.add(new PictureDetailTabPager(context));
		mList.add(new InterateDetailTabPager(context));
		
		//默认显示第一个
		setCurrentTabPager(0);
	}
	//解析数据
	protected void parseJson(String result) {
		Gson gson = new Gson();
		tabBean = gson.fromJson(result, TabBean.class);
		//将数据传递到leftfragment
		//这里的上下文就是SmartActivity
		SmartActivity activity = (SmartActivity)context;
		LeftFragment leftFragment = activity.getLeftFragment();
		//需要leftfragment提供一个方法用于接收数据
		leftFragment.setData(tabBean.getData());
	}
	public void setCurrentTabPager(int position) {
		BaseTabpager tabpager = mList.get(position);
		container.removeAllViews();
		container.addView(tabpager.view);
		tabpager.initData();
		//设置标题
		title.setText(tabBean.getData().get(position).getTitle());
	}
}






















