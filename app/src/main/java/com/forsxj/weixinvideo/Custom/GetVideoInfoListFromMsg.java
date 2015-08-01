package com.forsxj.weixinvideo.Custom;

import android.os.Message;

import com.forsxj.weixinvideo.Bean.VideoInfo;

import java.util.ArrayList;

public class GetVideoInfoListFromMsg
{
	public static ArrayList<VideoInfo> getVideoInfoListFromMsg(Message msg, String key)
	{
		Object o = msg.getData().getSerializable(key);
		ArrayList<VideoInfo> list = new ArrayList<>();
		if (o instanceof ArrayList<?> && ((ArrayList) o).size() > 0)
		{
			for (Object oo : (ArrayList) o)
			{
				if (oo instanceof VideoInfo)
				{
					list.add((VideoInfo) oo);
				}
			}
		}
		return list;
	}
}
