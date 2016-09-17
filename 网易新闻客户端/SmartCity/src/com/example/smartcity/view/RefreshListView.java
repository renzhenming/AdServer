package com.example.smartcity.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smartcity.R;

public class RefreshListView extends ListView implements OnScrollListener {

	private float startY = -1;
	private int measuredHeight;
	private View view;

	// 定义三种状态
	private static final int PUUL_TO_REFRESH = 1;
	private static final int RELEASE_TO_REFRESH = 2;
	private static final int REFRESHING = 3;

	// 默认状态
	private int mCurrentState = PUUL_TO_REFRESH;
	private ImageView arrow;
	private TextView title;
	private ProgressBar progress;
	private TextView time;
	private RotateAnimation upRotate;
	private RotateAnimation downRotate;
	private OnRefreshListener listener;
	private View footerView;
	private ProgressBar push_bar;
	private int footerHeight;
	//是否正在加载
	private boolean isLoadMore;

	public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initRefresh();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public RefreshListView(Context context) {
		this(context, null);
	}

	public void initRefresh() {
		//头布局
		view = View.inflate(getContext(), R.layout.pull_to_refreshview, null);
		arrow = (ImageView) view.findViewById(R.id.headview_arrow);
		title = (TextView) view.findViewById(R.id.head_refresh_title);
		progress = (ProgressBar) view.findViewById(R.id.headview_progress);
		time = (TextView) view.findViewById(R.id.head_refresh_time);
		addHeaderView(view);
		
		//脚布局
		footerView = View.inflate(getContext(), R.layout.push_to_refreshview, null);
		push_bar = (ProgressBar) footerView.findViewById(R.id.push_to_refresh_bar);
		addFooterView(footerView);

		// 测量view高度，默认隐藏头布局
		view.measure(0, 0);

		measuredHeight = view.getMeasuredHeight();
		view.setPadding(0, -measuredHeight, 0, 0);
		
		//测量并隐藏脚布局
		footerView.measure(0, 0);
		footerHeight = footerView.getMeasuredHeight();
		footerView.setPadding(0, -footerHeight, 0, 0);
		
		//设置listview的滑动监听
		setOnScrollListener(this);
		//初始化箭头动画
		initAnim();
	}

	private void initAnim() {
		upRotate = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		upRotate.setDuration(300);
		upRotate.start();
		upRotate.setFillAfter(true);
		
		downRotate = new RotateAnimation(180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		upRotate.setDuration(300);
		upRotate.start();
		upRotate.setFillAfter(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (mCurrentState == REFRESHING) {
				break;
			}
			if (startY == -1) {
				startY = ev.getY();
			}
			float endY = ev.getY();
			float dY = endY - startY;
			int firstVisiblePosition = getFirstVisiblePosition();
			if (dY > 0 && firstVisiblePosition == 0) {
				// 当前正在向下移动，并且是第一个条目，那么就更新刷新框
				int padding = (int) (-measuredHeight + dY);
				view.setPadding(0, padding, 0, 0);
	
				// 根据pading的值更新刷新布局的显示(mCurrentState != RELEASE_TO_REFRESH)正在刷新的时候禁止重复加载数据
				if (padding > 0 && mCurrentState != RELEASE_TO_REFRESH) {
					// 更新为释放刷新
					mCurrentState = RELEASE_TO_REFRESH;
					refreshView();
				} else if (padding < 0 && mCurrentState != PUUL_TO_REFRESH) {
					System.out.println("padding:"+padding);
					// 显示下拉刷新
					mCurrentState = PUUL_TO_REFRESH;
					refreshView();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			//抬起之后，将startY置为-1
			startY = -1;
			if (mCurrentState == PUUL_TO_REFRESH) {
				//隐藏
				view.setPadding(0, -measuredHeight, 0, 0);
			}else if (mCurrentState == RELEASE_TO_REFRESH) {
				//显示正在刷新
				mCurrentState = REFRESHING;
				refreshView();
			}
			break;

		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	private void refreshView() {
		switch (mCurrentState) {
		case PUUL_TO_REFRESH:
			title.setText("下拉刷新");
			progress.setVisibility(View.GONE);
			arrow.setVisibility(View.VISIBLE);
			
			arrow.startAnimation(downRotate);
			break;
		case RELEASE_TO_REFRESH:
			title.setText("释放刷新");
			progress.setVisibility(View.GONE);
			arrow.setVisibility(View.VISIBLE);
			
			arrow.startAnimation(upRotate);
			break;
		case REFRESHING:
			title.setText("正在刷新。。");
			progress.setVisibility(View.VISIBLE);
			view.setPadding(0, 0, 0, 0);
			//清楚了动画效果才能隐藏实现
			arrow.clearAnimation();
			arrow.setVisibility(View.GONE);
			if (listener != null) {
				listener.onRefresh();
			}
			break;

		default:
			break;
		}
		formatTime();
		
	}
	
	//监听接口
	public interface OnRefreshListener {
		void onRefresh();
		void onLoadMore();
	}
	
	public void setOnRefreshListener(OnRefreshListener listener) {
		this.listener = listener;
	}
	//设置刷新完成
	public void setOnRefreshComplete(){
		if (!isLoadMore) {
			//刷新完成后重新将当前刷新状态置为初始值：下拉刷新
			mCurrentState = PUUL_TO_REFRESH;
			view.setPadding(0, -measuredHeight, 0, 0);
			title.setText("下拉刷新");
			progress.setVisibility(View.GONE);
			arrow.setVisibility(View.VISIBLE);
		}else{
			footerView.setPadding(0, -footerHeight, 0, 0);
			//加载完成，更新isloadmore状态
			isLoadMore = false;
		}
		
	}
	
	//更新刷新布局的时间显示
	public void formatTime (){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time.setText(format.format(new Date())) ;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int lastVisiblePosition = getLastVisiblePosition();
		if (scrollState == SCROLL_STATE_IDLE && lastVisiblePosition == getCount() - 1 && isLoadMore != true) {
			isLoadMore = true;
			System.out.println("到底了");
			footerView.setPadding(0, 0, 0, 0);
			//设置刷新布局的位置
			setSelection(getCount());
			if (listener != null) {
				listener.onLoadMore();
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	
}
