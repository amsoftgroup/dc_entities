package com.amsoftgroup.geospatial.sqllite;

import java.io.File;
import java.io.IOException;

import com.amsoftgroup.geospatial.helper.AppHelper;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class SqlLiteEntityHelper extends SQLiteOpenHelper {

	public static final String TABLE_ENTITYTYPES = "entitytypes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ENTITYTYPE = "entitytype";
	public static final String COLUMN_ENTITYID = "entityid";

	private static final String DATABASE_NAME = "entitytypes.db";
	private static final int DATABASE_VERSION = AppHelper.getSqlLitaDBVersion();	
	
	private String TAG = "SqlLiteEntityHelper";
	
/*
	private String DATA_DIR = Environment.getDataDirectory().getAbsolutePath();
	private String APP_NAME = "";
	private String FILENAME = "AMSOFTGROUP_DB";
*/
	/*
	public SqlLiteEntityHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	*/
	public SqlLiteEntityHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_ENTITYTYPES + "( " + COLUMN_ID + " integer primary key autoincrement, "  
			+ COLUMN_ENTITYID + " integer not null, "
			+ COLUMN_ENTITYTYPE + " text not null);"; 

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG,"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_ENTITYTYPES);
		onCreate(db);
	}


}
