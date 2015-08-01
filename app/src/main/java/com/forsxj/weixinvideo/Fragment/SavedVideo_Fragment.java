package com.forsxj.weixinvideo.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.forsxj.weixinvideo.Adapter.AllVideoAdapter;
import com.forsxj.weixinvideo.Bean.VideoInfo;
import com.forsxj.weixinvideo.Custom.GetVideoInfoListFromMsg;
import com.forsxj.weixinvideo.Custom.NoLeakHandler;
import com.forsxj.weixinvideo.Custom.Utils;
import com.forsxj.weixinvideo.R;
import com.forsxj.weixinvideo.WorkThread.ListVideoThread;

import java.io.File;
import java.util.ArrayList;

public class SavedVideo_Fragment extends Fragment
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
		View view =  inflater.inflate(R.layout.fragment_saved_video, container, false);
		mListView = (ListView) view.findViewById(R.id.listView_SavedVideo);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(((VideoInfo) mListView.getAdapter().getItem(position)).getFileName()), "video/mp4");
				startActivity(intent);
			}
		});
		initVideoList();
		return view;
	}

	private void initVideoList()
	{
		File file = new File(Utils.getOutputPath());
		if (file.exists() && file.isDirectory() && file.canWrite())
		{
			ArrayList<File> videoPath_list = new ArrayList<>();
			videoPath_list.add(file);
			new ListVideoThread(mVideoListHandler, videoPath_list, ListVideoThread.MSG_ARG_SAVED_VIDEO).start();
		}
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
			if (msg.arg1 == ListVideoThread.MSG_ARG_SAVED_VIDEO)
			{
				savedVideo_fragment.mVideoInfoList = GetVideoInfoListFromMsg.getVideoInfoListFromMsg(msg,ListVideoThread.MSG_CONTENT_VIDEO_INFO_LIST);
				savedVideo_fragment.mListView.setAdapter(new AllVideoAdapter(savedVideo_fragment.getActivity(), savedVideo_fragment.mVideoInfoList));
			}
		}
	}
}
