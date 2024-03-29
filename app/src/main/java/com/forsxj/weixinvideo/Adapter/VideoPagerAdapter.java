package com.forsxj.weixinvideo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.forsxj.weixinvideo.Custom.VideoListFragment;

import java.util.ArrayList;
import java.util.List;

public class VideoPagerAdapter extends FragmentPagerAdapter
{
	private List<VideoListFragment> mFragments = new ArrayList<>();
	private static final String[] TITLES = {"微信视频","存档目录"};

	public VideoPagerAdapter(FragmentManager fm, List<VideoListFragment> fragments)
	{
		super(fm);
		mFragments = fragments;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return TITLES[position];
	}

	@Override
	public Fragment getItem(int position)
	{
		return mFragments.get(position);
	}

	@Override
	public int getCount()
	{
		return mFragments.size();
	}

}
