package com.example.smartcity.activity;

import com.example.smartcity.R;
import com.example.smartcity.R.id;
import com.example.smartcity.R.layout;
import com.example.smartcity.global.Constants;
import com.example.smartcity.utils.SharePreferenceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

	public class MainActivity extends Activity {

	    private RelativeLayout splash_bg;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_main);
	        initView();
	        initAnimation();
	    }

	    private void initAnimation() {
	        RotateAnimation ra = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
	        ra.setDuration(1000);

	        AlphaAnimation aa = new AlphaAnimation(0,1);
	        aa.setDuration(2000);

	        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
	        sa.setDuration(1000);

	        //动画集合
	        AnimationSet as = new AnimationSet(true);
	        as.addAnimation(ra);
	        as.addAnimation(aa);
	        as.addAnimation(sa);
	        splash_bg.setAnimation(as);
	        as.setAnimationListener(new AnimationListener() {


				@Override
				public void onAnimationStart(Animation animation) {
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					//第一次启动，进入向导页，否则进入主页
					boolean b = SharePreferenceUtils.getBooleanValue(getApplicationContext(), Constants.HAS_GUIDE, false);
					
					Intent intent;
					if (!b) {
						intent = new Intent(getApplicationContext(), GuideActivity.class);
						
					}else{
						//不是第一次
						intent = new Intent(getApplicationContext(), SmartActivity.class);
					}
					startActivity(intent);
					finish();
				}
			});

	    }

	    private void initView() {
	        splash_bg = (RelativeLayout) findViewById(R.id.image_splash_bg);
	    }
	}


