package com.amsoftgroup.geospatial.sqllite;

import com.amsoftgroup.geospatial.helper.AppHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlLiteDateHelper extends SQLiteOpenHelper {

	private String TAG = "SqlLiteDateHelper";
	public static final String TABLE_DATE = "date";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE = "date";

	private static final String DATABASE_NAME = "date.db";
	private static final int DATABASE_VERSION = AppHelper.getSqlLitaDBVersion();	
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_DATE + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_DATE
			+ " text not null);";

	public SqlLiteDateHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SqlLiteDateHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_DATE);
		onCreate(db);
	}

}
