package com.vogella.android.countries;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vogella.android.countries.MainActivity.StringListAdapter;

/** Simple test case class for our application */
public class SimpleAndroidTestCase extends AndroidTestCase {

	/** Test our StringListAdapter class */
	public void testStringListAdapter() {
		
		Context context = getContext();
		
		StringListAdapter adapter = new StringListAdapter(context, CountryUtils.getCountries());
		assertTrue(adapter.getCount() > 0);
		
		FrameLayout layout = new FrameLayout(context);

		View view = adapter.getView(0, null, layout);
		assertTrue(view instanceof TextView);
		
		// Here we create another view by reusing previous view
		View view2 = adapter.getView(1, view, layout);
		assertSame(view, view2);
		
	}
}