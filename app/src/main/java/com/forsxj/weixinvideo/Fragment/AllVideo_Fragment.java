package com.forsxj.weixinvideo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forsxj.weixinvideo.R;

public class AllVideo_Fragment extends Fragment
{

	public AllVideo_Fragment()
	{
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_all_video, container, false);
		return view;
	}

}
