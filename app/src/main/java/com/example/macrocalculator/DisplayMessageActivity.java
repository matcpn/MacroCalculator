package com.example.macrocalculator;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment; // imported the wrong shit ... happens too damn much
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Toast;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMessageActivity extends ActionBarActivity
{
	FoodList consumedFoodList;
	TextView text;
	DBAdapter myDb;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message); 
        
        
        openDB();
        consumedFoodList = myDb.loadConsumedList();
        this.populateListView();
        setTextField();
    }
    
	private void openDB() 
	{
		myDb = new DBAdapter(this);
		myDb.open();
	}
    public void populateListView() // has automatic scrolling ... PRAISE JESUS
    {
    	String[] myItems = this.consumedFoodList.getFoodNames();
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.da_item, myItems);
    	ListView list = (ListView) findViewById(R.id.listView1); // gotta use the different listviews that occur on different xml guis
    	list.setAdapter(adapter);
    }
    
    public void setTextField()
    {
    	//text.setText(""); // clear textbox, completely unneccesary because we are setting it, not appending.
    	text = (TextView)findViewById(R.id.textView1);
    	String content = "Calories: " + this.consumedFoodList.getCalorieCount() + " Protein: " + this.consumedFoodList.getProteinCount() + " Carbs: " + this.consumedFoodList.getCarbCount() + " Fats: " + this.consumedFoodList.getFatCount();
    	text.setText(content);
    }
	public void clearList(View view)
	{
		myDb.deleteAll(DBAdapter.CONSUMED_LIST);
		consumedFoodList = myDb.loadConsumedList();
        this.populateListView();
        setTextField();
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
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) 
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState) {
              View rootView = inflater.inflate(R.layout.fragment_display_message,
                      container, false);
              return rootView;
        }
    }
    

    
}