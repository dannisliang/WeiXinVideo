package com.forsxj.weixinvideo.WorkThread;

import android.content.Context;
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
	private Context mContext;
	private boolean mUpdate;

	//arg表示哪个ListView 0或1
	public ListVideoThread(Context context, Handler handler, ArrayList<File> videoFolders, int arg, boolean update)
	{
		this.mVideoFolders = videoFolders;
		this.mHandler = handler;
		this.mArg = arg;
		this.mContext = context;
		this.mUpdate = update;
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
					VideoInfo videoInfo = new VideoInfo(mContext, videoFilename, videoName, videoSize, videoTime, false);
					videoInfoList.add(videoInfo);
				}
				//通知fragment绑定数据
				Message message = mHandler.obtainMessage();
				message.what = Utils.MSG_ACTION_UPDATE_SUCCESS;
				message.arg1 = mArg;
				Bundle bundle = new Bundle();
				bundle.putBoolean(Utils.MSG_IS_UPDATE,mUpdate);//是否是手动刷新，手动刷新完后要显示提示
				bundle.putSerializable(Utils.MSG_CONTENT_VIDEO_INFO_LIST, videoInfoList);
				message.setData(bundle);
				message.sendToTarget();
			}
		}
	}
}
