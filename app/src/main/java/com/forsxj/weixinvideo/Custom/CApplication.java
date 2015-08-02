package com.forsxj.weixinvideo.Custom;

import android.app.Application;
import android.os.Handler;

public class CApplication extends Application
{
	private static Handler mMainHandler;

	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	public static void setMainHandler(Handler handler)
	{
		mMainHandler = handler;
	}

	public static Handler getMainHandler()
	{
		return mMainHandler;
	}
}
