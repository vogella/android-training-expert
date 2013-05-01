package com.vogella.android.countries;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import android.text.TextUtils;

import com.vogella.android.countries.CountryUtils;

/** Simple JUnit test */
public class CountryUtilsTest extends TestCase {

	public void testGetCountries() {
		
		List<String> countries = CountryUtils.getCountries();
		Assert.assertNotNull(countries);
		Assert.assertTrue(countries.size() > 0);
		
		for (String country : countries) {
			Assert.assertTrue(!TextUtils.isEmpty(country));
		}
	}
	
}
