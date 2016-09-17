package com.example.smartcity.pager.tabpager.slidetabpager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.sax.StartElementListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.example.smartcity.R;
import com.example.smartcity.activity.NewsDetailActivity;
import com.example.smartcity.bean.SlideDetailBean;
import com.example.smartcity.bean.SlideDetailBean.SlideListBean;
import com.example.smartcity.bean.SlideDetailBean.SlideTopBean;
import com.example.smartcity.bean.TabBean.LeftTabBean.SlideTabBean;
import com.example.smartcity.pager.tabpager.BaseTabpager;
import com.example.smartcity.utils.GsonTools;
import com.example.smartcity.utils.MyBitmapUtils;
import com.example.smartcity.utils.SharePreferenceUtils;
import com.example.smartcity.view.RefreshListView;
import com.example.smartcity.view.RefreshListView.OnRefreshListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.viewpagerindicator.CirclePageIndicator;

public class BaseSlideTabPager extends BaseTabpager implements
		OnPageChangeListener {

	private ViewPager viewpager;
	private SlideTabBean bean;

	private MyBitmapUtils bitmapUtil;
	// 请求接口
	private String tab_url;
	private ArrayList<SlideTopBean> topImgList;
	private ArrayList<SlideListBean> newsList;
	private TextView viewpagerTitle;
	private CirclePageIndicator circleIndicator;
	private RefreshListView listview;
	private String nextUrl;
	private String moreUrl;// 下一页数据的完整链接
	private NewsListAdapter adapter;
	private Handler handler;

	public BaseSlideTabPager(Context context, SlideTabBean slideTabBean) {
		super(context);
		bean = slideTabBean;
		tab_url = "http://10.0.3.2:8080/zhbj" + bean.getUrl();
	}

	@Override
	public void initData() {

		super.initData();
		getDataFromServer();
	}

	private void getDataFromServer() {

		HttpUtils util = new HttpUtils();
		util.send(HttpMethod.GET, tab_url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				System.out.println("resutl:d:" + result);

				parseJson(result, false);
				listview.setOnRefreshComplete();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub

			}
		});

	}

	protected void parseJson(String result, boolean isLoadMore) {

		SlideDetailBean slideDetailBean = GsonTools.JsonToBean(result,
				SlideDetailBean.class);
		// 下一页数据的地址
		nextUrl = slideDetailBean.data.more;
		if (!TextUtils.isEmpty(nextUrl)) {
			moreUrl = "http://10.0.3.2:8080/zhbj" + nextUrl;
		} else {
			moreUrl = null;
		}
		if (!isLoadMore) {
			// 头条新闻列表
			topImgList = slideDetailBean.data.topnews;
			newsList = slideDetailBean.data.news;

			viewpager.setAdapter(new TopNewsAdapter());
			viewpager.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						handler.removeCallbacksAndMessages(null);
						break;
					case MotionEvent.ACTION_UP:
						handler.sendEmptyMessageDelayed(0, 3000);
						break;
					case MotionEvent.ACTION_CANCEL:
						handler.sendEmptyMessageDelayed(0, 3000);
						break;

					default:
						break;
					}
					return false;
				}
			});
			circleIndicator.setViewPager(viewpager);
			circleIndicator.setSnap(true);
			adapter = new NewsListAdapter();
			listview.setAdapter(adapter);
			if (handler == null) {
				handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						int currentItem = viewpager.getCurrentItem();
						if (currentItem == topImgList.size() - 1) {
							currentItem = 0;
						} else {
							currentItem++;
						}
						viewpager.setCurrentItem(currentItem);
						handler.sendEmptyMessageDelayed(0, 3000);

					};

				};

				handler.sendEmptyMessageDelayed(0, 3000);
			}

			// 设置刷新的监听
			listview.setOnRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					getDataFromServer();// 刷新数据
				}

				@Override
				public void onLoadMore() {
					if (moreUrl != null) {
						// 存在下一页数据
						getMoreDataFromServer();
					} else {
						// 没有数据了
						Toast.makeText(mContext, "没有更多数据了", 0).show();
						listview.setOnRefreshComplete();
					}
				}
			});
			// 设置item点击事件
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					int index = position - listview.getHeaderViewsCount();
					SlideListBean listBean = newsList.get(index);
					String value = SharePreferenceUtils.getStringValue(
							mContext, "read_keys", "");
					if (!value.contains(listBean.id)) {
						value = value + "," + listBean.id;
						SharePreferenceUtils.setStringValue(mContext,
								"read_keys", value);
					}
					TextView title = (TextView) view
							.findViewById(R.id.news_title);
					title.setTextColor(Color.GRAY);

					Intent intent = new Intent(mContext,
							NewsDetailActivity.class);
					intent.putExtra("url", listBean.url);
					mContext.startActivity(intent);

				}
			});
			// 设置第一页标题
			viewpagerTitle.setText(topImgList.get(0).title);
		} else {
			ArrayList<SlideListBean> moreList = slideDetailBean.data.news;
			newsList.addAll(moreList);
			adapter.notifyDataSetChanged();
		}

	}

	protected void getMoreDataFromServer() {
		HttpUtils util = new HttpUtils();
		util.send(HttpMethod.GET, moreUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				parseJson(result, true);
				listview.setOnRefreshComplete();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				listview.setOnRefreshComplete();
			}
		});
	}

	@Override
	public View initView() {
		View headerView = View.inflate(mContext, R.layout.top_news_headerview,
				null);
		View view = View.inflate(mContext, R.layout.slide_tab, null);
		viewpager = (ViewPager) headerView.findViewById(R.id.tab_viewpager);
		viewpager.setOnPageChangeListener(this);
		viewpagerTitle = (TextView) headerView
				.findViewById(R.id.slide_viewpager_title);
		circleIndicator = (CirclePageIndicator) headerView
				.findViewById(R.id.indicator);
		listview = (RefreshListView) view.findViewById(R.id.slide_listvivew);

		listview.addHeaderView(headerView);
		return view;
	}

	class NewsListAdapter extends BaseAdapter {

		public NewsListAdapter() {
			if (bitmapUtil == null) {
				bitmapUtil = new MyBitmapUtils(mContext);
			}
//			bitmapUtil
//					.configDefaultLoadingImage(R.drawable.topnews_item_default);
		}

		@Override
		public int getCount() {
			return newsList.size();
		}

		@Override
		public SlideListBean getItem(int position) {
			return newsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(mContext, R.layout.item_news_list,
						null);
				holder.newsImg = (ImageView) convertView
						.findViewById(R.id.news_img);
				holder.newsTitle = (TextView) convertView
						.findViewById(R.id.news_title);
				holder.newsContent = (TextView) convertView
						.findViewById(R.id.news_content);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			SlideListBean item = getItem(position);
			String string = SharePreferenceUtils.getStringValue(mContext,
					"read_keys", "");
			if (string.contains(item.id)) {
				holder.newsTitle.setTextColor(Color.GRAY);
			}
			holder.newsTitle.setText(item.title);
			holder.newsContent.setText(item.pubdate);
			if (bitmapUtil != null) {
				bitmapUtil.display(holder.newsImg, item.listimage);
			}
			return convertView;
		}

	}

	static class ViewHolder {
		ImageView newsImg;
		TextView newsTitle, newsContent;
	}

	class TopNewsAdapter extends PagerAdapter {

		public TopNewsAdapter() {
			bitmapUtil = new MyBitmapUtils(mContext);
//			bitmapUtil
//					.configDefaultLoadingImage(R.drawable.topnews_item_default);
		}

		@Override
		public int getCount() {
			return topImgList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView view = new ImageView(mContext);
			view.setScaleType(ScaleType.CENTER_CROP);
			container.addView(view);
			bitmapUtil.display(view, topImgList.get(position).topimage);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		String title = topImgList.get(arg0).title;
		viewpagerTitle.setText(title);
	}

}
