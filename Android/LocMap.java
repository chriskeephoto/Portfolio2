package com.chriskee.android.phs;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class LocMap extends MapActivity 
{
	MapView mapView;
	GeoPoint p;
	GeoPoint testPoint;
	MapController mc;
	float fLat;
	float fLon;
	
	private class MapOverlay2 extends com.google.android.maps.Overlay
	{	
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
		
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locmap);
		
		MyPHS myPHS = (MyPHS)getApplicationContext();
		
        mapView = (MapView) findViewById(R.id.mapView2);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        mc = mapView.getController();
        
//        String coordinate[] = {"33.1339620", "-97.0827230"};
//        double lat = Double.parseDouble(coordinate[0]);
//        double lon = Double.parseDouble(coordinate[1]);
//        
//        p = new GeoPoint(
//        		(int)(lat * 1E6),
//        		(int)(lon * 1E6));
        
		p = myPHS.getGlobalPushPin();
		mc.animateTo(p);
		mc.setZoom(18);
        // add location pushpin
        MapOverlay2 mapOverlay = new MapOverlay2();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);
        
	}
	
	@Override
	protected boolean isRouteDisplayed() 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public void onClickLocMapBack(View view)
	{
		MyPHS myPHS = (MyPHS)getApplicationContext();
		GeoPoint p = new GeoPoint((int)((32.78119) * 1E6),(int)((-96.67455) * 1E6));
		myPHS.setGlobalPushPin(p);
		finish();
	}

}
