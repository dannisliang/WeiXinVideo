package com.forsxj.weixinvideo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forsxj.weixinvideo.Custom.Utils;
import com.forsxj.weixinvideo.R;

public class SettingActivity extends AppCompatActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		String url = Utils.getInternalRootDirectoryPath() + "/WeiXin_Video_Output/" + "1118021606158f2706824000.mp4";
		Glide.with(this).load(url).asBitmap().into(imageView);
	}
	
}
