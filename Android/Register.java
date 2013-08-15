package com.chriskee.android.phs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register);
	}
	
	public void onClickRegister2(View view)
	{
		EditText et1 = (EditText)findViewById(R.id.register_emailText);
		EditText et2 = (EditText)findViewById(R.id.register_password);
		EditText et3 = (EditText)findViewById(R.id.register_firstName);
		EditText et4 = (EditText)findViewById(R.id.register_lastName);
		String email = et1.getText().toString();
		String password = et2.getText().toString();
		String firstName = et3.getText().toString();
		String lastName = et4.getText().toString();
		
		if(email.length() == 0 || password.length() == 0)
		{
			Toast.makeText(getBaseContext(), "Please fill in both email-address and password.", Toast.LENGTH_LONG).show();
		}
		else
		{
			Intent i = new Intent("com.chriskee.android.phs.ValidateRegister");
			i.putExtra("email", email);
			i.putExtra("password", password);
			i.putExtra("firstName", firstName);
			i.putExtra("lastName", lastName);
			startActivity(i);
			finish();
		}
		
	}
	
	public void onClickRegisterCancel(View view)
	{
		//startActivity(new Intent("com.chriskee.android.phs.Register"));
		finish();
	}

}
