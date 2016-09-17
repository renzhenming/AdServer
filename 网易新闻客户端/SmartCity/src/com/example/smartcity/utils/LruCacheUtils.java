package com.example.smartcity.utils;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class LruCacheUtils {
	 
    //private HashMap<String, SoftReference<Bitmap>> map = new HashMap<String, SoftReference<Bitmap>>();
	private LruCache<String, Bitmap> map;
	public LruCacheUtils(){
		int cacheSize = (int) (Runtime.getRuntime().maxMemory()/8);
		System.out.println("maxSize:"+Runtime.getRuntime().maxMemory());
		map = new LruCache<String, Bitmap>(cacheSize ){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}
	/**
	 * 从内存中获取图片
	 * @param imageUrl
	 * @return
	 */
	public Bitmap getBitmapFromMemo(String imageUrl) {
		try {
			Bitmap soft = map.get(imageUrl);
			return soft;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存
	 * @param bitmap
	 * @param imageUrl
	 */
	public void setBitmapToMemo(Bitmap bitmap,String imageUrl){
		
			if (map.get(imageUrl)==null) {
				map.put(imageUrl, bitmap);
				System.out.println("成功写入缓存");
			
			}
			
		
		
	}

	
}
