package com.amsoftgroup.geospatial.dc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends Activity{

	private String TAG = "BaseActivity";
	
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
