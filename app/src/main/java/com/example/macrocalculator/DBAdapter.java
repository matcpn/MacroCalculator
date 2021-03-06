package com.example.macrocalculator;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DBAdapter {

	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String KEY_NAME = "name";
	public static final String KEY_CALORIE_COUNT = "calorie_count";
	public static final String KEY_PROTEIN_COUNT = "protein_count";
	public static final String KEY_CARB_COUNT = "carb_count";
	public static final String KEY_FAT_COUNT = "fat_count";
    public static final String KEY_DATE_CONSUMED = "date_consumed";
	
	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_NAME = 1;
	public static final int COL_CALORIE_COUNT = 2;
	public static final int COL_PROTEIN_COUNT = 3;
	public static final int COL_CARB_COUNT = 4;
	public static final int COL_FAT_COUNT = 5;
    public static final int COL_DATE_CONSUMED = 6;
	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_CALORIE_COUNT, KEY_PROTEIN_COUNT, KEY_CARB_COUNT, KEY_FAT_COUNT, KEY_DATE_CONSUMED};
	
	// DB info: it's name, and the tables we are using.
	public static final String DATABASE_NAME = "a"; //FOR WHATEVER REASON THE DATABASE NAME NEEDED TO CHANGE
	public static final String FOOD_MENU = "foodMenu";
	public static final String CONSUMED_LIST = "consumedList";
	
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 3;
				
	private static final String SQL_CREATE_FOOD_MENU = 
			"create table " + FOOD_MENU 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "

			+ KEY_NAME + " text not null, "
			
			+ KEY_CALORIE_COUNT + " integer not null, "
			+ KEY_PROTEIN_COUNT + " integer not null, "
			+ KEY_CARB_COUNT + " integer not null, "
			+ KEY_FAT_COUNT + " integer not null"
			
			// Rest  of creation:
			+ ");";
	
	private static final String SQL_CREATE_CONSUMED_LIST = 
			"create table " + CONSUMED_LIST 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "

			+ KEY_NAME + " text not null, "
            + KEY_CALORIE_COUNT + " integer not null, "
            + KEY_PROTEIN_COUNT + " integer not null, "
            + KEY_CARB_COUNT + " integer not null, "
            + KEY_FAT_COUNT + " integer not null, "
            + KEY_DATE_CONSUMED + " text not null" // date time formatted as "YYYY-MM-DD"
			+ ");";
	
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		myDBHelper.close();
	}
	
	public long addRowToMenu(String DATABASE_TABLE_NAME, String name, int calorieCount, int proteinCount, int carbCount, int fatCount) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_CALORIE_COUNT, calorieCount);
		initialValues.put(KEY_PROTEIN_COUNT, proteinCount);
		initialValues.put(KEY_CARB_COUNT, carbCount);
		initialValues.put(KEY_FAT_COUNT, fatCount);
		
		return db.insert(DATABASE_TABLE_NAME, null, initialValues);
	}
		
	public long addRowToConsumedList(String DATABASE_TABLE_NAME, String name, double calorieCount, double proteinCount, double carbCount, double fatCount)
	{
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("date adding: ", ft.format(date));
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_CALORIE_COUNT, calorieCount);
        initialValues.put(KEY_PROTEIN_COUNT, proteinCount);
        initialValues.put(KEY_CARB_COUNT, carbCount);
        initialValues.put(KEY_FAT_COUNT, fatCount);
        initialValues.put(KEY_DATE_CONSUMED, ft.format(date));

		return db.insert(DATABASE_TABLE_NAME, null, initialValues);
	}
	
	public Cursor getAllData(String DATABASE_TABLE_NAME) 
	{
		return db.rawQuery("select * from " + DATABASE_TABLE_NAME, null);
	}
	public Cursor GetFoodInfo()
	{
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "select * " +
                "from consumedList c " +
                "where c.date_consumed = \'" + ft.format(now) + "\';";
        Log.d("query: ", sql);
		Cursor c = db.rawQuery(sql, null);
		return c;
	}
	public FoodList loadFoodMenu()
	{
		Cursor cursor = getAllData(FOOD_MENU);
		return cursorToFoodList(cursor);
	}
	

	public FoodList loadConsumedList()
	{
		Cursor cursor = GetFoodInfo();
    	return cursorToConsumedList(cursor);
	}
	
	public FoodList cursorToFoodList(Cursor cursor)
	{
		FoodList foodList = new FoodList();
		if (cursor.moveToFirst()) 
		{
			do 
			{
				String name = cursor.getString(DBAdapter.COL_NAME);
				int calorieCount = cursor.getInt(DBAdapter.COL_CALORIE_COUNT);
				int proteinCount = cursor.getInt(DBAdapter.COL_PROTEIN_COUNT);
				int carbCount = cursor.getInt(DBAdapter.COL_CARB_COUNT);
				int fatCount = cursor.getInt(DBAdapter.COL_FAT_COUNT);

                Food newFood = new Food(name, calorieCount, proteinCount, carbCount, fatCount);
				foodList.addFood(newFood);
			} 
			while(cursor.moveToNext());
		}
		return foodList;
	}

    public FoodList cursorToConsumedList(Cursor cursor)
    {
        FoodList foodList = new FoodList();
        if (cursor.moveToFirst())
        {
            do
            {
                String name = cursor.getString(DBAdapter.COL_NAME);
                int calorieCount = cursor.getInt(DBAdapter.COL_CALORIE_COUNT);
                int proteinCount = cursor.getInt(DBAdapter.COL_PROTEIN_COUNT);
                int carbCount = cursor.getInt(DBAdapter.COL_CARB_COUNT);
                int fatCount = cursor.getInt(DBAdapter.COL_FAT_COUNT);
                String dateConsumed = cursor.getString(DBAdapter.COL_DATE_CONSUMED);

                Food newFood = new Food(name, calorieCount, proteinCount, carbCount, fatCount, dateConsumed);
                foodList.addFood(newFood);
            }
            while(cursor.moveToNext());
        }
        return foodList;
    }
	
	
	
	
	
	
	
	
	public boolean deleteRow(String DATABASE_TABLE_NAME, long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE_NAME, where, null) != 0;
	}
	
	public void deleteAll(String DATABASE_TABLE_NAME) {
		Cursor c = getAllData(DATABASE_TABLE_NAME);
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(DATABASE_TABLE_NAME, c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}

    public void removeRowFromConsumedList(String name) {
        String sql = "select c._id"
                + " from consumedList c " +
                "where c.name = '" + name + "';";
        Log.d("query", sql);
        Cursor c = db.rawQuery(sql, null);
        if(c.moveToFirst()) {
            int id = c.getInt(COL_ROWID);
            sql = "_id = " + id;
            Log.d("query", sql);
            Log.d("rows deleted", String.valueOf(db.delete(CONSUMED_LIST, sql, null)));
        }
    }

    public void removeRowFromFoodList(String name) {
        String sql = "select f._id"
                + " from foodMenu f " +
                "where f.name = '" + name + "';";
        Log.d("query", sql);
        Cursor c = db.rawQuery(sql, null);
        if(c.moveToFirst()) {
            int id = c.getInt(COL_ROWID);
            sql = "_id = " + id;
            Log.d("query", sql);
            Log.d("rows deleted", String.valueOf(db.delete(FOOD_MENU, sql, null)));
        }
    }


    private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(SQL_CREATE_FOOD_MENU);	
			_db.execSQL(SQL_CREATE_CONSUMED_LIST);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + FOOD_MENU);
			_db.execSQL("DROP TABLE IF EXISTS " + CONSUMED_LIST);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
