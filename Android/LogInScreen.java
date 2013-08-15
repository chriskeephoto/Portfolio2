package com.chriskee.android.phs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogInScreen extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}
	
	public void onClick(View view)
    {
		//startService(new Intent("com.chriskee.android.photohotspot.LogInService"));
		
    	EditText et1 = (EditText) findViewById(R.id.emailText);
    	EditText et2 = (EditText) findViewById(R.id.passwordText);
    	
    	String emailTxt = et1.getText().toString();
    	String passwordTxt = et2.getText().toString();
    	
    	if(emailTxt.length() == 0 || passwordTxt.length() == 0)
    	{
    		Toast.makeText(getBaseContext(), "Email and Password can not be empty!", Toast.LENGTH_LONG).show();
    	}
    	else
    	{
    		Intent i = new Intent("com.chriskee.android.phs.ValidateLogin");
    		i.putExtra("eTxt", emailTxt);
    		i.putExtra("pTxt", passwordTxt);
    		startActivity(i);
    	}
		
    }
	
	public void onClick2(View view)
	{
		startActivity(new Intent("com.chriskee.android.phs.Register"));
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	
	
}
