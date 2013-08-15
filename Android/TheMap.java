package com.chriskee.android.phs;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TheMap extends MapActivity
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
	//String finalEmail = getIntent().getStringExtra("email");
	
	
	

	
//	private class MapOverlay extends com.google.android.maps.Overlay
//	{
//
//		AddLocFrag dialogFragment = AddLocFrag.newInstance("Add a HotSpot at this location?");
//		
//		@Override
//		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
//		{
//			super.draw(canvas, mapView, shadow);
//			
//			// translate geopoint to screen pixels
//			Point screenPts = new Point();
//			mapView.getProjection().toPixels(p, screenPts);
//			
//			// add the marker
//			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pushpin);
//			canvas.drawBitmap(bmp, screenPts.x-12, screenPts.y-62, null);
//			return true;
//		}
//		
//		@Override
//		public boolean onTouchEvent(MotionEvent event, MapView mapView)
//		{
//			if(event.getAction() == 1)
//			{
//				testPoint = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
//				fLat = (float) (testPoint.getLatitudeE6() / 1E6);
//				fLon = (float) (testPoint.getLongitudeE6() / 1E6);
//				dialogFragment.show(getFragmentManager(), "dialog");
//				//Toast.makeText(getBaseContext(), "Location: " + fLat + "," + fLon, Toast.LENGTH_LONG).show();
//			}
//			return false;
//		}
//	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy); 
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		MyPHS myPHS = (MyPHS)getApplicationContext();
		
        mapView = (MapView) findViewById(R.id.mapView);
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
        openMap = myPHS.getGlobalPushPin();
		mc.animateTo(openMap);
		mc.setZoom(4);
		
		//Toast.makeText(getBaseContext(), "Tap on map to add a location!", Toast.LENGTH_LONG).show();
        // add location pushpin
        //listOfOverlays.add(mapOverlay);
		
		//MapOverlay mapOverlay = new MapOverlay();
		
		
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.pushpin);
		PHSItemizedOverlay itemizedoverlay = new PHSItemizedOverlay(drawable, this);
		
//		OverlayItem overlayitem = new OverlayItem(p, "Hola, Mundo!", "I'm in Mexico City!");
//		itemizedoverlay.addOverlay(overlayitem);
//		mapOverlays.add(itemizedoverlay);
		
		
		getMyLocations();
      

      	
		if(!(Lat.isEmpty()))
		{
			OverlayItem testOverlay = new OverlayItem(p, "Hola, Mundo!", "I'm in Mexico City!");
			Float[] aLat = Lat.toArray(new Float[0]);
			Float[] aLon = Lon.toArray(new Float[0]);
			if(aLat[0] != null)
			{
				
				for(int i = 0; i < aLat.length; i++)
				{
					p = new GeoPoint((int)(aLat[i] * 1E6), (int)(aLon[i] * 1E6));
					testOverlay = new OverlayItem(p, "Hola, Mundo!", "I'm in Mexico City!");
					itemizedoverlay.addOverlay(testOverlay);
					mapOverlays.add(itemizedoverlay);
				
				}
			}
		}
		
		//mapOverlays.add(itemizedoverlay);

        
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
	
	
	public void getMyLocations()
	{

		MyPHS myPHS = (MyPHS)getApplicationContext();
		String email = myPHS.getGlobalEmailAddress(); 
		StringBuilder stringBuilder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000); //Timeout Limit
	    HttpResponse response;                
		JSONObject json = new JSONObject();
	    try
	    {                    
			HttpPost post = new HttpPost("http://www.chriskee.com/Service1.svc/GetMyLocs");
	        json.put("email", email);
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
	    
	    String result = stringBuilder.toString().trim();
	    try 
	    {
			JSONArray jsonArray = new JSONArray(result);
			
			for(int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				//Toast.makeText(getBaseContext(), jsonObject.getString("LocationDescription"), Toast.LENGTH_SHORT).show();
				Lat.add((float) jsonObject.getDouble("Lat"));
				Lon.add((float) jsonObject.getDouble("Lon"));
			}
		} 
	    catch (JSONException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	

	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();
		
		MyPHS myPHS = (MyPHS)getApplicationContext();
		
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        mc = mapView.getController();
        p = myPHS.getGlobalPushPin();
        
		mc.animateTo(p);
		mc.setZoom(4);
        // add location pushpin
//        MapOverlay mapOverlay = new MapOverlay();
//        List<Overlay> listOfOverlays = mapView.getOverlays();
//        listOfOverlays.clear();
//        listOfOverlays.add(mapOverlay);
        
        
        
//        
//        getMyLocations();
//        
//
//		
//		Float[] aLat = Lat.toArray(new Float[0]);
//		Float[] aLon = Lon.toArray(new Float[0]);
//		if(aLat[0] != null)
//		{
//			
//			for(int i = 0; i < aLat.length; i++)
//			{
//				p = new GeoPoint((int)(aLat[i] * 1E6), (int)(aLon[i] * 1E6));
//				MapOverlay mapOverlay2 = new MapOverlay();
//				List<Overlay> listOfOverlays2 = mapView.getOverlays();
//				listOfOverlays.clear();
//				listOfOverlays.add(mapOverlay);
//			}
//		}
//		
		
	}
	
	


}
