package com.amsoftgroup.geospatial.dc;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.amsoftgroup.geospatial.dc.business.*;
import com.amsoftgroup.geospatial.dc.eula.Eula;
import com.amsoftgroup.geospatial.helper.AppHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubEntityListActivity extends Activity{
	
	private ListView lv;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Entity> m_orders = null;
    private OrderAdapter m_adapter;
    private TextView incidents;
    private int entityTypeId;
    private volatile int extendedDistance = -1;
    private String DISTANCE = "-1";
    private String MEASUREMENT = "METERS";
    private String TAG = "GeospatialActivity";
	private Location mLocation;
	private LocationManager mLocationManager;
	private double lon = Double.MAX_VALUE;
	private double lat = Double.MAX_VALUE;
	private boolean network_enabled = false;	
    private boolean firstRun = true;
    
    
	private Handler mHandler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	    	super.handleMessage(msg);
	    }
	};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		DISTANCE = prefs.getString("DISTANCE", ""+ AppHelper.getDefaultDistance()); 
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
    
	private Runnable mUpdateTimeTask = new Runnable() {

		public void run() {	
			getOrders();
			mHandler.post(mUpdateResults);
		}
	};

    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
        }
    };
    
    private void updateResultsInUi() {
    	
    	if (m_ProgressDialog != null){
    		m_ProgressDialog.dismiss();
    	}
    	m_adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
    	super.onStart();	
    }

    @Override public void onResume(){
    	super.onResume();

    	m_ProgressDialog = new ProgressDialog(this);
    	m_ProgressDialog.setTitle("Please wait...");
    	m_ProgressDialog.setMessage("Retrieving data ...");
    	m_ProgressDialog.setCancelable(true);
    	m_ProgressDialog.show();

    	incidents = (TextView)findViewById(R.id.hello);
    	
    	m_orders  = new ArrayList<Entity>();
    	m_adapter = new OrderAdapter(this, R.layout.row, m_orders);
    	lv = (ListView)this.findViewById(android.R.id.list);  
    	lv.setAdapter(this.m_adapter);       
    	lv.setOnItemClickListener(new OnItemClickListener() {

    		@Override
    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    				long arg3) {
/*
    			switch (entityTypeId){
	    			case EntityTypes.Embassy:{
	    				Intent newIntent = new Intent(SubEntityListActivity.this, EmbassyActivity.class);
	    				EntityType l = (EntityType) lv.getItemAtPosition(arg2);	
	    				newIntent.putExtra("gid", "" + l.getEntityId());	
	    				newIntent.putExtra("entityTypeId", "" + entityTypeId);	
	    				m_orders = null;
	    				startActivity(newIntent);
	    				break;
	    			}
	    			
	    			case EntityTypes.Hospital:{
	    				Intent newIntent = new Intent(SubEntityListActivity.this, HospitalActivity.class);
	    				EntityType l = (EntityType) lv.getItemAtPosition(arg2);	
	    				newIntent.putExtra("gid", "" + l.getEntityId());	
	    				newIntent.putExtra("entityTypeId", "" + entityTypeId);	
	    				m_orders = null;
	    				startActivity(newIntent);
	    				break;
	    			}
    			}
*/
    		}
    	});

    	Thread t = new Thread(mUpdateTimeTask);
    	t.start();		
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
    
    private String getURL(int distance){
    	
    	String url = AppHelper.getEntityByDistance(entityTypeId, Integer.parseInt(DISTANCE), lon, lat);
    	
		return url;
    	
    }

    private void getOrders(){

   		String url = getURL(extendedDistance); 
		Log.e(TAG, "************" + url);  
		JSONObject json = new JSONObject();
		
    	try{    

    		json = getJSONObject(url);
  	
    		if ((json == null) || (json.isNull("entity"))){

    			String value = "NO RESULTS WITHIN " + DISTANCE + " m";
    			incidents.setText(value);
    			
    			// recursive search again, just get closest ?
    			//implement nearest neighbor search algo, and...
    			// MOVE THIS FUNCTIONALITY TO THE DATABASE to prevent unnessary http roundtrips
    			
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
        			m_orders.add(e);
        			
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
    			m_orders.add(e);
    			Log.e(TAG, "**************OBJECT");  
    		}
    		
    	} catch (Exception e) {
    		Log.e(TAG, e.getMessage());		
    	}
    }
    
    private class OrderAdapter extends ArrayAdapter<Entity> {

        private ArrayList<Entity> items;

        public OrderAdapter(Context context, int textViewResourceId, ArrayList<Entity> items) {
        	super(context, textViewResourceId, items);
            this.items = items;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	View v = convertView;
            if (v == null) {
            	LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.two_row, null);
            }
            Entity o = (Entity)items.get(position);
            if (o != null) {
            	TextView tt = (TextView) v.findViewById(R.id.toptext);
            	TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                if (tt != null) {
                	String desc = o.getDescription();

                	tt.setText("  " + desc); 
                	bt.setText("  " + (int) o.getDistance() + " m");
                }
            }
            return v;
        }
    }
    
	@Override
	public void onPause() {
		super.onPause();
	}	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

    @Override    
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.app_menu, menu);
    	return true;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {

		case R.string.about:
		{
			String app_ver = "";

			try{
				app_ver = " v" + this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
			}
			catch (NameNotFoundException e){
				Log.v(TAG, e.getMessage());
			}

			String about = "wdc locator app" + app_ver + "\nby brian reed\nsupport@amsoftgroup.com";

			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			alertbox.setMessage(about);
			alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					//    Toast.makeText(getApplicationContext(), "OK button clicked", Toast.LENGTH_LONG).show();
				}
			});
			alertbox.show();
		}
		return true;
		case R.string.settings:
		{
			Intent settingsActivity = new Intent(getBaseContext(), DCPreferenceActivity.class);
			startActivity(settingsActivity);
		}
		return true;
		case R.string.status:
		{
			Intent settingsActivity = new Intent(getBaseContext(), WebStatusActivity.class);
			startActivity(settingsActivity);
		}
		return true;    

		default:
			return super.onOptionsItemSelected(item);
		}
	} 	
}