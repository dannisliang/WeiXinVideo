package com.forsxj.weixinvideo.Fragment;


import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.forsxj.weixinvideo.Adapter.AllVideoAdapter;
import com.forsxj.weixinvideo.Bean.VideoInfo;
import com.forsxj.weixinvideo.Custom.GetVideoInfoListFromMsg;
import com.forsxj.weixinvideo.Custom.NoLeakHandler;
import com.forsxj.weixinvideo.Custom.SnackBarToast;
import com.forsxj.weixinvideo.Custom.Utils;
import com.forsxj.weixinvideo.Custom.VideoListFragment;
import com.forsxj.weixinvideo.R;
import com.forsxj.weixinvideo.WorkThread.ListVideoThread;

import java.io.File;
import java.util.ArrayList;

public class SavedVideo_Fragment extends VideoListFragment
{
	private ArrayList<VideoInfo> mVideoInfoList = new ArrayList<>();
	private ListView mListView;
	private VideoListHandler mVideoListHandler;

	public SavedVideo_Fragment()
	{
		mVideoListHandler = new VideoListHandler(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_saved_video, container, false);
		mListView = (ListView) view.findViewById(R.id.listView_SavedVideo);
		initVideoList(false);
		return view;
	}

	private void initVideoList(boolean update)
	{
		File file = new File(Utils.getOutputPath());
		if (file.exists() && file.isDirectory() && file.canWrite())
		{
			ArrayList<File> videoPath_list = new ArrayList<>();
			videoPath_list.add(file);
			new ListVideoThread(getActivity(), mVideoListHandler, videoPath_list, Utils.MSG_ARG_SAVED_VIDEO, update).start();
		}
	}

	public ListView getListView()
	{
		return mListView;
	}

	private static class VideoListHandler extends NoLeakHandler<SavedVideo_Fragment>
	{
		public VideoListHandler(SavedVideo_Fragment outClass)
		{
			super(outClass);
		}

		@Override
		public void handleMessage(Message msg, SavedVideo_Fragment savedVideo_fragment)
		{
			if (msg.arg1 == Utils.MSG_ARG_SAVED_VIDEO)
			{
				savedVideo_fragment.mVideoInfoList = GetVideoInfoListFromMsg.getVideoInfoListFromMsg(msg, Utils.MSG_CONTENT_VIDEO_INFO_LIST);
				savedVideo_fragment.mListView.setAdapter(
						new AllVideoAdapter(savedVideo_fragment.getActivity(),
								savedVideo_fragment.mVideoInfoList,
								Utils.MSG_ARG_SAVED_VIDEO));
				savedVideo_fragment.updateTitle();
				if (msg.getData().getBoolean(Utils.MSG_IS_UPDATE))
				{
					SnackBarToast.showDefaultSnackBarToast_Short(savedVideo_fragment.mListView,savedVideo_fragment.getString(R.string.Update_VideoList_Successed));
				}
			}
		}
	}

	@Override
	public void reLoadVideoList(boolean update)
	{
		initVideoList(update);
	}

	@Override
	public int getArg()
	{
		return Utils.MSG_ARG_SAVED_VIDEO;
	}
}
