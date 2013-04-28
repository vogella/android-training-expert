package com.example.android.rssfeed;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RssfeedActivity extends Activity implements
		MyListFragment.OnItemSelectedListener {

	private boolean isInOnePaneLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rssfeed);
		
		isInOnePaneLayout = findViewById(R.id.container) != null;
		
		if (isInOnePaneLayout) {
			FragmentManager fm = getFragmentManager();
			MyListFragment fragment =
					(MyListFragment) fm.findFragmentByTag("list");
			if (fragment == null) {
				fragment = new MyListFragment();
				fm.beginTransaction()
					.add(R.id.container, fragment, "list").commit();
			}
		}
		
		String link = getIntent().getStringExtra("link");
		if (link != null) {
			onRssItemSelected(link);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_refresh:
			
			if (isInOnePaneLayout) {
				MyListFragment fragment = (MyListFragment) 
						getFragmentManager().findFragmentByTag("list");
				fragment.updateListContent();
			} else {
				MyListFragment fragment = (MyListFragment) 
						getFragmentManager().findFragmentById(R.id.list);
				fragment.updateListContent();
			}
			
			break;
		case R.id.action_settings:
			Intent intent = new Intent(this, MyPreferenceActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onRssItemSelected(String link) {
		
		FragmentManager fm = getFragmentManager();
		
		if (isInOnePaneLayout) {
			DetailFragment fragment = new DetailFragment();
			
			Bundle bundle = new Bundle();
			bundle.putString("link", link);
			fragment.setArguments(bundle);
			
			fm.beginTransaction()
				.replace(R.id.container, fragment)
				.addToBackStack(null)
				.commit();
			
		} else {
			DetailFragment fragment = 
					(DetailFragment) fm.findFragmentById(R.id.detail);
			fragment.setText(link);
		}
		
	}
}
