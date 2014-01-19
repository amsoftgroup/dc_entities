package com.amsoftgroup.geospatial.sqllite;

import java.util.ArrayList;
import java.util.List;

import com.amsoftgroup.geospatial.dc.business.EntityType;
import com.amsoftgroup.geospatial.helper.AppHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EntityTypeDataSource {

	// Database fields
	private SQLiteDatabase database;
	private SqlLiteEntityHelper dbHelper;
	private String[] allColumns = { 
			SqlLiteEntityHelper.COLUMN_ID,
			SqlLiteEntityHelper.COLUMN_ENTITYID,
			SqlLiteEntityHelper.COLUMN_ENTITYTYPE,
			};
	
	private String TAG ="EntityTypeDataSource";

	public EntityTypeDataSource(Context context) {
		dbHelper = new SqlLiteEntityHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public EntityType createEntityType(EntityType entityType) {
		ContentValues values = new ContentValues();
		values.put(SqlLiteEntityHelper.COLUMN_ENTITYID, entityType.getEntityId());
		values.put(SqlLiteEntityHelper.COLUMN_ENTITYTYPE, entityType.getEntityDescription());
		long insertId = database.insert(SqlLiteEntityHelper.TABLE_ENTITYTYPES, null, values);
		// To show how to query
		Cursor cursor = database.query(SqlLiteEntityHelper.TABLE_ENTITYTYPES,
				allColumns, SqlLiteEntityHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		return cursorToEntityType(cursor);
	}

	public void deleteEntityType(EntityType entityType) {
		int id = entityType.getEntityId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(SqlLiteEntityHelper.TABLE_ENTITYTYPES, SqlLiteEntityHelper.COLUMN_ID + " = " + id, null);
	}

	public void deleteAllEntityType() {

		database.delete(SqlLiteEntityHelper.TABLE_ENTITYTYPES, "", null);
	}
	
	public ArrayList<EntityType> getAllEntityTypes() {
		ArrayList<EntityType> entityTypes = new ArrayList<EntityType>();
		Cursor cursor = database.query(SqlLiteEntityHelper.TABLE_ENTITYTYPES,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
		int count =  cursor.getColumnCount();
		Log.e(TAG, "cursor.getColumnCount()" + count);
		for (int i = 0; i < count; i++){
			Log.e(TAG, "cursor.getColumnname(" + i + ")" + cursor.getColumnName(i));
		}
		Log.e(TAG, "getCount()" + cursor.getCount());
		while (!cursor.isAfterLast()) {
			EntityType entityType = cursorToEntityType(cursor);
			entityTypes.add(entityType);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return entityTypes;
	}
	
	private EntityType cursorToEntityType(Cursor cursor) {
		Log.e(TAG, "cursor.getInt(1)" + cursor.getInt(1));
		Log.e(TAG, "cursor.getString(2)" + cursor.getString(2));
		EntityType entityType = new EntityType();
		entityType.setEntityId(cursor.getInt(1));
		entityType.setEntityDescription(cursor.getString(2));

		return entityType;
	}
}
