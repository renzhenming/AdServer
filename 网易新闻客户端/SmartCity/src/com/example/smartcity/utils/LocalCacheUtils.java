package com.example.smartcity.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

public class LocalCacheUtils {
	
	private final String LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/smartcity/bitmapcache";
	private LruCacheUtils lruUtils;

	public LocalCacheUtils(LruCacheUtils lruUtils) {
		this.lruUtils = lruUtils;
	}
	/**
	 * 从本地获取图片
	 * @param imageview
	 * @param imageUrl
	 */
	public Bitmap getBitmapFromLocal(String imageUrl) {
		try {
			File file = new File(LOCAL_PATH, MD5Encoder.encode(imageUrl));
			if (file.exists()) {
				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
				lruUtils.setBitmapToMemo(bitmap, imageUrl);
				return bitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存图片缓存到本地
	 * @param imageview
	 * @param imageUrl
	 */
	public boolean setBitmapToLocal(Bitmap bitmap, String imageUrl){
		File cacheDir = new File(LOCAL_PATH);
		if (!cacheDir.exists() || !cacheDir.isDirectory()) {
			cacheDir.mkdirs();
		}
		try {
			File cacheFile = new File(cacheDir, MD5Encoder.encode(imageUrl));
			OutputStream stream = new FileOutputStream(cacheFile);
			boolean compress = bitmap.compress(CompressFormat.PNG, 100, stream );
			System.out.println("connnnn:"+compress);
			return compress;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
