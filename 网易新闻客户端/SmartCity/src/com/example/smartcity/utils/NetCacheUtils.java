package com.example.smartcity.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class NetCacheUtils {
	
	private LocalCacheUtils localUtils;
	private LruCacheUtils lruUtils;

	public NetCacheUtils(LocalCacheUtils localUtils, LruCacheUtils lruUtils) {
		this.localUtils = localUtils;
		this.lruUtils = lruUtils;
	}

	public void getBitmapFromNet(ImageView imageview, String imageUrl) {
		new NetBitmapTask().execute(imageview,imageUrl);
	}
	
	class NetBitmapTask extends AsyncTask<Object, Void, Bitmap> {

		private ImageView imageview;
		private String url;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected Bitmap doInBackground(Object... params) {
			imageview = (ImageView) params[0];
			url = (String) params[1];
			Bitmap bitmap = downloadBitmap(url);
			//保存至本地
			localUtils.setBitmapToLocal(bitmap, url);
			//保存至缓存
			lruUtils.setBitmapToMemo(bitmap, url);
			imageview.setTag(url);
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			String bindUrl = (String) imageview.getTag();
			if (bindUrl.equals(url)) {
				imageview.setImageBitmap(bitmap);
			}
			
		}
		
		
	}

	public Bitmap downloadBitmap(String url) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream stream = conn.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(stream);
				return bitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				
				conn.disconnect();
			}
		}
		
		return null;
	}
	

}
