package com.chriskee.android.phs;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class PHSItemizedOverlay extends ItemizedOverlay 
{
	Context mContext;
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public PHSItemizedOverlay(Drawable pushpin, Context context) 
	{
		super(boundCenterBottom(pushpin));
		mContext = context;
	}
	
	public void addOverlay(OverlayItem overlay) 
	{
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) 
	{
		 return mOverlays.get(i);
	}

	@Override
	public int size() 
	{
		return mOverlays.size();
	}
	
//	@Override
//	protected boolean onTap(int index) 
//	{
//	  OverlayItem item = mOverlays.get(index);
//	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//	  dialog.setTitle("Photo HotSpot");
//	  dialog.setMessage(locationDescription);
//	  dialog.show();
//	  return true;
//	}

}
