package com.amsoftgroup.geospatial.dc;


import java.util.ArrayList;
import java.util.Date;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.amsoftgroup.geospatial.sqllite.DateDataSource;
import com.amsoftgroup.geospatial.sqllite.EntityTypeDataSource;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeospatialActivity extends BaseActivity{
	
	private ListView lv;
	
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<EntityType> m_orders = null;
    private OrderAdapter m_adapter;
    private SharedPreferences prefs = null;
    private String DISTANCE = "-1";
    private String MEASUREMENT = "METERS";
    private double lon;
    private double lat;
    private static int testcode = 0;
    private String TAG = "GeospatialActivity";
    
	private Handler mHandler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	    	super.handleMessage(msg);
	    }
	};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		//Eula.show(this);
        setContentView(R.layout.main);
       
		try {
			Bundle bundle = this.getIntent().getExtras();
				
			if (bundle.containsKey("lon")){
				lon = bundle.getDouble("lon");
			}
			if (bundle.containsKey("lat")) {
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
    	
    	m_ProgressDialog = new ProgressDialog(this);
		m_ProgressDialog.setTitle("Please wait...");
		m_ProgressDialog.setMessage("Retrieving data ...");
		m_ProgressDialog.setCancelable(true);
    }
    
    @Override public void onResume(){
    	super.onResume();

    	prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	DISTANCE = prefs.getString("DISTANCE", "" + AppHelper.getDefaultDistance());
    	MEASUREMENT = prefs.getString("MEASUREMENT", "METERS");
    	
    	m_orders  = new ArrayList<EntityType>();
    	m_adapter = new OrderAdapter(this, R.layout.row, m_orders);
    	lv = (ListView)this.findViewById(android.R.id.list);  
    	lv.setAdapter(this.m_adapter);       
    	lv.setOnItemClickListener(new OnItemClickListener() {

    		@Override
    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    				long arg3) {
    			
    			Intent newIntent = new Intent(GeospatialActivity.this, ExpandableEntityListActivity.class);
    			EntityType l = (EntityType) lv.getItemAtPosition(arg2);	
    			newIntent.putExtra("entityTypeId", "" + l.getEntityId());	
    			newIntent.putExtra("lon", lon);
    			newIntent.putExtra("lat", lat);
    			m_orders = null;
    			startActivity(newIntent);
    			
    			/*
    			Intent newIntent = new Intent(GeospatialActivity.this, MapActivity.class);
    			EntityType l = (EntityType) lv.getItemAtPosition(arg2);	
    			newIntent.putExtra("entityTypeId", "" + l.getEntityId());	
    			newIntent.putExtra("lon", lon);
    			newIntent.putExtra("lat", lat);
    			m_orders = null;
    			startActivity(newIntent);
    			*/
    		}
   	});

    	Thread t = new Thread(mUpdateTimeTask);
    	t.start();		
    }

    @SuppressWarnings("unused")
    private void getOrders(){

    	// TODO:  IF SQLLite "Date Updated" DB table exists, and
    	// IF the date is recent enough (one week?) pull the EntityTypes from the database
    	// Otherwise, check the webserver, update the "Date Updated" table and, if needed, 
    	// update the EntityType table

    	DateDataSource dds = new DateDataSource(getApplicationContext());
    	dds.open();
    	EntityTypeDataSource ets = new EntityTypeDataSource(getApplicationContext());
    	ets.open();

    	Date then = dds.getAllDate();

    	// Approx every 10 days, check for new EntityType updates from the server...
    	if ((then == null) || (((new Date().getTime()-then.getTime())/(24 * 60 * 60 * 1000)) > AppHelper.getDaysBetweenUpdateChecks())){

    		try{    

    			ets.deleteAllEntityType();

    			JSONObject json = JsonParser.connect(AppHelper.getEntityTypes());	
    			JSONArray lineItems = json.getJSONArray("entityType");			

    			for (int i = 0; i <lineItems.length(); i++) {    				
    				EntityType e = new EntityType();
    				JSONObject jsonLineItem = (JSONObject) lineItems.get(i);
    				String key = jsonLineItem.getString("entityId");
    				String value = jsonLineItem.getString("entityDescription");
    				e.setEntityId(Integer.parseInt(key));
    				e.setEntityDescription(value);
    				m_orders.add(ets.createEntityType(e));
    				//Log.e(TAG, "retrieving entities from webserver");
    			}
    		} catch (Exception e) {
    			Log.e(TAG, e.getMessage());
    		}

    		//UPDATE TIME
    		dds.deleteDate();
    		dds.createDate();

    	}else{

    		ArrayList<EntityType> arl = ets.getAllEntityTypes();
    		//Log.e(TAG, "arl " + arl.size());
    		if (arl != null){
    			for (int i = 0; i < arl.size(); i++){
    				m_orders.add(arl.get(i));	
    				//Log.e(TAG, "retrieving entities from DATABASE!");
    			}	
    		}
    	}

    	dds.close();
    	ets.close();
    
    }

    private class OrderAdapter extends ArrayAdapter<EntityType> {

    	private ArrayList<EntityType> items;

    	public OrderAdapter(Context context, int textViewResourceId, ArrayList<EntityType> items) {
    		super(context, textViewResourceId, items);
    		this.items = items;
    	}
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
        	View v = convertView;
            if (v == null) {
            	LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row, null);
            }
            EntityType o = items.get(position);
            if (o != null) {
            	TextView tt = (TextView) v.findViewById(R.id.toptext);
                if (tt != null) {
                	tt.setText("  " + o.getEntityDescription()); 
                }
            }
            return v;
        }
    }
    
	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(mUpdateTimeTask);
	}
	
}