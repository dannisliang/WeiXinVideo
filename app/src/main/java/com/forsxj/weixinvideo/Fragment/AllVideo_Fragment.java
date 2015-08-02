package com.forsxj.weixinvideo.Fragment;


import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.forsxj.weixinvideo.Adapter.AllVideoAdapter;
import com.forsxj.weixinvideo.Bean.VideoInfo;
import com.forsxj.weixinvideo.Custom.CApplication;
import com.forsxj.weixinvideo.Custom.GetVideoInfoListFromMsg;
import com.forsxj.weixinvideo.Custom.NoLeakHandler;
import com.forsxj.weixinvideo.Custom.SnackBarToast;
import com.forsxj.weixinvideo.Custom.Utils;
import com.forsxj.weixinvideo.Custom.VideoListFragment;
import com.forsxj.weixinvideo.Interface.AllVideoListView_Method;
import com.forsxj.weixinvideo.R;
import com.forsxj.weixinvideo.WorkThread.ListVideoThread;
import com.forsxj.weixinvideo.WorkThread.SaveVideoThread;

import java.io.File;
import java.util.ArrayList;

public class AllVideo_Fragment extends VideoListFragment implements AllVideoListView_Method
{
	private ArrayList<VideoInfo> mVideoInfoList = new ArrayList<>();
	private ListView mListView;
	private VideoListHandler mVideoListHandler;

	public AllVideo_Fragment()
	{
		mVideoListHandler = new VideoListHandler(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public ListView getListView()
	{
		return mListView;
	}

	@Override
	public void saveSelected()
	{
		new SaveVideoThread(getSelectedVideo(), CApplication.getMainHandler()).start();
	}

	@Override
	public void sync()
	{

	}

	private static class VideoListHandler extends NoLeakHandler<AllVideo_Fragment>
	{
		public VideoListHandler(AllVideo_Fragment outClass)
		{
			super(outClass);
		}

		@Override
		public void handleMessage(Message msg, AllVideo_Fragment allVideo_fragment)
		{
			if (msg.arg1 == ListVideoThread.MSG_ARG_ALL_VIDEO)
			{
				allVideo_fragment.mVideoInfoList = GetVideoInfoListFromMsg.getVideoInfoListFromMsg(msg, ListVideoThread.MSG_CONTENT_VIDEO_INFO_LIST);
				allVideo_fragment.mListView.setAdapter(new AllVideoAdapter(allVideo_fragment.getActivity(), allVideo_fragment.mVideoInfoList));
			}
		}
	}

	private void initVideoList()
	{
		String videoFolderPath = Utils.getWeiXinVideoPath();
		File file_VideoFolderPath = new File(videoFolderPath);
		ArrayList<File> videoPath_Found = new ArrayList<>();
		if (file_VideoFolderPath.exists())
		{
			File[] files = file_VideoFolderPath.listFiles();
			if (files.length > 0)
			{
				for (File file1 : files)
				{
					if (file1.isDirectory()
							&& file1.getName().length() == 32
							&& file1.canRead())
					{
						File file = new File(file1.getAbsolutePath() + "/video");
						if (file.exists() && file.isDirectory() && file.canRead())
						{
							videoPath_Found.add(file);
						}
					}
				}
			}
		}
		if (videoPath_Found.size() == 0)
		{
			SnackBarToast.showDefaultSnackBarToast_Short(mListView, "没有找到微信视频目录！");
			return;
		}
		new ListVideoThread(mVideoListHandler, videoPath_Found, ListVideoThread.MSG_ARG_ALL_VIDEO).start();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_all_video, container, false);
		mListView = (ListView) view.findViewById(R.id.listView_AllVideo);
		initVideoList();
		return view;
	}

	@Override
	public void reLoadVideoList()
	{
		initVideoList();
	}
}
