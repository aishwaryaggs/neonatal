package com.neonatal.monitoring.system;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.neonatal.monitoring.system.Newactivity.FetchThingspeakTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends Activity {
	
	

	

//	private double value3;
	
	//private double value5;
	
	
	Timer t;
	private Button mBtnTemp;
	
	
	
	int i1=100;
	private Button mBtnLogout;
	private Button mBtnpressure;

	// Database
	private MyDbHalper mMyDbHalper;
	private SQLiteDatabase mDatabase;
		

	double longitude, latitude;
	private ToggleButton toggleButton;
	
	GPSTracker gps;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = 
		    new StrictMode.ThreadPolicy.Builder().permitAll().build();      
		        StrictMode.setThreadPolicy(policy);
		}
    	mMyDbHalper = new MyDbHalper(MainActivity.this, "locationFinderDb",
				null, 1);
		mDatabase = mMyDbHalper.getWritableDatabase();
		
		
        
       // mBtnHumidity = (Button)findViewById(R.id.btn_humidity);
        mBtnTemp = (Button)findViewById(R.id.btn_temp);
        mBtnpressure=(Button)findViewById(R.id.btn_pressure);
        toggleButton = (ToggleButton)findViewById(R.id.toggleButton1); 
    
        mBtnLogout = (Button)findViewById(R.id.btn_logout);
        
        
        
        gps = new GPSTracker(MainActivity.this);   
        toggleButton.setOnClickListener(new OnClickListener() {
			
	
			private String lati;
			private String longi;

			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				String toggletext=  toggleButton.getText().toString();
				 /*try{*/
		    	Toast.makeText(getApplicationContext(), "toggletextdddqa."+toggletext, Toast.LENGTH_LONG).show();
				  
				  if(toggletext.equals("off"))
				  {
					Toast.makeText(getApplicationContext(), "toggletext."+toggletext, Toast.LENGTH_LONG).show();
					//x.cancel();
					Intent intent=new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
					finish();
					
				  }
				  else {
					  t = new Timer();
					  
					  Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_LONG).show();
					  latitude = gps.getLatitude();
						longitude = gps.getLongitude();
						 t.scheduleAtFixedRate(new TimerTask() {

					            @Override
					            public void run() {
					                runOnUiThread(new Runnable() {

					                    @Override
					                    public void run() {
//					                    	if(clickedvalue == )
//					                    	text.setText(String.valueOf(i));
					                    	try {
					                    	
					    				    	
					    				    		
					    				    		Toast.makeText(getApplicationContext(), "Baby In Critical Condition"+i1, Toast.LENGTH_LONG).show();
					    				    	
					    						String link = "http://www.google.com/maps/place/"
					    								+ lati + "," + longi;
					    						
					    						String hrtStatus ="High Pulse";
					    						String lowHrtStatus ="Low Pulse";
					    						
					    						String tempStatus ="High Temp";
					    						String lowtempStatus ="Low Temp";
					    						
					    						
					    						Thread.sleep(3500);
					    						checkShortestDistance(hrtStatus);
					    		            	checkShortestDistanceHos(hrtStatus);
					    		            	
					    		            	/*checkShortestDistance(tempStatus);
					    		            	checkShortestDistanceHos(tempStatus);
					    		            	
					    		            	
					    		            	checkShortestDistance(lowHrtStatus);
					    		            	checkShortestDistanceHos(lowHrtStatus);
					    		            	
					    		            	checkShortestDistance(lowtempStatus);
					    		            	checkShortestDistanceHos(lowtempStatus);*/
					    				    	}
					    				    	
					        				
					                    catch (Exception e) {
					        					// TODO Auto-generated catch block
					        					e.printStackTrace();
					        				}
					                    
					                    }
					                });
					            }

					        }, 0, 20000);
				
				  }
			}
});
        
        //For Temperature
        mBtnTemp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), Newactivity.class);
				i.putExtra("checked", "T");
				startActivity(i);
				
				
				
				
				
			}
		});
        
        //For Heart Beat Sensor
        
        mBtnpressure.setOnClickListener(new OnClickListener() {
			
//			private AsyncTask<Void, Void, String> rep;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent i = new Intent(getApplicationContext(), Newactivity.class);
				i.putExtra("checked", "P");
				startActivity(i);
				
//				clickedvalue = "P";
//				
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				
//				try {
//					rep=new FetchThingspeakTask().execute();
//			
//					
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
				
				
			}
		});
     
        
        
        mBtnLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.finish();
			}
		});   
        
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.contac:

			Intent intent_author = new Intent(MainActivity.this,
					AddPoliceStation.class);
			startActivity(intent_author);
			return true;

		case R.id.addHospital:
			Intent intent = new Intent(getApplicationContext(), AddHospital.class);
			startActivity(intent);
			return true;
			
		case R.id.thingspeak:
			Intent th = new Intent(getApplicationContext(), Thingspeak.class);
			startActivity(th);
			return true;
		
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	

	public void checkShortestDistance(String status) {

		latitude = gps.getLatitude();
		longitude = gps.getLongitude();
		
		Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
		mDatabase = mMyDbHalper.getReadableDatabase();
		ArrayList list = new ArrayList<String>();
		ArrayList list1 = new ArrayList<String>();
		
		// Toast.makeText(this, getLat + getLong, Toast.LENGTH_SHORT).show();

		String query = "SELECT * FROM police";

		Cursor c = mDatabase.rawQuery(query, null);
		// cc.moveToFirst();
		if (c.moveToFirst()) {
			do {
				// int i = c.getInt(1);
				String name = c.getString(1);
				String phonenumber = c.getString(2);
				/*Double lat = Double.parseDouble(c.getString(3));
				Double lot = Double.parseDouble(c.getString(4));*/
				// Log.i(TAG, "Details from database " +
				// name+" "+phonenumber+" "+lat+" "+lot );
				/*Double dis = DistanceCalculation.distance(latitude, longitude,
						lat, lot, 'k');*/

				//list1.add(dis);
				list.add( name + "~"
						+ phonenumber);

			} while (c.moveToNext());
		}
		//Object obj = Collections.min(list1);
		/*
		 * Log.i(TAG, list.toString() ); Log.i(TAG, obj.toString() );
		 */

		for (int i = 0; i < list.size(); i++) {
			String value = list.get(i).toString();
			String[] data = value.split("~");
			
				String smalldisname = data[0];
				String smalldisnamephone = data[1];
				// Log.i(TAG, smalldisname+"  "+smalldisnamephone );

				Toast.makeText(getApplicationContext(), "Relative Phone  "+smalldisnamephone, Toast.LENGTH_LONG).show();
				// --------------------------

				// send sms code here

				/*String SMSEMERGENCYMESSAGE = "https://www.google.com/maps/search/?api=1&query="
						+ latitude + "," + longitude;*/
				
				String link = "Admit to hospital";
				
				try {
					// Toast.makeText(this, smalldisnamephone,
					// Toast.LENGTH_SHORT).show();
					
					Thread.sleep(6000);
					SmsManager mSmsManager = SmsManager.getDefault();
					mSmsManager.sendTextMessage(smalldisnamephone, null,
							"Baby Is in Critical Condition "+ "Please "+ link, null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ---------------------------------

		}
	}


	public void checkShortestDistanceHos(String status) {
		
		latitude = gps.getLatitude();
		longitude = gps.getLongitude();
		
		Toast.makeText(
				getApplicationContext(),
				"Your Location is - \nLat: " + latitude + "\nLong: "
						+ longitude, Toast.LENGTH_LONG).show();
		

		Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
		mDatabase = mMyDbHalper.getReadableDatabase();
		ArrayList list = new ArrayList<String>();
		ArrayList list1 = new ArrayList<String>();

		// Toast.makeText(this, getLat + getLong, Toast.LENGTH_SHORT).show();

		String query = "SELECT * FROM hospital";

		Cursor c = mDatabase.rawQuery(query, null);
		// cc.moveToFirst();
		if (c.moveToFirst()) {
			do {
				// int i = c.getInt(1);
				String name = c.getString(1);
				String phonenumber = c.getString(2);
				/*Double lat = Double.parseDouble(c.getString(3));
				Double lot = Double.parseDouble(c.getString(4));
				// Log.i(TAG, "Details from database " +
				// name+" "+phonenumber+" "+lat+" "+lot );
				Double dis = DistanceCalculation.distance(latitude, longitude,
						lat, lot, 'k');

				list1.add(dis);*/
				list.add( name + "~"
						+ phonenumber);

			} while (c.moveToNext());
		}
		//Object obj = Collections.min(list1);
		/*
		 * Log.i(TAG, list.toString() ); Log.i(TAG, obj.toString() );
		 */

		for (int i = 0; i < list.size(); i++) {
			String value = list.get(i).toString();
			String[] data = value.split("~");
		/*	String dist = data[0];
			

				String smalallat = data[1];
				String smalllong = data[2];*/
				String smalldisname = data[0];
				String smalldisnamephone = data[1];
				
				Toast.makeText(getApplicationContext(), "Hospital Phone  "+smalldisnamephone, Toast.LENGTH_LONG).show();
				// Log.i(TAG, smalldisname+"  "+smalldisnamephone );

				// --------------------------

				// send sms code here

				/*String SMSEMERGENCYMESSAGE = "https://www.google.com/maps/search/?api=1&query="
						+ latitude + "," + longitude;*/
				
				
				String link = "Admit in to Hospital";
				try {

					// Toast.makeText(this, smalldisnamephone,
					// Toast.LENGTH_SHORT).show();
					Thread.sleep(6000);
					SmsManager mSmsManager = SmsManager.getDefault();
					mSmsManager.sendTextMessage(smalldisnamephone, null,
							"Baby Is in Critical Condition "+ "Please "+ link, null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ---------------------------------

			
		}
	}
}
