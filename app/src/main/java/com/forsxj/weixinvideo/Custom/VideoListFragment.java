package com.forsxj.weixinvideo.Custom;

import android.os.Handler;
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
			updateListView();
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
			updateListView();
		}
	}

	private void updateListView()
	{
		((BaseAdapter)(getListView().getAdapter())).notifyDataSetChanged();
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
			final List<File> selectedVideo = getSelectedVideo();
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
					//开始删除时不允许打开菜单
					for (int i = 0; i < selectedVideo.size(); i++)
					{
						selectedVideo.get(i).delete();
					}
					reLoadVideoList();
				}
			});
			snackbar.show();
		}
	}

	abstract public void reLoadVideoList();

	public Handler getMainHandler()
	{
		return CApplication.getMainHandler();
	}

	//获取选中的文件
	public ArrayList<File> getSelectedVideo()
	{
		ArrayList<File> files = new ArrayList<>();
		int count = getListView().getAdapter().getCount();
		for (int i = 0; i < count; i++)
		{
			VideoInfo videoInfo = (VideoInfo)getListView().getAdapter().getItem(i);
			if (videoInfo.getSelected())
			{
				files.add(new File(videoInfo.getFileName()));
			}
		}
		return files;
	}
}
