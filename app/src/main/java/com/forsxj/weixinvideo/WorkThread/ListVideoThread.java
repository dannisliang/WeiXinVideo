package com.forsxj.weixinvideo.WorkThread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.forsxj.weixinvideo.Bean.VideoInfo;
import com.forsxj.weixinvideo.Custom.Utils;

import java.io.File;
import java.io.FileFilter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListVideoThread extends Thread
{
	private ArrayList<File> mVideoFolders;
	private Handler mHandler;
	public static final int MSG_ACTION_UPDATE_SUCCESS = 0;
	public static final int MSG_ACTION_UPDATE_FAILE = 1;
	public static final int MSG_ACTION_UPDATE_NO_NEW_DATA = 2;
	public static final String MSG_CONTENT_VIDEOINFO_LIST = "MSG_CONTENT_VIDEOINFO_LIST";

	public ListVideoThread(Handler handler, ArrayList<File> videoFolders)
	{
		this.mVideoFolders = videoFolders;
		this.mHandler = handler;
	}

	@Override
	public void run()
	{
		ArrayList<VideoInfo> videoInfos = new ArrayList<>();
		for (int i = 0; i < mVideoFolders.size(); i++)
		{
			File[] videoFiles = mVideoFolders.get(i).listFiles(new FileFilter()
			{
				@Override
				public boolean accept(File pathname)
				{
					if (pathname.isDirectory() || !pathname.canRead())
					{
						return false;
					}
					else
					{
						return pathname.getName().endsWith(".mp4");
					}
				}
			});
			if (videoFiles.length > 0)
			{
				for (int j = 0; j < videoFiles.length; j++)
				{
					String videoSize = "";
					String videoName = videoFiles[j].getName();
					String videoAbsoulteName = videoFiles[j].getAbsolutePath();
					Calendar videoTime = Calendar.getInstance();
					videoTime.setTime(new Date(videoFiles[j].lastModified()));
					float videoLength;
					try
					{
						RandomAccessFile file = new RandomAccessFile(videoAbsoulteName, "rw");
						videoLength = file.length() / 1024;//KB
						boolean isMB = videoLength > 1024;
						videoSize = "文件大小:"
								+ Utils.getReservedDecimal(isMB ? videoLength / 1024 : videoLength, isMB ? 1 : 0)
								+ (isMB ? "MB" : "KB");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					VideoInfo videoInfo = new VideoInfo(videoAbsoulteName, videoName, videoSize, videoTime, false);
					videoInfos.add(videoInfo);
				}
				Message message = mHandler.obtainMessage();
				message.what = MSG_ACTION_UPDATE_SUCCESS;
				Bundle bundle = new Bundle();
				bundle.putSerializable(MSG_CONTENT_VIDEOINFO_LIST, videoInfos);
				message.setData(bundle);
				message.sendToTarget();
			}
		}
	}
}
