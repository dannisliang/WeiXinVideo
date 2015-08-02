package com.forsxj.weixinvideo.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.forsxj.weixinvideo.Adapter.VideoPagerAdapter;
import com.forsxj.weixinvideo.Fragment.AllVideo_Fragment;
import com.forsxj.weixinvideo.Fragment.SavedVideo_Fragment;
import com.forsxj.weixinvideo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	private List<Fragment> mFragments = new ArrayList<>();
	private boolean mShowMenu = false;
	private List<ImageView> mFabList = new ArrayList<>();
	private int mFab_Y;

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
		//set Fab
//		final FloatingActionButton Fab = (FloatingActionButton) findViewById(R.id.Fab);
//		mFab_Y = Utils.getScreenHeight(this) - Fab.getHeight();

//		System.out.println(mFab_Y);
//		ImageView Fab_Menu_A = (ImageView) findViewById(R.id.Fab_Menu_A);
//		ImageView Fab_Menu_B = (ImageView) findViewById(R.id.Fab_Menu_B);
//		ImageView Fab_Menu_C = (ImageView) findViewById(R.id.Fab_Menu_C);
//		ImageView Fab_Menu_D = (ImageView) findViewById(R.id.Fab_Menu_D);
//		mFabList.add(Fab_Menu_A);
//		mFabList.add(Fab_Menu_B);
//		mFabList.add(Fab_Menu_C);
//		mFabList.add(Fab_Menu_D);
//		Fab.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				if (mShowMenu)
//				{
//					closeMenu();
//				}
//				else
//				{
//					showMenu();
//				}
//				System.out.println(Fab.getMeasuredWidth());
//			}
//		});
	}

	private void closeMenu()
	{
		mShowMenu = false;
	}

	private void showMenu()
	{
		mShowMenu = true;
//		mFabList.get(0).setVisibility(View.VISIBLE);
//		AnimatorSet animatorSet = new AnimatorSet();
//		ObjectAnimator animator_Alpha = ObjectAnimator.ofFloat(mFabList.get(0), "alpha", 0f, 1f);
//		animator_Alpha.setDuration(1000);
//		ObjectAnimator animator_Position = ObjectAnimator.ofInt(mFabList.get(0),
//				"translationY", mFab_Y, mFab_Y - (mFabList.get(0).getHeight() + 100) * 4);
//		animator_Position.setDuration(1000);
//		animatorSet.playTogether(animator_Alpha,animator_Position);
//		animatorSet.start();
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
