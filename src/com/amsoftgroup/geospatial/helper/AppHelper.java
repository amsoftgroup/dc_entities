package com.amsoftgroup.geospatial.helper;

import java.util.ArrayList;

import android.net.ConnectivityManager;
import com.amsoftgroup.geospatial.dc.business.EntityType;

public class AppHelper {
	
	public static int getSqlLitaDBVersion(){
		return 1;
	}
	
	public static boolean isConnected(ConnectivityManager cm){

		return  (cm.getActiveNetworkInfo() != null &&
				cm.getActiveNetworkInfo().isAvailable() &&
				cm.getActiveNetworkInfo().isConnected());

	}
	
	public static String getGeoServerURL(){
	// append "&typeName=WDC:barsandliquor&maxFeatures=50"
		return "http://tequila.sombrerosoft.com:8000/geoserver/WDC/ows?service=WFS&version=1.0.0&request=GetFeature&outputFormat=json&typeName=WDC:barsandliquor&maxFeatures=50";

	}
	
	private static String getConnectionUrl(){
		//DEVELOPMENT
		//return "http://192.168.1.105:8080/GeospatialJSON/rest/entity/";
		
		//PRODUCTION
		return "http://tequila.sombrerosoft.com/GeospatialJSON/rest/entity/";
	}

	public static String getEntityTypes(){
		return getConnectionUrl() + "get/";
	}	
	
	public static String getEntityByDistance(int entityTypeId, int distance, double lon, double lat){
		return getConnectionUrl() + "getEntityByDistance/" + entityTypeId + "/" + distance + "/" + lon + "/" + lat;
	}	

	public static String getEntityByTypeAndGID(int entityTypeId, int gid){
		return getConnectionUrl() + "getEntity/"+ entityTypeId + "/" + gid ;
	}
	
	public static String getDefaultDistance(){
		return "100";
	}
	
	public static int getDaysBetweenUpdateChecks(){
		return 10;
	}
	public static String getUser(){
		return "geospatial";
	}
	
	public static String getPasswd(){
		return "Cym4ge!!";
	}
	
	public static boolean isGeoServerEnabled(){
		return false;
	}
	public static boolean isGeoServerPWEnabled(){
		return true;
	}
}
