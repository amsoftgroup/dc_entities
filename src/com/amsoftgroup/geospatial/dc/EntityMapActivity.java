package com.amsoftgroup.geospatial.dc;


import android.app.Activity;
import android.os.Bundle;
import com.google.android.maps.*;

public class EntityMapActivity extends MapActivity {
    private MapView mapView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.maps);
        //mapView = (MapView) this.findViewById(R.id.mapview);
        //mapView.setBuiltInZoomControls(true);

    }


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}