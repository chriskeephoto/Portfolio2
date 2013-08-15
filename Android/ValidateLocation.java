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
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

public class ValidateLocation extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy); 
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checklocation);
		
		MyPHS myPHS = (MyPHS)getApplicationContext();
		
		String email = myPHS.getGlobalEmailAddress();  
		float lat = getIntent().getFloatExtra("lat", 0);
		float lon = getIntent().getFloatExtra("lon", 0);
		String locationDescription = getIntent().getStringExtra("LocationDescription");
		String photo = getIntent().getStringExtra("Photo");
		//byte[] photo = getIntent().getByteArrayExtra("Photo");
		int locationType = Integer.parseInt(getIntent().getStringExtra("LocationType"));
		
		if(addHotSpot(email, lat, lon, locationDescription, locationType, photo))
		{
			Toast.makeText(getBaseContext(), "Location added", Toast.LENGTH_LONG).show();
			finish();
		}
		else
		{
			Toast.makeText(getBaseContext(), "Error! Location NOT added", Toast.LENGTH_LONG).show();
			finish();
		}
	}
	
	
	public boolean addHotSpot(String email, float lat, float lon, String locationDescription, int locationType, String photo)
	{
		StringBuilder stringBuilder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000); //Timeout Limit
	    HttpResponse response;                
		JSONObject json = new JSONObject();
		String result;
	    try
	    {                    
			HttpPost post = new HttpPost("http://www.chriskee.com/Service1.svc/AddLocTest");
			//HttpPost post = new HttpPost("http://www.chriskee.com/Service1.svc/AddLocation");
			//HttpPost post = new HttpPost("http://localhost:11523/Service1.svc/AddLocation");
	        json.put("email", email);
	        json.put("Lat", lat);
	        json.put("Lon", lon);
	        json.put("LocationDescription", locationDescription);
	        json.put("LocationTypeID", locationType);
	        if(photo != null)
	        	json.put("Photo", photo);
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
				
				
				//Toast.makeText(getBaseContext(), stringBuilder.toString(), Toast.LENGTH_LONG).show();
	        }
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();                    
	    }                
	    
	    result = stringBuilder.toString().trim();
	    if(result.equalsIgnoreCase("true"))
	    	return true;
	    else
	    	return false;
	}
	
}
