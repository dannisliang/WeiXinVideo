package com.forsxj.weixinvideo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class VideoAdapter extends BaseAdapter
{
	private LayoutInflater mLayoutInflater;
	private ArrayList<VideoInfo> mVideoInfoList = new ArrayList<>();

	public VideoAdapter(Context context, ArrayList<VideoInfo> videoInfoList)
	{
		this.mVideoInfoList = videoInfoList;
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount()
	{
		return mVideoInfoList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mVideoInfoList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		if (convertView == null)
		{
			convertView = mLayoutInflater.inflate(R.layout.listview_item,null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);
			viewHolder.mTextView_VideoName = (TextView) convertView.findViewById(R.id.textView_Name);
			viewHolder.mTextView_VideoInfo = (TextView) convertView.findViewById(R.id.textView_Info);
			viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mImageView.setImageBitmap(mVideoInfoList.get(position).getVideoCover());
		viewHolder.mTextView_VideoName.setText(mVideoInfoList.get(position).getVideoName());
		viewHolder.mTextView_VideoInfo.setText(mVideoInfoList.get(position).getVideoDetailInfo());
		viewHolder.mCheckBox.setChecked(mVideoInfoList.get(position).getSelected());
		viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mVideoInfoList.get(position).setSelected(((CheckBox) v).isChecked());
			}
		});
		return convertView;
	}

	public void setAdapter(ArrayList<VideoInfo> videoInfoList)
	{
		this.mVideoInfoList.clear();
		if (videoInfoList.size() > 0)
		{
			for (int i = 0; i < videoInfoList.size(); i++)
			{
				mVideoInfoList.add(videoInfoList.get(i));
			}
		}
	}

	private class ViewHolder
	{
		public ImageView mImageView;
		public TextView mTextView_VideoName;
		public TextView mTextView_VideoInfo;
		public CheckBox mCheckBox;
	}
}
