package com.forsxj.weixinvideo.Custom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.forsxj.weixinvideo.Bean.VideoInfo;

public class VideoListView extends ListView implements AdapterView.OnItemClickListener,
		AdapterView.OnItemLongClickListener,
		AbsListView.OnScrollListener
{
	private Context mContext;

	public VideoListView(Context context)
	{
		this(context, null);
	}

	public VideoListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
		setOnItemClickListener(this);
		setOnItemLongClickListener(this);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse(((VideoInfo) getAdapter().getItem(position)).getFileName()), "video/mp4");
		mContext.startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
	{

		return false;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{

	}
}
