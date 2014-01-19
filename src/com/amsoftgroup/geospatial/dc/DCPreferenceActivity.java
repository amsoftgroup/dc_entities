package com.amsoftgroup.geospatial.dc;


import java.util.ArrayList;

import com.amsoftgroup.geospatial.helper.AppHelper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.ListView;
import android.widget.Toast;
 
public class DCPreferenceActivity extends PreferenceActivity {

	private int distance;
	private String system_of_measurement;
	private boolean location_debug; 
	


	private String TAG = "DCPreferenceActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);

	}

	@Override
	protected void onStart() {	
		super.onStart();
		getPrefs();
	}
	
    private void getPrefs() {
        // Get the xml/preferences.xml preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    
        distance = Integer.parseInt(prefs.getString("DISTANCE", AppHelper.getDefaultDistance()));
    	system_of_measurement = prefs.getString("MEASUREMENT_TYPE", "METRIC");
    	location_debug = prefs.getBoolean("LOCATION_DEBUG", false);
    }
    
}