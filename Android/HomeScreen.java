package com.chriskee.android.phs;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class HomeScreen extends TabActivity  
{    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        
        //String finalEmail = getIntent().getStringExtra("email");
        Toast.makeText(getBaseContext(), ((MyPHS) this.getApplication()).getGlobalEmailAddress(), Toast.LENGTH_LONG).show();
        
        Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost();  // The activity TabHost
		TabHost.TabSpec spec;  // Resusable TabSpec for each tab
		Intent intent;  // Reusable Intent for each tab
		
		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, TheMap.class);
		
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("map").setIndicator("Map",
				res.getDrawable(R.drawable.ic_launcher))
				.setContent(intent);
		tabHost.addTab(spec);
		
		// Do the same for the other tabs
		intent = new Intent().setClass(this, MyLocations.class);
		spec = tabHost.newTabSpec("location info").setIndicator("My Locations",
				res.getDrawable(R.drawable.ic_launcher))
				.setContent(intent);
		tabHost.addTab(spec);
		
		// Do the same for the other tabs
		intent = new Intent().setClass(this, AddMarker.class);
		spec = tabHost.newTabSpec("location info").setIndicator("Add HotSpot",
				res.getDrawable(R.drawable.ic_launcher))
				.setContent(intent);
		tabHost.addTab(spec);
			
		tabHost.setCurrentTab(0);
        
        
        
    }

    
}