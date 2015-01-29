package com.example.macrocalculator;

// if u erase the app, u lose the text file and it only loads what it has in the oncreate method ... with options.add ...


// solid video: http://www.youtube.com/watch?v=gFHhuRY-OxA
// plenty of solid youtube content for andorid.


// we are so retarded, we were trying to parse a sting by spaces and the name of the string had spaces in it...options.add(new Food("Protein Shake", 30f, 0f, 0f));
//protein shake has spaces u idiot.


//want to use better data storage/sort methods
//
//werid problem happens with the back button 
//if u hit the back button then u restore info so u call closedb but then if it hits hack it tries to use a closed db without reopening it. 


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;
import android.widget.Toast;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.ActionBar;


public class MainActivity extends ActionBarActivity { 
	public FoodList FoodMenu;
	DBAdapter myDb;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) 
		{
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}


		openDB();
		FoodMenu = myDb.loadFoodMenu();
		
		populateListView(); 
		registerClickCallback();

   	}
	
	private void openDB() 
	{
		myDb = new DBAdapter(this);
		myDb.open();
	}
	
	
	public void are_you_sure(View view) 
	{
		myDb.close();
	    Intent intent = new Intent(this, Are_you_sure.class);
		startActivity(intent);
	}
	
	
	public void sendMessage(View view) // called from the buttons 
	{
		myDb.close();
	    Intent intent = new Intent(this, DisplayMessageActivity.class);
		startActivity(intent);
	}
	
	public void clearList(View view)
	{
		myDb.deleteAll(DBAdapter.CONSUMED_LIST);
	}
	public void AddItem(View view) // called from the buttons 
	{
		myDb.close();
	    Intent intent = new Intent(this, AddItem.class);
		startActivity(intent);
	}
	
	
	public void populateListView() // has automatic scrolling ... PRAISE JESUS
    {
    	String[] myItems = this.FoodMenu.getFoodNames();
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.da_item, myItems); // luckily if myItems is empty it does not bust
    	ListView list = (ListView) findViewById(R.id.listViewMain);
    	list.setAdapter(adapter);
    }
	
	private void registerClickCallback()  //if a item is clicked
	{
		ListView list = (ListView) findViewById(R.id.listViewMain);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) 
			{ 
				TextView textView = (TextView) viewClicked; 
				myDb.addRowToConsumedList(ConsumedListDBHandler.CONSUMED_LIST, textView.getText().toString());
			}
		});
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}
	}
}
