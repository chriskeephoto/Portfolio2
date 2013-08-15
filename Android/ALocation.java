package com.chriskee.android.phs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ALocation extends Activity 
{
	float latitude;
	float longitude;

	String strLocationPhoto;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alocation);
		
		String locationDescription = getIntent().getStringExtra("Description");
		int locationID = getIntent().getIntExtra("ID", 0);
		latitude = getIntent().getFloatExtra("Lat", 0);
		longitude = getIntent().getFloatExtra("Lon", 0);
		
		TextView description = (TextView) findViewById(R.id.ALocDescription);
		description.setText(locationDescription.toString());
		getPhoto(locationID);
		byte[] imgByteArray = Base64.decode(strLocationPhoto, Base64.DEFAULT);
		Bitmap bitmapImage = BitmapFactory.decodeByteArray(imgByteArray, 0, imgByteArray.length);
		ImageView iv = (ImageView)findViewById(R.id.ALocImage);
		iv.setImageBitmap(bitmapImage);
		
	}
	
	public void onClickALocDone(View view)
	{
		finish();
	}
	
	public void onClickALocOnMap(View view)
	{		
		Intent i = new Intent("com.chriskee.android.phs.LocMap");
		//i.putExtra("Lat", latitude);
		//i.putExtra("Lon", longitude);
		MyPHS myPHS = (MyPHS)getApplicationContext();
		GeoPoint p = new GeoPoint((int)(latitude * 1E6),(int)(longitude * 1E6));
		myPHS.setGlobalPushPin(p);
		startActivity(i);
	}
	
	public void getPhoto(int locationID)
	{
		StringBuilder stringBuilder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000); //Timeout Limit
	    HttpResponse response;                
		JSONObject json = new JSONObject();
	    try
	    {                    
			HttpPost post = new HttpPost("http://www.chriskee.com/Service1.svc/GetLocPhotoByID");
	        json.put("LocationID", locationID);
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
				strLocationPhoto = jsonObject.getString("Photo");
				//Toast.makeText(getBaseContext(), jsonObject.getString("LocationDescription"), Toast.LENGTH_SHORT).show();
				//locations.add(jsonObject.getString("LocationDescription"));
				//locationID.add(jsonObject.getInt("LocationID"));
			}
		} 
	    catch (JSONException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
