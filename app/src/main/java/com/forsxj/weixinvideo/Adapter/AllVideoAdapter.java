package com.forsxj.weixinvideo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.forsxj.weixinvideo.Bean.VideoInfo;
import com.forsxj.weixinvideo.R;

import java.util.ArrayList;

public class AllVideoAdapter extends BaseAdapter
{
	private LayoutInflater mLayoutInflater;
	private ArrayList<VideoInfo> mVideoInfoList = new ArrayList<>();
	private Context mContext;

	public AllVideoAdapter(Context context, ArrayList<VideoInfo> videoInfoList)
	{
		this.mVideoInfoList = videoInfoList;
		mLayoutInflater = LayoutInflater.from(context);
		mContext = context;
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
			viewHolder.mTextView_VideoSize = (TextView) convertView.findViewById(R.id.textView_Size);
			viewHolder.mTextView_VideoTime = (TextView) convertView.findViewById(R.id.textView_Time);
			viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Glide.with(mContext).load(mVideoInfoList.get(position).getFileName()).asBitmap().into(viewHolder.mImageView);
		viewHolder.mTextView_VideoName.setText(mVideoInfoList.get(position).getVideoName());
		viewHolder.mTextView_VideoSize.setText(mVideoInfoList.get(position).getVideoSize());
		viewHolder.mTextView_VideoTime.setText(mVideoInfoList.get(position).getVideoTime());
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

	private class ViewHolder
	{
		public ImageView mImageView;
		public TextView mTextView_VideoName;
		public TextView mTextView_VideoSize;
		public TextView mTextView_VideoTime;
		public CheckBox mCheckBox;
	}
}
