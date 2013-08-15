package com.chriskee.android.phs;

import java.io.BufferedReader;
import java.io.IOException;
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
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;


public class ValidateLogin extends Activity 
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy); 

		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkln);
		
		MyPHS myPHS = (MyPHS)getApplicationContext();
		
		String emailTxt = getIntent().getStringExtra("eTxt");
		String passwordTxt = getIntent().getStringExtra("pTxt");
		
		
		if(isValidUser(emailTxt, passwordTxt))
		{
			myPHS.setGlobalEmailAddress(emailTxt); 
			startActivity(new Intent("com.chriskee.android.phs.HomeScreen"));
			finish();
		}
		else
		{
			Toast.makeText(getBaseContext(), "Error! Invalid email:password combination.", Toast.LENGTH_LONG).show();
			//startActivity(new Intent("com.chriskee.android.phs.HomeScreen"));
			finish();
		}
		
		//HttpClient client = new DefaultHttpClient();
		//HttpPost post = new HttpPost("http://www.chriskee.com/Service1.svc/ValidateUser");
		
//		post.setHeader("Content-type", "application/json");
//		post.setHeader("Accept", "application/json");
//		
//		HttpEntity entity = response.getEntity();
//		
//		JSONObject hsObject = new JSONObject();
//		
//		try
//		{
//			hsObject.put("email", emailTxt);
//			hsObject.put("password", passwordTxt);
//			//post.setEntity(new StringEntity(hsObject.toString(), "UTF-8")); 
//			StringEntity se = new StringEntity( hsObject.toString());
//			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//			
//			
//			
//			//post.setEntity(se);
//			//HttpResponse response = client.execute(post);   
//			//HttpEntity entity = response.getEntity();
//			//if (entity != null) 
//			//{
////				InputStream instream = entity.getContent();
////				String result= convertStreamToString(instream);
////				JSONObject jsonObject = new JSONObject(result);
////				Toast.makeText(getBaseContext(), jsonObject.getString("ValidateUser1"), Toast.LENGTH_SHORT).show();
////				instream.close();
//			//}
//			
//		}
//		
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			Toast.makeText(getBaseContext(), "Error! try failed", Toast.LENGTH_LONG).show();
//		}
		
	}
	
	
	protected boolean isValidUser(final String email, final String password) 
	{
		StringBuilder stringBuilder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000); //Timeout Limit
	    HttpResponse response;                
		JSONObject json = new JSONObject();
		String result;
	    try
	    {                    
			HttpPost post = new HttpPost("http://www.chriskee.com/Service1.svc/ValidateUser");
	        json.put("email", email);
	        json.put("password", password);
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
	    
	    result = stringBuilder.toString().trim();
	    if(result.equalsIgnoreCase("true"))
	    {
	    	//finalEmail = email;
	    	return true;
	    }
	    else
	    	return false;
	}


}
