package com.amsoftgroup.geospatial.sqllite;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.amsoftgroup.geospatial.helper.AppHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DateDataSource {

	private String TAG = "DateDataSource";
	private SimpleDateFormat dateFormat = null;
	// Database fields
	private SQLiteDatabase database;
	private SqlLiteDateHelper dbHelper;
	private String[] allColumns = { 
			SqlLiteDateHelper.COLUMN_ID,
			SqlLiteDateHelper.COLUMN_DATE
			};

	public DateDataSource(Context context) {
		dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		dbHelper = new SqlLiteDateHelper(context);
		
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Date createDate() {
		Date d = new Date();
		ContentValues values = new ContentValues();

		String dateString = dateFormat.format(d);
		
		values.put(SqlLiteDateHelper.COLUMN_DATE, dateString);
	
		long insertId = database.insert(SqlLiteDateHelper.TABLE_DATE, null, values);
		
		// To show how to query
		
		Cursor cursor = database.query(SqlLiteDateHelper.TABLE_DATE,
				allColumns, SqlLiteDateHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		return cursorToDate(cursor);
		
		//return d;
		
	}

	public void deleteDate() {
		
		database.delete(SqlLiteDateHelper.TABLE_DATE, SqlLiteDateHelper.COLUMN_ID
				+ "", null);
	}

	public Date getAllDate() {
		Date d = null;
		Cursor cursor = database.query(SqlLiteDateHelper.TABLE_DATE,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			d = cursorToDate(cursor);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		
		return d;
	}

	private Date cursorToDate(Cursor cursor) {

		String dateString = cursor.getString(1);
	    Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			Log.e(TAG, dateString + ": " + e.toString());
		}

		return convertedDate;
	}
}
