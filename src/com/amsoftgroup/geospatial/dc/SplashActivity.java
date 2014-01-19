package com.amsoftgroup.geospatial.dc;

import java.util.ArrayList;

import com.amsoftgroup.geospatial.dc.business.EntityType;
import com.amsoftgroup.geospatial.helper.AppHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class SplashActivity extends BaseActivity {

	protected boolean _active = true;
	protected int _splashTime = 5000; // time to display the splash screen in ms	
	private Location mLocation;
	private LocationManager mLocationManager;
	private boolean network_enabled = false;
	private volatile double lon = Double.MAX_VALUE;
	private volatile double lat = Double.MAX_VALUE;
	private SharedPreferences prefs = null;
	private boolean LOCATION_DEBUG = false;
	
	private String TAG = "SplashActivity";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
  
    }
    
    @Override public void onResume(){
    	super.onResume();
    	
    	prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	//LOCATION_DEBUG = prefs.getBoolean("LOCATION_DEBUG", false);
    
    	//if (LOCATION_DEBUG){
    	if (true){
            Intent newIntent = new Intent(SplashActivity.this, GeospatialActivity.class);
            newIntent.putExtra("lon", 77.01);	
            newIntent.putExtra("lat", 38.91);	
            startActivity(newIntent);
            finish();
    	}
    	
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		try{
			network_enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			//Log.d(TAG, "NETWORK_PROVIDER enabled");
		}catch(Exception ex){
			Log.e(TAG, "NETWORK_PROVIDER not enabled: " + ex.toString());
		}   

		if (network_enabled){
			mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0, myLocL); 	
		}else{
			
			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			alertbox.setMessage("Cannot determine location: NETWORK_PROVIDER.");
			alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					//Toast.makeText(getApplicationContext(), "OK button clicked", Toast.LENGTH_LONG).show();
				}
			});
			alertbox.show();
			return;
		}
    }
    
	@Override
	public void onPause() {
		super.onPause();
		
		mLocationManager.removeUpdates(myLocL);
	}	
	
	private final LocationListener myLocL = new LocationListener(){

		@Override
		public void onLocationChanged(Location location) {	
			mLocation = location;
			lon = mLocation.getLongitude();
			lat = mLocation.getLatitude();
			String Text = "My current location is Latitud " + lat+ "Longitud = " + lon;

			Toast.makeText( getApplicationContext(),Text,Toast.LENGTH_SHORT).show();
			
            if ((lat != Double.MAX_VALUE ) && (lon != Double.MAX_VALUE)){
                
                Intent newIntent = new Intent(SplashActivity.this, GeospatialActivity.class);
                newIntent.putExtra("lon",lon);	
                newIntent.putExtra("lat",lat);	
                startActivity(newIntent);
                finish();
                //Log.e(TAG, "lat " + lat + " lon " + lon);
                //stop();        	
            }
		}
		
		@Override
		public void onProviderDisabled(String provider) {

		}
		
		@Override
		public void onProviderEnabled(String provider) {}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}	
		
	};
	
    	
  
    
}
