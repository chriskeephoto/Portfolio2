package com.chriskee.android.phs;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddLocation extends Activity 
{
	String[] locationTypes;
	float lat;
	float lon;
	String locationType = "1";
	String URL = "http://www.chriskee.com/Service1.svc/AddLocation";
	TextView textTargetUri;
	String test;
	//byte[] myImage;
	String encodedImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addloc);
		
		Toast.makeText(this, Float.toString(getIntent().getFloatExtra("lat", 0)) + ", " + Float.toString(getIntent().getFloatExtra("lon", 0)), Toast.LENGTH_SHORT).show();
		
		Button attachImage = (Button)findViewById(R.id.AttachPhoto);
		 
		textTargetUri = (TextView)findViewById(R.id.targeturi);
		
		lat = getIntent().getFloatExtra("lat", 0);
		lon = getIntent().getFloatExtra("lon", 0);
		
		locationTypes = getResources().getStringArray(R.array.locType_array);
		Spinner s1 = (Spinner) findViewById(R.id.locationTypeSpinner);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, locationTypes);
		
		s1.setAdapter(adapter);
		s1.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				int index = arg0.getSelectedItemPosition();
				if(locationTypes[index].equalsIgnoreCase("urban"))
					locationType = "1";
				if(locationTypes[index].equalsIgnoreCase("nature"))
					locationType = "2";
				if(locationTypes[index].equalsIgnoreCase("rustic"))
					locationType = "3";
				Toast.makeText(getBaseContext(), "You have selected: " + locationTypes[index], Toast.LENGTH_SHORT).show();
			}
			public void onNothingSelected(AdapterView<?> arg0)
			{
				
			}
		});
		
		attachImage.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, 0);
			}
		});
			
	}
	
//	public void onClickAttachPhoto(View view)
//	{
//		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//		
//		
//	}
	
	public void onClickAddLoc(View view) throws Exception
	{	
		EditText et1 = (EditText)findViewById(R.id.locationDescriptionText);
		String locationDescription = et1.getText().toString();
		
		if(locationDescription.length() == 0)
			Toast.makeText(getBaseContext(), "Please enter a location description", Toast.LENGTH_LONG).show();
		else
		{
			Intent i = new Intent("com.chriskee.android.phs.ValidateLocation");
			i.putExtra("lat", lat);
			i.putExtra("lon", lon);
			i.putExtra("LocationDescription", locationDescription);
			i.putExtra("LocationType", locationType);
			i.putExtra("Photo", encodedImage);
			startActivity(i);
			finish();
		}
		
	}
	
	public void onClickAddLocCancel(View view)
	{
		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK)
		{
			Uri targetUri = data.getData();
			textTargetUri.setText(targetUri.toString());
			test = targetUri.toString();
			try {
				Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), targetUri);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] myImage = baos.toByteArray();
				encodedImage = Base64.encodeToString(myImage, Base64.DEFAULT);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
