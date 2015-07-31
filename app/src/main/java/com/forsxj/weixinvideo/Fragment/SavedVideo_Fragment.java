package com.forsxj.weixinvideo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forsxj.weixinvideo.R;

public class SavedVideo_Fragment extends Fragment
{
	public SavedVideo_Fragment()
	{
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_saved_video, container, false);
	}
	
	
}
