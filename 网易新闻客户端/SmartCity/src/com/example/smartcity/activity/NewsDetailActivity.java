package com.example.smartcity.activity;

import com.example.smartcity.R;
import com.example.smartcity.R.id;
import com.example.smartcity.R.layout;
import com.example.smartcity.R.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewsDetailActivity extends Activity implements OnClickListener {

	private ImageButton leftButton;
	private ImageButton share;
	private ImageButton textSize;
	private WebView webView;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_news_detail);
		initView();
		initData();
	}

	private void initData() {
		String url = getIntent().getStringExtra("url");
		webView.loadUrl(url);
		WebSettings settings = webView.getSettings();
		settings.setBuiltInZoomControls(true);
		settings.setUseWideViewPort(true);
		settings.setJavaScriptEnabled(true);
		
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onReceivedTitle(WebView view, String title) {
				System.out.println("title:"+title);
				super.onReceivedTitle(view, title);
			}
			
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				System.out.println("progress:"+newProgress);
				super.onProgressChanged(view, newProgress);
			}
		});
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				progressBar.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				progressBar.setVisibility(View.GONE);
			}
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webView.loadUrl(url);
				return true;
			}
		});
	}
	@Override
	public void onBackPressed() {
		
		if (webView.canGoBack()) {
			webView.goBack();
		}else{
			super.onBackPressed();
		}
		
	}

	private void initView() {
		TextView title = (TextView) findViewById(R.id.basepager_center_title);
		title.setVisibility(View.GONE);
		webView = (WebView) findViewById(R.id.webview);
		
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		leftButton = (ImageButton) findViewById(R.id.basepager_left_button);
		share = (ImageButton) findViewById(R.id.icon_share);
		textSize = (ImageButton) findViewById(R.id.icon_textsize);
		share.setVisibility(View.VISIBLE);
		textSize.setVisibility(View.VISIBLE);
		leftButton.setOnClickListener(this);
		share.setOnClickListener(this);
		textSize.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.basepager_left_button:

			break;
		case R.id.icon_share:

			break;
		case R.id.icon_textsize:
			showChooseDialog();
			break;

		default:
			break;
		}
	}

	private void showChooseDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("选择字体");
		String [] items = new String[]{"超大字体","大字体","正常字体","小字体","超小字体"};
		builder.setSingleChoiceItems(items , 2, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("which:"+which);
			}

			
		});
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
			

		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.show();
	}
}
