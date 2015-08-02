package com.forsxj.weixinvideo.Custom;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.forsxj.weixinvideo.Bean.VideoInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class VideoListFragment extends Fragment
{
	abstract public ListView getListView();

	public void selectAll()
	{
		if (getListView() != null && getListView().getAdapter() != null)
		{
			for (int i = 0; i < getListView().getAdapter().getCount(); i++)
			{
				((VideoInfo)(getListView().getAdapter().getItem(i))).setSelected(true);
			}
			((BaseAdapter)(getListView().getAdapter())).notifyDataSetChanged();
		}
	}

	public void cancelAll()
	{
		if (getListView() != null && getListView().getAdapter() != null)
		{
			for (int i = 0; i <  getListView().getAdapter().getCount(); i++)
			{
				((VideoInfo)(getListView().getAdapter().getItem(i))).setSelected(false);
			}
			((BaseAdapter)(getListView().getAdapter())).notifyDataSetChanged();
		}
	}

	public void delSelected()
	{
		if (getListView() != null && getListView().getAdapter() != null)
		{
			int count = getListView().getAdapter().getCount();
			if (count == 0)
			{
				SnackBarToast.showDefaultSnackBarToast_Short(getListView(),"没有可供删除的文件！");
				return;
			}
			//获取选中的文件
			final List<VideoInfo> selectedVideo = new ArrayList<>();
			for (int i = 0; i < count; i++)
			{
				VideoInfo videoInfo = (VideoInfo)getListView().getAdapter().getItem(i);
				if (videoInfo.getSelected())
				{
					selectedVideo.add(videoInfo);
				}
			}
			//开始删除
			if (selectedVideo.size() == 0)
			{
				SnackBarToast.showDefaultSnackBarToast_Short(getListView(),"没有选中文件！");
				return;
			}
			Snackbar snackbar = Snackbar.make(getListView(), "确定要删除吗？", Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					for (int i = 0; i < selectedVideo.size(); i++)
					{
						File file = new File(selectedVideo.get(i).getFileName());
						file.delete();
					}
				}
			});
			snackbar.show();
		}
	}
}
