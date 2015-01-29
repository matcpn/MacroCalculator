package com.example.macrocalculator;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class Are_you_sure extends ActionBarActivity {

	DBAdapter myDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		openDB();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_are_you_sure);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.are_you_sure, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_are_you_sure,
					container, false);
			return rootView;
		}
	}
	private void openDB() 
	{
		myDb = new DBAdapter(this);
		myDb.open();
	}
	public void yes(View view)
	{
		this.myDb.deleteAll(DBAdapter.FOOD_MENU);
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		
	}
	public void no(View view)
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
