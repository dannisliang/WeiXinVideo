package com.forsxj.weixinvideo.Bean;

import android.content.Context;

import com.forsxj.weixinvideo.R;

import java.io.Serializable;
import java.util.Calendar;

public class VideoInfo implements Serializable
{
	private String mFileName;//全路径名称
	private String mVideoName;//视频名称
	private String mVideoSize;//文件大小
	private Calendar mVideoTime;//文件创建时间，在复制文件后还原文件原始创建时间
	private boolean mSelected;
	private Context mContext;

	public VideoInfo(Context context, String fileName, String videoName, String videoSize, Calendar videoTime, boolean selected)
	{
		this.mFileName = fileName;
		this.mVideoName = videoName;
		this.mVideoSize = videoSize;
		this.mVideoTime = videoTime;
		this.mSelected = selected;
		this.mContext = context;
	}

	public String getFileName()
	{
		return mFileName;
	}

	public String getVideoName()
	{
		return mVideoName;
	}

	public String getVideoSize()
	{
		return mContext.getString(R.string.File_Size) + mVideoSize;
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
		return mContext.getString(R.string.File_Name) + getVideoName() + " " +
				mContext.getString(R.string.File_Size) + getVideoSize();
	}

	public String getVideoTime()
	{
		return mContext.getString(R.string.File_Time) + mVideoTime.get(Calendar.YEAR) + "/" +
				String.valueOf(mVideoTime.get(Calendar.MONTH) + 1) + "/" +
				mVideoTime.get(Calendar.DAY_OF_MONTH) + " " +
				mVideoTime.get(Calendar.HOUR_OF_DAY) + ":" +
				mVideoTime.get(Calendar.MINUTE) + ":" +
				mVideoTime.get(Calendar.SECOND);
	}
}
