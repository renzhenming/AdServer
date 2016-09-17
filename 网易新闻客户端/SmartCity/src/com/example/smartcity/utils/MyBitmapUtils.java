package com.example.smartcity.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class MyBitmapUtils {
	/**网络缓存工具类*/
	private NetCacheUtils netUtils;
	/**本地缓存工具类*/
	private LocalCacheUtils localUtils;
	/**内存缓存工具类*/
	private LruCacheUtils lruUtils;

	public MyBitmapUtils(Context mContext) {
		lruUtils = new LruCacheUtils();
		localUtils = new LocalCacheUtils(lruUtils);
		netUtils = new NetCacheUtils(localUtils,lruUtils);
	}

	public void display(ImageView imageview, String imageUrl) {
		
		//加载内存图片
		Bitmap lruBitmap = lruUtils.getBitmapFromMemo(imageUrl);
		if (lruBitmap != null) {
			imageview.setImageBitmap(lruBitmap);
			System.out.println("加载内存缓存图片");
			return;
		}
		//本地获取图片
		Bitmap bitmap;
		try {
			bitmap = localUtils.getBitmapFromLocal(imageUrl);
			if (bitmap != null) {
				imageview.setImageBitmap(bitmap);
				System.out.println("加载本地图片");
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//请求服务器加载图片
		netUtils.getBitmapFromNet(imageview,imageUrl);
		System.out.println("加载网络图片");
	}

}
