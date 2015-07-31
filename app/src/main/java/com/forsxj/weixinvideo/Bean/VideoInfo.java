package com.forsxj.weixinvideo.Bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.forsxj.weixinvideo.R;

import java.io.Serializable;

public class VideoInfo implements Serializable
{
	private String mFileName;
	private Bitmap mVideoCover;
	private String mVideoName;
	private String mVideoDetailInfo;
	private boolean mSelected;

	public VideoInfo(Context context ,String fileName, Bitmap videoCover, String videoName, String videoInfo, boolean selected)
	{
		this.mFileName = fileName;
		this.mVideoCover = videoCover;
		if (videoCover == null)
		{
			this.mVideoCover = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
		}
		else
		{
			this.mVideoCover = videoCover;
		}
		this.mVideoName = videoName;
		this.mVideoDetailInfo = videoInfo;
		this.mSelected = selected;
	}

	public String getFileName()
	{
		return mFileName;
	}

	public Bitmap getVideoCover()
	{
		return mVideoCover;
	}

	public String getVideoName()
	{
		return mVideoName;
	}

	public String getVideoDetailInfo()
	{
		return mVideoDetailInfo;
	}

	public boolean getSelected()
	{
		return mSelected;
	}

	public void setSelected(boolean selected)
	{
		mSelected = selected;
	}

	@Override
	public String toString()
	{
		return "文件名："+getVideoName() + " 文件大小：" + getVideoDetailInfo();
	}
}
