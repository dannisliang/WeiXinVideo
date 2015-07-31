package com.forsxj.weixinvideo.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.forsxj.weixinvideo.Adapter.VideoPagerAdapter;
import com.forsxj.weixinvideo.Fragment.AllVideo_Fragment;
import com.forsxj.weixinvideo.Fragment.SavedVideo_Fragment;
import com.forsxj.weixinvideo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	private List<Fragment> mFragments = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
	}

	private void initUI()
	{
		//set toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle(getResources().getString(R.string.app_name));
		setSupportActionBar(toolbar);//取代ActionBar
		//set viewpager
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		initViewPager(viewPager);
		//set tabLayout
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		tabLayout.setFillViewport(true);
		tabLayout.setupWithViewPager(viewPager);
	}

	private void initViewPager(ViewPager viewPager)
	{
		mFragments.add(new AllVideo_Fragment());
		mFragments.add(new SavedVideo_Fragment());
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
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
