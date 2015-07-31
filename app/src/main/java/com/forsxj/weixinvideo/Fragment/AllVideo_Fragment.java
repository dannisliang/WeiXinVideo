package com.forsxj.weixinvideo.Fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.forsxj.weixinvideo.Adapter.AllVideoAdapter;
import com.forsxj.weixinvideo.Bean.VideoInfo;
import com.forsxj.weixinvideo.Custom.NoLeakHandler;
import com.forsxj.weixinvideo.R;
import com.forsxj.weixinvideo.WorkThread.ListVideoThread;

import java.util.ArrayList;

public class AllVideo_Fragment extends Fragment
{
	private ArrayList<VideoInfo> mVideoInfos = new ArrayList<>();
	private ListView mListView;
	private VideoListHandler mVideoListHandler;

	public AllVideo_Fragment()
	{
		mVideoListHandler = new VideoListHandler(this);
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
			allVideo_fragment.mVideoInfos = (ArrayList<VideoInfo>) msg.getData().getSerializable(ListVideoThread.MSG_CONTENT_VIDEOINFO_LIST);
			if (allVideo_fragment.mVideoInfos != null)
			{
				allVideo_fragment.mListView.setAdapter(new AllVideoAdapter(allVideo_fragment.getActivity(),allVideo_fragment.mVideoInfos));
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_all_video, container, false);
		mListView = (ListView) view.findViewById(R.id.listView_AllVideo);
//		new ListVideoThread(this,mVideoListHandler,mVideoInfos);
		return view;
	}

}
