package com.vogella.bgp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.vogella.bgp.R;
import com.vogella.bgp.fragments.AsyncTaskLoaderFragment;
import com.vogella.bgp.fragments.AsyncTaskWorkerFragment;
import com.vogella.bgp.fragments.IntentServiceWorkerFragment1;
import com.vogella.bgp.fragments.IntentServiceWorkerFragment2;
import com.vogella.bgp.fragments.IntentServiceWorkerFragment3;
import com.vogella.bgp.fragments.IntentServiceWorkerFragment4;
import com.vogella.bgp.fragments.RetainedWorkerFragment;
import com.vogella.bgp.fragments.ThreadWorkerFragment;
import com.vogella.bgp.fragments.TimerFragment;

public class MainActivity extends FragmentActivity {

	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		
		@SuppressWarnings("rawtypes")
		private final Class[] mFragments 
			= new Class[] {
				TimerFragment.class,
				ThreadWorkerFragment.class,
				AsyncTaskWorkerFragment.class,
				AsyncTaskLoaderFragment.class,
				RetainedWorkerFragment.class,
				IntentServiceWorkerFragment1.class,
				IntentServiceWorkerFragment2.class,
				IntentServiceWorkerFragment3.class,
				IntentServiceWorkerFragment4.class,
		};
		
		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return mFragments.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "";
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public Fragment getItem(int position) {
			Context c = getApplicationContext();
			return Fragment.instantiate(c, mFragments[position].getName());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager fm = getSupportFragmentManager();
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new MyFragmentPagerAdapter(fm));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
