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
	private int mArg;
	public static final int MSG_ACTION_UPDATE_SUCCESS = 0;
	public static final int MSG_ARG_ALL_VIDEO = 0;
	public static final int MSG_ARG_SAVED_VIDEO = 1;
//	public static final int MSG_ACTION_UPDATE_FAILE = 1;
//	public static final int MSG_ACTION_UPDATE_NO_NEW_DATA = 2;
	public static final String MSG_CONTENT_VIDEO_INFO_LIST = "MSG_CONTENT_VIDEO_INFO_LIST";

	public ListVideoThread(Handler handler, ArrayList<File> videoFolders ,int arg)
	{
		this.mVideoFolders = videoFolders;
		this.mHandler = handler;
		this.mArg = arg;
	}

	@Override
	public void run()
	{
		ArrayList<VideoInfo> videoInfoList = new ArrayList<>();
		for (int i = 0; i < mVideoFolders.size(); i++)
		{
			File[] videoFiles = mVideoFolders.get(i).listFiles(new FileFilter()
			{
				@Override
				public boolean accept(File pathname)
				{
					return !(pathname.isDirectory() || !pathname.canRead()) && pathname.getName().endsWith(".mp4");
				}
			});
			if (videoFiles.length > 0)
			{
				for (File videoFile : videoFiles)
				{
					String videoSize = "";
					String videoName = videoFile.getName();
					String videoFilename = videoFile.getAbsolutePath();
					Calendar videoTime = Calendar.getInstance();
					videoTime.setTime(new Date(videoFile.lastModified()));
					float videoLength;
					try
					{
						RandomAccessFile file = new RandomAccessFile(videoFilename, "rw");
						videoLength = file.length() / 1024;//KB
						boolean isMB = videoLength > 1024;
						videoSize = Utils.getReservedDecimal(isMB ? videoLength / 1024 : videoLength, isMB ? 1 : 0)
								+ (isMB ? "MB" : "KB");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					VideoInfo videoInfo = new VideoInfo(videoFilename, videoName, videoSize, videoTime, false);
					videoInfoList.add(videoInfo);
				}
				Message message = mHandler.obtainMessage();
				message.what = MSG_ACTION_UPDATE_SUCCESS;
				message.arg1 = mArg;
				Bundle bundle = new Bundle();
				bundle.putSerializable(MSG_CONTENT_VIDEO_INFO_LIST, videoInfoList);
				message.setData(bundle);
				message.sendToTarget();
			}
		}
	}
}
