package com.chriskee.android.phs;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class AddMarker extends MapActivity 
{
	MapView mapView;
	GeoPoint p;
	GeoPoint openMap;
	GeoPoint testPoint;
	MapController mc;
	float fLat;
	float fLon;
	List<Float> Lat = new ArrayList<Float>();
	List<Float> Lon = new ArrayList<Float>();
	
	private class MapOverlay extends com.google.android.maps.Overlay
	{

		AddLocFrag dialogFragment = AddLocFrag.newInstance("Add a HotSpot at this location?");
		
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
		{
			super.draw(canvas, mapView, shadow);
			
			// translate geopoint to screen pixels
			Point screenPts = new Point();
			mapView.getProjection().toPixels(p, screenPts);
			
			// add the marker
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pushpin);
			canvas.drawBitmap(bmp, screenPts.x-12, screenPts.y-62, null);
			return true;
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event, MapView mapView)
		{
			if(event.getAction() == 1)
			{
				testPoint = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
				fLat = (float) (testPoint.getLatitudeE6() / 1E6);
				fLon = (float) (testPoint.getLongitudeE6() / 1E6);
				dialogFragment.show(getFragmentManager(), "dialog");
				//Toast.makeText(getBaseContext(), "Location: " + fLat + "," + fLon, Toast.LENGTH_LONG).show();
			}
			return false;
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		MyPHS myPHS = (MyPHS)getApplicationContext();
		
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        mc = mapView.getController();
        
        p = myPHS.getGlobalPushPin();
		mc.animateTo(p);
		mc.setZoom(4);
		
         //add location pushpin
      MapOverlay mapOverlay = new MapOverlay();
      List<Overlay> listOfOverlays = mapView.getOverlays();
      listOfOverlays.clear();
      listOfOverlays.add(mapOverlay);
		
		
		
		Toast.makeText(getBaseContext(), "Tap on map to add a location!", Toast.LENGTH_LONG).show();

        
	}
	
	@Override
	protected boolean isRouteDisplayed() 
	{
		return false;
	}
	
	public void doPositiveClick()
	{
		Intent i = new Intent("com.chriskee.android.phs.AddLocation");
		//i.putExtra("email", finalEmail);
		i.putExtra("lat", fLat);
		i.putExtra("lon", fLon);
		startActivity(i);
	}
	
	public void doNegativeClick()
	{
	}
	
}
	
	

//	@Override
//	protected void onResume() 
//	{
//		// TODO Auto-generated method stub
//		super.onResume();
//		
//		MyPHS myPHS = (MyPHS)getApplicationContext();
//		
//        mapView = (MapView) findViewById(R.id.mapView);
//        mapView.setBuiltInZoomControls(true);
//        mapView.setSatellite(true);
//        mc = mapView.getController();
//        p = myPHS.getGlobalPushPin();
//        
//		mc.animateTo(p);
//		mc.setZoom(4);
//
//        
//        
//        
////        
////        getMyLocations();
////        
////
////		
////		Float[] aLat = Lat.toArray(new Float[0]);
////		Float[] aLon = Lon.toArray(new Float[0]);
////		if(aLat[0] != null)
////		{
////			
////			for(int i = 0; i < aLat.length; i++)
////			{
////				p = new GeoPoint((int)(aLat[i] * 1E6), (int)(aLon[i] * 1E6));
////				MapOverlay mapOverlay2 = new MapOverlay();
////				List<Overlay> listOfOverlays2 = mapView.getOverlays();
////				listOfOverlays.clear();
////				listOfOverlays.add(mapOverlay);
////			}
////		}
////		
//		
//	}
	
	
