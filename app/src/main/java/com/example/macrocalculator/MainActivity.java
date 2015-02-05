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


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	public FoodList FoodMenu;
	DBAdapter myDb;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		if (savedInstanceState == null) 
		{
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}


		build();

   	}
	private void build() {
        setContentView(R.layout.activity_main);
        openDB();
        FoodMenu = myDb.loadFoodMenu();
        setupFoodSearchButton();
        populateListView();
        registerClickCallback();

        startService(new Intent(this, StatusBarNotification.class));
    }
	private void openDB() 
	{
		myDb = new DBAdapter(this);
		myDb.open();
	}

    private void setupFoodSearchButton() {
        myDb.close();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Button SearchButton = (Button) findViewById(R.id.delete_menu);
        final Intent i = new Intent(this, StatusBarNotification.class);
        final DBAdapter db = myDb;

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Add Item");
                builder.setMessage("Would you like to permanently delete the food menu?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing but close the dialog
                        myDb.open();
                        db.deleteAll(DBAdapter.FOOD_MENU);
                        myDb.close();
                        dialog.dismiss();
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
	
	public void sendMessage(View view) // called from the buttons 
	{
		myDb.close();
	    Intent intent = new Intent(this, ConsumedFoodActivity.class);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		ListView list = (ListView) findViewById(R.id.listViewMain);
        final Intent i = new Intent(this, StatusBarNotification.class);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) 
			{
                final TextView textView = (TextView) viewClicked;


                builder.setTitle("Add Item");
                builder.setMessage("Would you like to add the item to the consumed list or remove it from the menu?");

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        // add to the db
                        myDb.open();
                        myDb.addRowToConsumedList(ConsumedListDBHandler.CONSUMED_LIST, textView.getText().toString());
                        myDb.close();
                        dialog.dismiss();
                        startService(i);
                    }

                });

                builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing (for now)
                        myDb.open();
                        myDb.removeRowFromFoodList(textView.getText().toString());
                        myDb.close();
                        build();
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
