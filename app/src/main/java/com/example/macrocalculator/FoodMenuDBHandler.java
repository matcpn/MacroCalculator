package com.example.macrocalculator;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FoodMenuDBHandler {
	private static final String TAG = "DBAdapter";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_CALORIE_COUNT = "calorie_count";
	public static final String KEY_PROTEIN_COUNT = "protein_count";
	public static final String KEY_CARB_COUNT = "carb_count";
	public static final String KEY_FAT_COUNT = "fat_count";
	
	public static final int COL_ROWID = 0;
	public static final int COL_NAME = 1;
	public static final int COL_CALORIE_COUNT = 2;
	public static final int COL_PROTEIN_COUNT = 3;
	public static final int COL_CARB_COUNT = 4;
	public static final int COL_FAT_COUNT = 5;

	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_CALORIE_COUNT, KEY_PROTEIN_COUNT, KEY_CARB_COUNT, KEY_FAT_COUNT};
	
	public static final String DATABASE_NAME = "MacroCalculatorDatabase"; 
	public static final String FOOD_MENU = "foodMenu";
	public static final String CONSUMED_LIST = "consumedList";
	
	public static final int DATABASE_VERSION = 2;	
	

	private static final String SQL_CREATE_CONSUMED_LIST = 
			"create table " + CONSUMED_LIST 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "

			+ KEY_NAME + " text not null"

			+ ");";
	
	private static final String SQL_CREATE_FOOD_MENU = 
			"create table " + FOOD_MENU 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "

			+ KEY_NAME + " text not null, "
			+ KEY_CALORIE_COUNT + " integer not null, "
			+ KEY_PROTEIN_COUNT + " integer not null, "
			+ KEY_CARB_COUNT + " integer not null, "
			+ KEY_FAT_COUNT + " integer not null"
			
			+ ");";
	
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;
	
	public FoodMenuDBHandler(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	public FoodMenuDBHandler open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		myDBHelper.close();
	}
	
	public long insertRow(String DATABASE_TABLE_NAME, String name, int calorieCount, int proteinCount, int carbCount, int fatCount) {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_CALORIE_COUNT, calorieCount);
		initialValues.put(KEY_PROTEIN_COUNT, proteinCount);
		initialValues.put(KEY_CARB_COUNT, carbCount);
		initialValues.put(KEY_FAT_COUNT, fatCount);
		
		return db.insert(DATABASE_TABLE_NAME, null, initialValues);
	}
	
	public Cursor getAllRows(String DATABASE_TABLE_NAME) {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE_NAME, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
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
