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
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

public class ValidateRegister extends Activity 
{

	String finalEmail; // Gets passed when starting "HomeScreen" activity.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creatingaccount);
		
		MyPHS myPHS = (MyPHS)getApplicationContext();
		
		String email = getIntent().getStringExtra("email");
		String password = getIntent().getStringExtra("password");
		String firstName = getIntent().getStringExtra("firstName");
		String lastName = getIntent().getStringExtra("lastName");
		
		if(registerUser(email, password, firstName, lastName))
		{
			myPHS.setGlobalEmailAddress(email); 
			startActivity(new Intent("com.chriskee.android.phs.HomeScreen"));
			finish();
		}
		else
		{
			Toast.makeText(getBaseContext(), "Error! User already exists.", Toast.LENGTH_LONG).show();
			finish();
		}
		
		//Toast.makeText(getBaseContext(), email + ", " + password, Toast.LENGTH_LONG).show();
		
	}
	
	protected boolean registerUser(final String email, final String password, final String firstName, final String lastName) 
	{
		StringBuilder stringBuilder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000); //Timeout Limit
	    HttpResponse response;                
		JSONObject json = new JSONObject();
		String result;
	    try
	    {                    
			HttpPost post = new HttpPost("http://www.chriskee.com/Service1.svc/AddUser");
	        json.put("EmailAddress", email);
	        json.put("Password", password);
	        json.put("FirstName", firstName);
	        json.put("LastName", lastName);
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
	    {
	    	finalEmail = email;
	    	return true;
	    }
	    else
	    	return false;
	}
	

}
