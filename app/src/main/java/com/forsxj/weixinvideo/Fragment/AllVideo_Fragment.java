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
import com.forsxj.weixinvideo.Custom.NoLeakHandler;
import com.forsxj.weixinvideo.Custom.SnackBarToast;
import com.forsxj.weixinvideo.Custom.Utils;
import com.forsxj.weixinvideo.R;
import com.forsxj.weixinvideo.WorkThread.ListVideoThread;

import java.io.File;
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

	private void initVideoList()
	{
		String videoFolderPath = Utils.getInternalRootDirectoryPath() + "/tencent/MicroMsg/";
		File file_VideoFolderPath = new File(videoFolderPath);
		ArrayList<File> videoPath_Found = new ArrayList<>();
		if (file_VideoFolderPath.exists())
		{
			File[] files = file_VideoFolderPath.listFiles();
			if (files.length > 0)
			{
				for (int i = 0; i < files.length; i++)
				{
					if (files[i].isDirectory()
							&& files[i].getName().length() == 32
							&& files[i].canRead())
					{
						File file = new File(files[i].getAbsolutePath() + "/video");
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
			SnackBarToast.showDefaultSnackBarToast_Short(mListView,"没有找到微信视频目录！");
			return;
		}
		new ListVideoThread(mVideoListHandler,videoPath_Found).start();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_all_video, container, false);
		mListView = (ListView) view.findViewById(R.id.listView_AllVideo);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(((VideoInfo) mListView.getAdapter().getItem(position)).getFileName()),"video/mp4");
				startActivity(intent);
			}
		});
		initVideoList();
		return view;
	}
}
