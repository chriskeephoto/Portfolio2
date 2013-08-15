package com.chriskee.android.phs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyLocations extends ListActivity 
{		
	//final String email = getIntent().getStringExtra("email");
	List<String> locations = new ArrayList<String>();
	List<Integer> locationID = new ArrayList<Integer>();
	List<Float> latitude = new ArrayList<Float>();
	List<Float> longitude = new ArrayList<Float>();
	
		
	public void onCreate(Bundle savedInstanceState)
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy);
		
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.locations);
		
		
		//new ReadJSONFeedTaskLocations().execute("http://www.chriskee.com/Service1.svc/GetMyLocations");
		
		//String[] aLocations = locations.toString();
		//locations.add("test");
		 
		MyPHS myPHS = (MyPHS)getApplicationContext();
		
		String email = myPHS.getGlobalEmailAddress(); 
		
		getMyLocations(email);
		String[] aLocations = locations.toArray(new String[0]);
		//if(aLocations[0] == null)
			//Toast.makeText(getBaseContext(), "No locations.", Toast.LENGTH_LONG).show();
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aLocations));
		
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
		Integer[] aLocationID = locationID.toArray(new Integer[0]);
		String[] aLocations = locations.toArray(new String[0]);
		Float[] aLat = latitude.toArray(new Float[0]);
		Float[] aLon = longitude.toArray(new Float[0]);
		
		Intent i = new Intent("com.chriskee.android.phs.ALocation");
		i.putExtra("Lat", aLat[position]);
		i.putExtra("Lon", aLon[position]);
		i.putExtra("Description", aLocations[position]);
		i.putExtra("ID", aLocationID[position]);
		startActivity(i);
		//Toast.makeText(getBaseContext(), "Location ID is" + aLocationID[position].toString(), Toast.LENGTH_LONG).show();
	}
	
	public void getMyLocations(String email)
	{
		StringBuilder stringBuilder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000); //Timeout Limit
	    HttpResponse response;                
		JSONObject json = new JSONObject();
	    try
	    {                    
			HttpPost post = new HttpPost("http://www.chriskee.com/Service1.svc/GetMyLocs");
	        json.put("email", email);
	        StringEntity se = new StringEntity(json.toString());  
	        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	        post.setEntity(se);                    
	        response = client.execute(post);
	        
	        if(response!=null)
	        {
	        	HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while((line = reader.readLine()) != null)
				{
					stringBuilder.append(line);
				}
				
	        }
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();                    
	    }                
	    
	    String result = stringBuilder.toString().trim();
	    try 
	    {
			JSONArray jsonArray = new JSONArray(result);
			
			for(int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				//Toast.makeText(getBaseContext(), jsonObject.getString("LocationDescription"), Toast.LENGTH_SHORT).show();
				locations.add(jsonObject.getString("LocationDescription"));
				locationID.add(jsonObject.getInt("LocationID"));
				latitude.add((float) jsonObject.getDouble("Lat"));
				longitude.add((float) jsonObject.getDouble("Lon"));
			}
		} 
	    catch (JSONException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
	
//	public String readJSONFeed(String URL)
//	{
//		StringBuilder stringBuilder = new StringBuilder();
//		HttpClient client = new DefaultHttpClient();
//		//HttpGet httpGet = new HttpGet(URL);
//		
//		JSONObject json = new JSONObject();
//		try
//		{
//			HttpPost post = new HttpPost(URL);
//			json.put("email", email);
//			StringEntity se = new StringEntity(json.toString());  
//		    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//		    post.setEntity(se); 
//			HttpResponse response = client.execute(post);
//			StatusLine statusLine = response.getStatusLine();
//			int statusCode = statusLine.getStatusCode();
//			if(statusCode == 200)
//			{
//				HttpEntity entity = response.getEntity();
//				InputStream content = entity.getContent();
//				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//				String line;
//				while((line = reader.readLine()) != null)
//				{
//					stringBuilder.append(line);
//				}
//				
//			}
//			else
//			{
//				Log.e("JSON", "Failed to download file");
//			}
//		}
//		catch(ClientProtocolException e)
//		{
//			e.printStackTrace();
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return stringBuilder.toString();
//	}
//	
//	
//	private class ReadJSONFeedTaskLocations extends AsyncTask<String, Void, String>
//	{
//		protected String doInBackground(String... urls)
//		{
//			return readJSONFeed(urls[0]);
//		}
//		
//		protected void onPostExecute(String result)
//		{
//			try
//			{
//				//String test = new String();
//				JSONArray jsonArray = new JSONArray(result);
//				Log.i("JSON", "Number of Locations in feed: " + jsonArray.length());
//				
//				for(int i = 0; i < jsonArray.length(); i++)
//				{
//					JSONObject jsonObject = jsonArray.getJSONObject(i);
//					//Toast.makeText(getBaseContext(), jsonObject.getString("LocationDescription"), Toast.LENGTH_SHORT).show();
//					locations.add(jsonObject.getString("LocationDescription"));
//					//test = locations.toString();
//				}
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}


