package com.amsoftgroup.geospatial.dc.business;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amsoftgroup.geospatial.dc.JsonParser;
import com.amsoftgroup.geospatial.dc.hibernate.Embassy;
import com.amsoftgroup.geospatial.helper.AppHelper;

import android.util.Log;

public class EntityResolver {

	private String TAG = "EntityResolver";
	private int gid = -1;
	private int entityType = -1;
	private JSONObject json;

	public EntityResolver(int entityTypeId, int gidId){
		this.entityType = entityTypeId;
		this.gid = gidId;

		try{    
			json = JsonParser.connect(AppHelper.getEntityByTypeAndGID(entityType, gid));	
			Log.e(TAG, "***************URL= " + AppHelper.getEntityByTypeAndGID(entityType, gid));  
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
	}

	public LinkedHashMap<String, String> resolve(){

		switch (entityType){
		case(EntityTypes.Anc):{
			return ParseAnc(gid);
		}case(EntityTypes.Barsandliquor):{
			return ParseBarsandliquor(gid);
		}case(EntityTypes.Bicyclelanes):{
			//TODO: this should never be reached.
			// this should be trapped in GeospatialActivity and sent straight to a map!
			break;
		}case(EntityTypes.Embassy):{
			return ParseEmbassy(gid);
		}case(EntityTypes.Gasstations):{
			return ParseGasstations(gid);
		}case(EntityTypes.GroceryStores):{
			return ParseGrocerystores(gid);
		}case(EntityTypes.Hikingtrails):{
			//TODO: this should never be reached.
			// this should be trapped in GeospatialActivity and sent straight to a map!
			break;
		}case(EntityTypes.Historicdistrics):{
			//TODO: this should never be reached.
			// this should be trapped in GeospatialActivity and sent straight to a map!
			break;
		}case(EntityTypes.Historicstructures):{
			return ParseHistoricstructures(gid);
		}case(EntityTypes.Hospital):{
			return ParseHospitals(gid);
		}case(EntityTypes.Hotels):{
			return ParseHotels(gid);
		}case(EntityTypes.Libraries):{
			return ParseLibraries(gid);
		}case(EntityTypes.Policestations):{
			return ParsePolicestations(gid);
		}case(EntityTypes.Postoffice):{
			return ParsePostoffice(gid);
		}
		}
		return null;
	}

	private LinkedHashMap<String, String> ParseAnc(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    
			if (!json.isNull("anc")){
				Log.e(TAG, "ParseAnc(" + gid +") failed to return results.");  			
			}else if (json.get("anc").getClass().equals(JSONObject.class)){
				JSONObject jsonLineItem =json.getJSONObject("anc");
				if (!jsonLineItem.isNull("description")){
					hm.put("Title:",jsonLineItem.getString("description"));
				}
				if (!jsonLineItem.isNull("ancId")){
					hm.put("ANC ID:",jsonLineItem.getString("ancId"));
				}
				if (!jsonLineItem.isNull("webUrl")){
					hm.put("Website:",jsonLineItem.getString("webUrl"));
				}
			}		
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}

	private LinkedHashMap<String, String> ParseBarsandliquor(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    

			if (json.isNull("barsandliquor")){
				Log.e(TAG, "ParseBarsandliquor(" + gid +") failed to return results.");  			
			}else if (json.get("barsandliquor").getClass().equals(JSONObject.class)){
				JSONObject jsonLineItem =json.getJSONObject("barsandliquor");
				if (!jsonLineItem.isNull("tradeName")){
					hm.put("Title: ",jsonLineItem.getString("tradeName"));
				}
				if (!jsonLineItem.isNull("address")){
					hm.put("Address: ",jsonLineItem.getString("address"));
				}
				if (!jsonLineItem.isNull("descriptio")){
					hm.put("Description: ",jsonLineItem.getString("descriptio"));
				}
				if (!jsonLineItem.isNull("license")){
					hm.put("License: ",jsonLineItem.getString("license"));
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}

	private LinkedHashMap<String, String> ParseEmbassy(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    
			if (json.isNull("embassy")){
				Log.e(TAG, "ParseEmbassy(" + gid +") failed to return results.");  			
			}else if (json.get("embassy").getClass().equals(JSONObject.class)){
				JSONObject jsonLineItem =json.getJSONObject("embassy");
				if (!jsonLineItem.isNull("description")){
					hm.put("Title: ",jsonLineItem.getString("description"));
				}
				if (!jsonLineItem.isNull("address")){
					hm.put("Address: ",jsonLineItem.getString("address"));
				}
				if (!jsonLineItem.isNull("telephone")){
					hm.put("Phone: ",jsonLineItem.getString("telephone"));
				}
				if (!jsonLineItem.isNull("weburl")){
					hm.put("Website: ",jsonLineItem.getString("weburl"));
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}

	private LinkedHashMap<String, String> ParseGasstations(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    

			if (json.isNull("gasstations")){
				
				Log.e(TAG, "ParseGasstations(" + gid +") failed to return results.");  	
				
			}else if (json.get("gasstations").getClass().equals(JSONObject.class)){
		
				JSONObject jsonLineItem =json.getJSONObject("gasstations");
				
				Log.e(TAG, "jsonLineItem.isNull(): " + jsonLineItem.isNull("name"));  			
				
				if (!jsonLineItem.isNull("name")){
					hm.put("Title: ",jsonLineItem.getString("name"));
				}
				if (!jsonLineItem.isNull("address")){
					hm.put("Address: ",jsonLineItem.getString("address"));
				}
				if (!jsonLineItem.isNull("phone")){
					hm.put("Phone: ",jsonLineItem.getString("phone"));
				}
				if (!jsonLineItem.isNull("fullServi")){
					int fs = jsonLineItem.getInt("fullServi");
					String yesno = "NO";
					if (fs==1){
						yesno = "YES";
					}
					hm.put("Full Service: ", yesno);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}

	private LinkedHashMap<String, String> ParseGrocerystores(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    
			if (json.isNull("grocerystores")){
				Log.e(TAG, "ParseGrocerystores(" + gid +") failed to return results.");  			
			}else if (json.get("grocerystores").getClass().equals(JSONObject.class)){
				JSONObject jsonLineItem =json.getJSONObject("grocerystores");
				if (!jsonLineItem.isNull("storename")){
					hm.put("Title: ",jsonLineItem.getString("storename"));
				}
				if (!jsonLineItem.isNull("address")){
					hm.put("Address: ",jsonLineItem.getString("address"));
				}
				if (!jsonLineItem.isNull("phone")){
					hm.put("Phone: ",jsonLineItem.getString("phone"));
				}
				if (!jsonLineItem.isNull("ward")){
					hm.put("Ward: ",jsonLineItem.getString("ward"));
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}

	private LinkedHashMap<String, String> ParseHistoricstructures(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    

			if (json.isNull("historicstructures")){
				Log.e(TAG, "ParseHistoricstructures(" + gid +") failed to return results.");  			
			}else if (json.get("historicstructures").getClass().equals(JSONObject.class)){
				JSONObject jsonLineItem =json.getJSONObject("historicstructures");
				if (!jsonLineItem.isNull("label")){
					hm.put("Title: ",jsonLineItem.getString("label"));
				}
				if (!jsonLineItem.isNull("address")){
					hm.put("Address: ",jsonLineItem.getString("address"));
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}
	
	private LinkedHashMap<String, String> ParseHospitals(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    

			if (json.isNull("hospitals")){
				Log.e(TAG, "ParseHospitals(" + gid +") failed to return results.");  
				hm.put("Could not retrieve results","unexpected value returned.");
			}else if (json.get("hospitals").getClass().equals(JSONObject.class)){
				JSONObject jsonLineItem =json.getJSONObject("hospitals");
				hm.put("DATA MAY BE INCORRECT ","PLEASE EXIT THIS APP AND CALL 911 FOR EMERGENCIES!");
				hm.put("","");
				if (!jsonLineItem.isNull("name")){
					hm.put("Title: ",jsonLineItem.getString("name"));
				}
				if (!jsonLineItem.isNull("address")){
					hm.put("Address: ",jsonLineItem.getString("address"));
				}
				if (!jsonLineItem.isNull("webUrl")){
					hm.put("Website: ",jsonLineItem.getString("webUrl"));
				}
				if (!jsonLineItem.isNull("type")){
					hm.put("Type: ",jsonLineItem.getString("type"));
				}
			}else{
				Log.e(TAG, "ParseHospitals(" + gid +") returned unexpected results.");
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}
	
	private LinkedHashMap<String, String> ParseHotels(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    

			if (json.isNull("hotels")){
				Log.e(TAG, "ParseHotels(" + gid +") failed to return results.");  
				hm.put("Could not retrieve results:"," unexpected value returned.");
			}else if (json.get("hotels").getClass().equals(JSONObject.class)){
				JSONObject jsonLineItem =json.getJSONObject("hotels");
				if (!jsonLineItem.isNull("name")){
					hm.put("Title: ",jsonLineItem.getString("name"));
				}
				if (!jsonLineItem.isNull("numrooms")){
					hm.put("Number of Rooms: ",jsonLineItem.getString("numrooms"));
				}
				if (!jsonLineItem.isNull("phone")){
					hm.put("Phone: ",jsonLineItem.getString("phone"));
				}
				if (!jsonLineItem.isNull("address")){
					hm.put("Address: ",jsonLineItem.getString("address"));
				}
				if (!jsonLineItem.isNull("zip")){
					hm.put("Zip Code: ",jsonLineItem.getString("zip"));
				}
				if (!jsonLineItem.isNull("webUrl")){
					hm.put("Website: ",jsonLineItem.getString("webUrl"));
				}	
			}else{
				Log.e(TAG, "ParseHotels(" + gid +") returned unexpected results.");
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}	
	
	private LinkedHashMap<String, String> ParseLibraries(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    

			if (json.isNull("libraries")){
				Log.e(TAG, "ParseLibraries(" + gid +") failed to return results.");  
				hm.put("Could not retrieve results:"," unexpected value returned.");
			}else if (json.get("libraries").getClass().equals(JSONObject.class)){
				JSONObject jsonLineItem =json.getJSONObject("libraries");
				if (!jsonLineItem.isNull("name")){
					hm.put("Title: ",jsonLineItem.getString("name"));
				}
				if (!jsonLineItem.isNull("phone")){
					hm.put("Phone: ",jsonLineItem.getString("phone"));
				}
				if (!jsonLineItem.isNull("address")){
					hm.put("Address: ",jsonLineItem.getString("address"));
				}
				if (!jsonLineItem.isNull("webUrl")){
					hm.put("Website: ",jsonLineItem.getString("webUrl"));
				}
			}else{
				Log.e(TAG, "ParseLibraries(" + gid +") returned unexpected results.");
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}	
	
	private LinkedHashMap<String, String> ParsePolicestations(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    

			if (json.isNull("policestations")){
				Log.e(TAG, "ParsePolicestations(" + gid +") failed to return results.");  
				hm.put("Could not retrieve results:"," unexpected value returned.");
			}else if (json.get("policestations").getClass().equals(JSONObject.class)){
				JSONObject jsonLineItem =json.getJSONObject("policestations");
				if (!jsonLineItem.isNull("name")){
					hm.put("Title: ",jsonLineItem.getString("name"));
				}
				if (!jsonLineItem.isNull("phone")){
					hm.put("Phone: ",jsonLineItem.getString("phone"));
				}
				if (!jsonLineItem.isNull("address")){
					hm.put("Address: ",jsonLineItem.getString("address"));
				}
				if (!jsonLineItem.isNull("webUrl")){
					hm.put("Website: ",jsonLineItem.getString("webUrl"));
				}
			}else{
				Log.e(TAG, "ParsePolicestations(" + gid +") returned unexpected results.");
				hm.put("Could not retrieve results:"," unexpected value returned.");
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}	
	
	private LinkedHashMap<String, String> ParsePostoffice(int gid){

		LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();

		try{    

			if (json.isNull("postoffice")){
				Log.e(TAG, "ParsePostoffice(" + gid +") failed to return results.");  
				hm.put("Could not retrieve results:"," unexpected value returned.");
			}else if (json.get("postoffice").getClass().equals(JSONObject.class)){
				JSONObject jsonLineItem =json.getJSONObject("postoffice");
				if (!jsonLineItem.isNull("name")){
					hm.put("Title: ",jsonLineItem.getString("name"));
				}
				if (!jsonLineItem.isNull("phone")){
					hm.put("Phone: ",jsonLineItem.getString("phone"));
				}
				if (!jsonLineItem.isNull("address")){
					hm.put("Address: ",jsonLineItem.getString("address"));
				}
				if (!jsonLineItem.isNull("webUrl")){
					hm.put("Website: ",jsonLineItem.getString("webUrl"));
				}
			}else{
				Log.e(TAG, "ParsePostoffice(" + gid +") returned unexpected results.");
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());		
		}
		return hm;	
	}		
}
