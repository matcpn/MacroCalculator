package com.example.macrocalculator;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment; // imported the wrong shit ... happens too damn much
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Toast;
import android.widget.TextView;

public class ConsumedFoodActivity extends ActionBarActivity
{
	public FoodList consumedFoodList;
	TextView text;
	DBAdapter myDb;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        build();
    }

    private void build() {
        setContentView(R.layout.activity_consumed_food);

        openDB();
        consumedFoodList = myDb.loadConsumedList();
        this.populateListView();
        registerClickCallback();
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
    	text = (TextView)findViewById(R.id.textView1);
    	String content = "Calories: " + this.consumedFoodList.getCalorieCount() + " Protein: " + this.consumedFoodList.getProteinCount() + " Carbs: " + this.consumedFoodList.getCarbCount() + " Fats: " + this.consumedFoodList.getFatCount();
    	text.setText(content);
    }
	public void clearList(View view)
	{
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Intent i = new Intent(this, StatusBarNotification.class);
        builder.setTitle("Clear List");
        builder.setMessage("Would you like to clear the consumed foods list?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                // clear the table in the db
                //TODO
                //Make a new method that only deletes food from today and not clears the entire table
                Log.d("clearing", "clearing consumedFoods list");
                myDb.deleteAll(DBAdapter.CONSUMED_LIST);
                consumedFoodList = myDb.loadConsumedList();
                startService(i);
                build();
            }

        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
	}

    private void registerClickCallback()  //if a item is clicked
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ListView list = (ListView) findViewById(R.id.listView1);
        final Intent i = new Intent(this, StatusBarNotification.class);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id)
            {
                final TextView textView = (TextView) viewClicked;
                builder.setTitle("Remove Item");
                builder.setMessage("Would you like to remove the item from the consumed foods?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        // remove the item from db
                        Log.d("removing", String.valueOf(textView.getText()));
                        myDb.removeRowFromConsumedList(textView.getText().toString());
                        startService(i);
                        build();
                    }

                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
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