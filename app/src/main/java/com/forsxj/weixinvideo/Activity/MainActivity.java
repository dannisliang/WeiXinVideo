package com.forsxj.weixinvideo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.forsxj.weixinvideo.Adapter.VideoPagerAdapter;
import com.forsxj.weixinvideo.Custom.CApplication;
import com.forsxj.weixinvideo.Custom.NoLeakHandler;
import com.forsxj.weixinvideo.Custom.VideoListFragment;
import com.forsxj.weixinvideo.Fragment.AllVideo_Fragment;
import com.forsxj.weixinvideo.Fragment.SavedVideo_Fragment;
import com.forsxj.weixinvideo.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	private List<VideoListFragment> mFragments = new ArrayList<>();
	private FloatingActionMenu mFab_menu;
	private ViewPager mViewPager;
	private FloatingActionButton mFab_save;
	private FloatingActionButton mFab_sync;
	private MainHandler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mHandler = new MainHandler(this);//初始化UI Handler
		CApplication.setMainHandler(mHandler);
		initUI();
	}

	private void initUI()
	{
		//findviewById
		mFab_menu = (FloatingActionMenu) findViewById(R.id.Fab_menu);
		FloatingActionButton Fab_del = (FloatingActionButton) findViewById(R.id.menu_item_del);
		FloatingActionButton Fab_selectAll = (FloatingActionButton) findViewById(R.id.menu_item_selectAll);
		FloatingActionButton Fab_cancelAll = (FloatingActionButton) findViewById(R.id.menu_item_cancelAll);
		mFab_save = (FloatingActionButton) findViewById(R.id.menu_item_save);
		mFab_sync = (FloatingActionButton) findViewById(R.id.menu_item_sync);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		//set toolbar
		toolbar.setTitle(getResources().getString(R.string.app_name));
		setSupportActionBar(toolbar);//取代ActionBar
		//set viewpager
		initViewPager(mViewPager);
		//set tabLayout
		tabLayout.setFillViewport(true);
		tabLayout.setupWithViewPager(mViewPager);
		//set Fab
		Fab_del.setOnClickListener(new Fab_Menu_Item_OnClickListener());
		Fab_selectAll.setOnClickListener(new Fab_Menu_Item_OnClickListener());
		Fab_cancelAll.setOnClickListener(new Fab_Menu_Item_OnClickListener());
		mFab_save.setOnClickListener(new Fab_Menu_Item_OnClickListener());
		mFab_sync.setOnClickListener(new Fab_Menu_Item_OnClickListener());
	}

	private class Fab_Menu_Item_OnClickListener implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			int which_listView = mViewPager.getCurrentItem();//当前是哪一个ListView
			switch (v.getId())
			{
				case R.id.menu_item_del:
					mFragments.get(which_listView).delSelected();
					break;
				case R.id.menu_item_selectAll:
					mFragments.get(which_listView).selectAll();
					break;
				case R.id.menu_item_cancelAll:
					mFragments.get(which_listView).cancelAll();
					break;
				case R.id.menu_item_save:
					if (which_listView == 0)
					{
						((AllVideo_Fragment)(mFragments.get(which_listView))).saveSelected();
					}
					break;
				case R.id.menu_item_sync:
					if (which_listView == 0)
					{
						((AllVideo_Fragment)(mFragments.get(which_listView))).sync();
					}
					break;
			}
			mFab_menu.close(true);
		}
	}

	private void initViewPager(ViewPager viewPager)
	{
		mFragments.add(new AllVideo_Fragment());
		mFragments.add(new SavedVideo_Fragment());
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{

			}

			@Override
			public void onPageSelected(int position)
			{
				if (position == 0)
				{
					mFab_save.setVisibility(View.VISIBLE);
					mFab_sync.setVisibility(View.VISIBLE);
				}
				else
				{
					mFab_save.setVisibility(View.GONE);
					mFab_sync.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});
		viewPager.setAdapter(new VideoPagerAdapter(getSupportFragmentManager(), mFragments));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			startActivity(new Intent(MainActivity.this,SettingActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private static class MainHandler extends NoLeakHandler<MainActivity>
	{

		public MainHandler(MainActivity outClass)
		{
			super(outClass);
		}

		@Override
		public void handleMessage(Message msg, MainActivity mainActivity)
		{

		}
	}
}
