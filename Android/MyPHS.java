package com.chriskee.android.phs;

import com.google.android.maps.GeoPoint;

import android.app.Application;

public class MyPHS extends Application 
{
	private String globalEmailAddress;
	private String globalPassword;
	private GeoPoint globalPushPin = new GeoPoint((int)((32.78119) * 1E6),(int)((-96.67455) * 1E6));
	
	@Override
	public void onCreate()
	{
		//globalEmailAddress = "";
		//globalPassword = "";
		super.onCreate();
	}
	
	public GeoPoint getGlobalPushPin()
	{
		return globalPushPin;
	}
	
	public void setGlobalPushPin(GeoPoint globalPushPin)
	{
		this.globalPushPin = globalPushPin;
	}
	
	public String getGlobalEmailAddress() 
	{ 
		return globalEmailAddress; 
	}
	public void setGlobalEmailAddress(String globalEmailAddress) 
	{
		this.globalEmailAddress = globalEmailAddress;
	}
	
	public String getGlobalPassword() 
	{ 
		return globalPassword; 
	}
	public void setGlobalPassword(String globalPassword) 
	{
		this.globalPassword = globalPassword;
	}

}
