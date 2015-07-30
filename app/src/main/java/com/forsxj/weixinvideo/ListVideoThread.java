package com.forsxj.weixinvideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileFilter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ListVideoThread extends Thread
{
	private ArrayList<File> mVideoFolders;
	private Context mContext;
	private Handler mHandler;
	public static final int MSG_ACTION_UPDATE_SUCCESS = 0;
	public static final int MSG_ACTION_UPDATE_FAILE = 1;
	public static final int MSG_ACTION_UPDATE_NO_NEW_DATA = 2;
	public static final String MSG_CONTENT_VIDEOINFO_LIST = "MSG_CONTENT_VIDEOINFO_LIST";

	public ListVideoThread(Context context, Handler handler, ArrayList<File> videoFolders)
	{
		this.mVideoFolders = videoFolders;
		this.mContext = context;
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
					String videoDetailInfo = "";
					String videoName = videoFiles[j].getName();
					String videoAbsoulteName = videoFiles[j].getAbsolutePath();
					Bitmap videoCover = null;
					File file_VideoCover = new File(videoFiles[j].getAbsolutePath().substring(0,videoFiles[j].getAbsolutePath().lastIndexOf(".")) + ".jpg");
					if (file_VideoCover.exists() && file_VideoCover.isFile() && file_VideoCover.canRead())
					{
						videoCover = BitmapFactory.decodeFile(file_VideoCover.getAbsolutePath());
					}
					float videoLength;
					try
					{
						RandomAccessFile file = new RandomAccessFile(videoAbsoulteName,"rw");
						videoLength = file.length() / 1024;//KB
						videoDetailInfo = "文件大小:" + Float.toString(videoLength) + "KB";
						if (videoLength > 1024)
						{
							videoDetailInfo = videoLength / 1024 + "MB";
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					VideoInfo videoInfo = new VideoInfo(mContext,videoAbsoulteName,videoCover,videoName,videoDetailInfo,false);
					videoInfos.add(videoInfo);
				}
				Message message = mHandler.obtainMessage();
				message.what = MSG_ACTION_UPDATE_SUCCESS;
				Bundle bundle = new Bundle();
				bundle.putSerializable(MSG_CONTENT_VIDEOINFO_LIST,videoInfos);
				message.setData(bundle);
				message.sendToTarget();
			}
		}
	}
}
