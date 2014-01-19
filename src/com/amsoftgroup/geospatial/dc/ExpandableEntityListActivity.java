package com.amsoftgroup.geospatial.dc;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amsoftgroup.geospatial.dc.business.Entity;
import com.amsoftgroup.geospatial.dc.business.EntityResolver;
import com.amsoftgroup.geospatial.dc.business.EntityType;
import com.amsoftgroup.geospatial.dc.business.EntityTypes;
import com.amsoftgroup.geospatial.dc.business.MegaEntity;
import com.amsoftgroup.geospatial.dc.hibernate.*;
import com.amsoftgroup.geospatial.helper.AppHelper;
import com.amsoftgroup.geospatial.helper.ExpandableEntityListActivityData;

import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

public class ExpandableEntityListActivity extends ExpandableListActivity {

	private ExpandableListAdapter mAdapter;
	private ExpandableListView lv;
	private ProgressDialog m_ProgressDialog = null;
	private ArrayList<Entity> m_parentList = new ArrayList<Entity>();
	private LinkedHashMap m_child = null;
	private Entity m_entity = null;
	private TextView incidents;
	private volatile int extendedDistance = -1;
	private String DISTANCE = "-1";
	private String MEASUREMENT = "METERS";
	private String TAG = "ExpandableEntityListActivity";
	private Location mLocation;
	private LocationManager mLocationManager;
	private double lon = Double.MAX_VALUE;
	private double lat = Double.MAX_VALUE;
	private boolean network_enabled = false;	

	private int gid = -1;
	private int entityTypeId = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAdapter = new MyExpandableListAdapter();
		setListAdapter(mAdapter);
		
		registerForContextMenu(getExpandableListView());
		getExpandableListView().setOnChildClickListener(this);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		DISTANCE = prefs.getString("DISTANCE", "" + AppHelper.getDefaultDistance()); 
		extendedDistance = Integer.parseInt(DISTANCE);

		try {
			Bundle bundle = this.getIntent().getExtras();
			if ((bundle.containsKey("entityTypeId")) && (bundle.getString("entityTypeId") != null)){
				entityTypeId = Integer.parseInt(bundle.getString("entityTypeId"));
			}				
			if (bundle.containsKey("lon")){
				lon = bundle.getDouble("lon");
			}
			if (bundle.containsKey("lat")){
				lat = bundle.getDouble("lat");
			}		
		} catch (Exception e) {
			Log.e(TAG, "onCreate: " + e.toString());
		} 
	}

	@Override 
	public void onResume(){
		super.onResume();

		m_ProgressDialog = new ProgressDialog(this);
		m_ProgressDialog.setTitle("Please wait...");
		m_ProgressDialog.setMessage("Retrieving data ...");
		m_ProgressDialog.setCancelable(true);
		m_ProgressDialog.show();

		ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

		if (!AppHelper.isConnected(connec)){
			
			if (m_ProgressDialog != null){
				m_ProgressDialog.dismiss();
			}
			
			this.setContentView(R.layout.error);
			TextView destination = (TextView) findViewById(R.id.error);
			destination.setText("No network connection!\nPlease try again later.");
        
		}else{	
			if (AppHelper.isGeoServerEnabled()){
				Log.e(TAG, "CALLING TASK " + AppHelper.getGeoServerURL());
				GetParentDetailsTaskGeoServer task = new GetParentDetailsTaskGeoServer();
				task.execute(AppHelper.getGeoServerURL());

			}else{
				Log.e(TAG, "CALLING TASK " + AppHelper.getEntityByDistance(entityTypeId, Integer.parseInt(DISTANCE), lon, lat));
				GetParentDetailsTask task = new GetParentDetailsTask();
				task.execute(AppHelper.getEntityByDistance(entityTypeId, Integer.parseInt(DISTANCE), lon, lat));
			}
		}
	}	
	
	private JSONObject getJSONObject(String url){

		JSONObject json = new JSONObject();

		try{    
			json = JsonParser.connect(url);		
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return json;
	}
	
	private JSONObject getJSONObject(String url, String user, String pass){

		JSONObject json = new JSONObject();

		try{    
			json = JsonParser.connect(url, user, pass);		
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return json;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
			int childPosition, long id) {

		if (m_ProgressDialog != null){
			m_ProgressDialog.dismiss();
		}
		
		Intent newIntent = new Intent(ExpandableEntityListActivity.this, EntityMapActivity.class);
		newIntent.putExtra("lon", lon);
		newIntent.putExtra("lat", lat);

		//newIntent.putExtra("entitylon", lon);
		//newIntent.putExtra("entitylat", lat);
		
		//startActivity(newIntent);
		
		return true;
	}

	public class MyExpandableListAdapter extends BaseExpandableListAdapter {

		
		public Object getChild(int groupPosition, int childPosition) {
			return m_parentList.get(groupPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return 1;
		}

		public TextView getGenericView() {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			TextView textView = new TextView(ExpandableEntityListActivity.this);
			textView.setLayoutParams(lp);
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			textView.setPadding(70, 10, 10, 10);
			return textView;
		}
		
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
				View convertView, ViewGroup parent) {
			
			Log.e(TAG, "CALLING TASK " + AppHelper.getEntityByTypeAndGID(groupPosition, childPosition));
			//m_child = null; wait. what?
			GetChildDetailsTask task = new GetChildDetailsTask();
			
			m_entity = (Entity) getChild(groupPosition, childPosition);
			gid = m_entity.getGid(); 
			entityTypeId = m_entity.getEntityTypeId();

			// the asyncTask will invalidate and refresh views when it completes executing.
			task.execute(AppHelper.getEntityByTypeAndGID(entityTypeId, gid));

			TextView textView = getGenericView();
			if ((m_child!=null) && (m_child.size() > 0)){
		    	Iterator it = m_child.entrySet().iterator();
		    	String s = "";
		    	while (it.hasNext()) {
		    		Map.Entry pairs = (Map.Entry)it.next();
		    		s += pairs.getKey() + "" + pairs.getValue() + "\n";
		    		it.remove(); // avoids a ConcurrentModificationException
		    	}
				textView.setText(s);
			}else{
				m_ProgressDialog.setTitle("Please wait...");
				m_ProgressDialog.setMessage("Retrieving data ...");
				m_ProgressDialog.setCancelable(true);
				m_ProgressDialog.show();
			}
			return textView;	
		}

		public Object getGroup(int groupPosition) {
			return m_parentList.get(groupPosition);
		}

		public int getGroupCount() {
			return m_parentList.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
				ViewGroup parent) {
			
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.two_row, null);
			}
			Entity et = (Entity) getGroup(groupPosition);
			if (et != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				if (tt != null) {
					tt.setText("  " + et.getDescription() ); 
				}
				if (bt != null) {
					bt.setText("  " + (int)(et.getDistance()) + " m"); 
				}
			}
			return v;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public boolean hasStableIds() {
			return true;
		}
	}
	
	 private class GetParentDetailsTask extends AsyncTask<String, Integer, Integer > {

		 protected Integer doInBackground(String... urls) {

			 JSONObject json = new JSONObject();
			
			 try{    
				 json = getJSONObject(urls[0]);

				 if ((json == null) || (json.isNull("entity"))){

					 Entity e = new Entity();
			
					 e.setGid(-1);
					 e.setDescription("No Entities in range " + DISTANCE + " meters" );
					 e.setDistance(0);
					 e.setEntityTypeId(0);
					 m_parentList.add(e);
					 Log.e(TAG, "***************NULL");  

				 }else if (json.get("entity").getClass().equals(JSONArray.class)){
					 JSONArray lineItems = json.getJSONArray("entity");

					 for (int i = 0; i <lineItems.length(); i++) {
						 Entity e = new Entity();
						 JSONObject jsonLineItem = (JSONObject) lineItems.get(i);

						 String gid = jsonLineItem.getString("gid");
						 String value = jsonLineItem.getString("description");
						 double dist_meters = jsonLineItem.getDouble("distance");
						 e.setGid(Integer.parseInt(gid));
						 e.setDescription(value);
						 e.setDistance(dist_meters);
						 e.setEntityTypeId(entityTypeId);
						 m_parentList.add(e);
					 }

					 Log.e(TAG, "**************ARRAY");  

				 }else if (json.get("entity").getClass().equals(JSONObject.class)){
					 Entity e = new Entity();
					 JSONObject lineItem = json.getJSONObject("entity");
					 String value = lineItem.getString("description");
					 double dist_meters = lineItem.getDouble("distance");
					 String gid = lineItem.getString("gid");
					 e.setGid(Integer.parseInt(gid));
					 e.setDescription(value);
					 e.setDistance(dist_meters);
					 e.setEntityTypeId(entityTypeId);
					 m_parentList.add(e);
					 Log.e(TAG, "**************OBJECT");  
				 }

			 } catch (Exception e) {
				 Log.e(TAG, e.getMessage());		
			 }
			return entityTypeId;
		 }
		 
		 @Override
		 protected void onProgressUpdate(Integer... progress) {
			 Log.e(TAG, "****onProgressUpdate");
		 }

		 @Override
		 protected void onPostExecute(Integer result) {
			 
			 getExpandableListView().invalidateViews();
			 
			 Log.e(TAG, "****onPostExecute");

			 if (m_ProgressDialog != null){
				 m_ProgressDialog.dismiss();
			 }
		 }
	 }
	 
	 private class GetParentDetailsTaskGeoServer extends AsyncTask<String, Integer, Integer > {

		 protected Integer doInBackground(String... urls) {

			 JSONObject json = new JSONObject();
			
			 try{   
				 
				 //TODO: better handling switching between GeoServer and alternate homegrown webapp
				 
				 if (AppHelper.isGeoServerPWEnabled()){
					 json = getJSONObject(urls[0], AppHelper.getUser(), AppHelper.getPasswd());
				 }else{
					 json = getJSONObject(urls[0]);
				 }
				 
				 if ((json == null) || (json.isNull("properties"))){

					 Entity e = new Entity();
			
					 e.setGid(-1);
					 e.setDescription("No Entities in range " + DISTANCE + " meters" );
					 e.setDistance(0);
					 e.setEntityTypeId(0);
					 m_parentList.add(e);
					 Log.e(TAG, "***************NULL");  

				 }else if (json.get("properties").getClass().equals(JSONArray.class)){
					 JSONArray lineItems = json.getJSONArray("properties");

					 for (int i = 0; i <lineItems.length(); i++) {
						 Entity e = new Entity();
						 JSONObject jsonLineItem = (JSONObject) lineItems.get(i);

						 String gid = jsonLineItem.getString("gid");
						 String value = jsonLineItem.getString("description");
						 double dist_meters = jsonLineItem.getDouble("distance");
						 e.setGid(Integer.parseInt(gid));
						 e.setDescription(value);
						 e.setDistance(dist_meters);
						 e.setEntityTypeId(entityTypeId);
						 m_parentList.add(e);
					 }

					 Log.e(TAG, "**************ARRAY");  

				 }else if (json.get("properties").getClass().equals(JSONObject.class)){
					 Entity e = new Entity();
					 JSONObject lineItem = json.getJSONObject("properties");
					 String value = lineItem.getString("description");
					 double dist_meters = lineItem.getDouble("distance");
					 String gid = lineItem.getString("gid");
					 e.setGid(Integer.parseInt(gid));
					 e.setDescription(value);
					 e.setDistance(dist_meters);
					 e.setEntityTypeId(entityTypeId);
					 m_parentList.add(e);
					 Log.e(TAG, "**************OBJECT");  
				 }

			 } catch (Exception e) {
				 Log.e(TAG, e.getMessage());		
			 }
			return entityTypeId;
		 }	 
	
		 @Override
		 protected void onProgressUpdate(Integer... progress) {
			 Log.e(TAG, "****onProgressUpdate");
		 }

		 @Override
		 protected void onPostExecute(Integer result) {
			 
			 getExpandableListView().invalidateViews();
			 
			 Log.e(TAG, "****onPostExecute");

			 if (m_ProgressDialog != null){
				 m_ProgressDialog.dismiss();
			 }
		 }
	 }
	 
	 private class GetChildDetailsTask extends AsyncTask<String, Integer, Integer > {

		 protected Integer doInBackground(String... urls) {
			 m_child = new EntityResolver(entityTypeId, gid).resolve();
			return m_child.size();
		 }

		 @Override
		 protected void onProgressUpdate(Integer... progress) {
			 Log.e(TAG, "****onProgressUpdate");
		 }

		 @Override
		 protected void onPostExecute(Integer result) {

			 getExpandableListView().invalidate();
			 
			 Log.e(TAG, "****onPostExecute");

			 if (m_ProgressDialog != null){
				 m_ProgressDialog.dismiss();
			 }

		 }
	 } 

}



