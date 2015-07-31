package com.forsxj.weixinvideo.Custom;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public abstract class NoLeakHandler<T> extends Handler
{
	private WeakReference<T> mT;

	public NoLeakHandler(T outClass)
	{
		mT = new WeakReference<>(outClass);
	}

	@Override
	public void handleMessage(Message msg)
	{
		T t = mT.get();
		handleMessage(msg, t);
	}

	public abstract void handleMessage(Message msg, T t);
}
