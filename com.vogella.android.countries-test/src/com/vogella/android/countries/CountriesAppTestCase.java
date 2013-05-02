package com.vogella.android.countries;

import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;
import android.test.RenamingDelegatingContext;

public class CountriesAppTestCase 
	extends ApplicationTestCase<CountriesApp> {

	public CountriesAppTestCase() {
		super(CountriesApp.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// inject renaming delegating context
		RenamingDelegatingContext ctx = new RenamingDelegatingContext(getSystemContext(), "test_");
		setContext(ctx);
	}
	
	public void testDatabaseInitialized() {
		
		// cause all onCreate() to be called
		createApplication();
		
		// checkout our database
		DatabaseHelper dbHelper = getApplication().getDatabase();
		assertNotNull(dbHelper);
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		assertNotNull(db);
		assertTrue(db.isOpen());
		
	}
	
}
