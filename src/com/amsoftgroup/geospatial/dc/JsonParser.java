package com.amsoftgroup.geospatial.dc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amsoftgroup.geospatial.helper.Base64;
 
import android.util.Log;
 
public class JsonParser {
	
	private static String TAG = "JsonParser";
 
    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        	Log.e(TAG, e.toString());
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            	Log.e(TAG, e.toString());
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    public static JSONObject connect(String url)
    {
    	
    	JSONObject json = null;
 
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url); 
        HttpResponse response;
        
        try {
            response = httpclient.execute(httpget);
            Log.i(TAG,response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
 
            if (entity != null) {

                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                Log.i(TAG,result);
 
               // if ((result == null) || (result.length() <= 0) || (result.equalsIgnoreCase("null"))){
                    json = new JSONObject(result);
                    Log.i(TAG,"<jsonobject>\n"+json.toString()+"\n</jsonobject>");
               // }

                instream.close();
            }
 
 
        } catch (ClientProtocolException e) {
        	Log.e(TAG, e.toString());
        } catch (IOException e) {
        	Log.e(TAG, e.toString());
        } catch (JSONException e) {
        	Log.e(TAG, e.toString());
        }
		return json;
    }
    

    public static JSONObject connect(String url, String user, String pass)
    {

    	JSONObject json = null;
 
        HttpClient httpclient = new DefaultHttpClient(); 
        HttpGet httpget = new HttpGet(url); 
        httpget.addHeader(BasicScheme.authenticate(
        		 new UsernamePasswordCredentials(user, pass),
        		 "UTF-8", false));

        HttpResponse response;

        try {  	
        	
            response = httpclient.execute(httpget);
            Log.i(TAG,response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();

            if (entity != null) {

            	InputStream instream = entity.getContent();
            	String result= convertStreamToString(instream);
            	Log.i(TAG,result);
            	json = new JSONObject(result);
            	Log.i(TAG,"<jsonobject>\n"+json.toString()+"\n</jsonobject>");
            	instream.close();
            }
 
 
        } catch (ClientProtocolException e) {
        	Log.e(TAG, e.toString());
        } catch (IOException e) {
        	Log.e(TAG, e.toString());
        } catch (JSONException e) {
        	Log.e(TAG, e.toString());
        }
		return json;
    }
 
}