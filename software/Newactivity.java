package com.neonatal.monitoring.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Newactivity extends Activity {
	private static final String THINGSPEAK_CHANNEL_ID = "830980";    //987748
	private static final String THINGSPEAK_API_KEY = "LJTPQVQYHBTFWS2L";//GCSJ8OH6G5RGBHQW
	private static final String THINGSPEAK_API_KEY_STRING = "api_key";  //api_key

	private static final String THINGSPEAK_DATE_TIME = "created_at";
	
	private static final String THINGSPEAK_FIELD1 = "field1";
	private static final String THINGSPEAK_FIELD2 = "field2";
	

	private static final String THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/channels/";
	private static final String THINGSPEAK_FEEDS_LAST = "/feeds/last?"; 
	
	private MyDbHalper mMyDbHalper;
	private SQLiteDatabase mDatabase;
		

	double longitude, latitude;
	private ToggleButton toggleButton;
	
	GPSTracker gps;

	private String date_time;
	private double value1;
	private double value2;
	private ProgressDialog progressDialog;
//	public int seconds = 60;
//    public int minutes = 10;
	Timer t;
	int i = 0;
	private TextView text;
	private TextView mTvStatus;
	private TextView mTvDateTime;
	public static String clickedvalue = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newactivity);
		text= (TextView)findViewById(R.id.tv_new);
		mTvStatus = (TextView)findViewById(R.id.tv_status);
		mTvDateTime = (TextView)findViewById(R.id.tv_DateTime);
		t = new Timer();
		progressDialog = new ProgressDialog(Newactivity.this);
		mMyDbHalper = new MyDbHalper(Newactivity.this, "locationFinderDb",
				null, 1);
		mDatabase = mMyDbHalper.getWritableDatabase();
		
		String lati;
		String longi;
		 gps = new GPSTracker(Newactivity.this);   
		Intent i =getIntent();
		clickedvalue=i.getStringExtra("checked");

		 
		  date_time = String.valueOf(THINGSPEAK_DATE_TIME);
		
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                    	if(clickedvalue == )
//                    	text.setText(String.valueOf(i));
                    	try {
        					new FetchThingspeakTask().execute();
        				} catch (Exception e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
                    
                    }
                });
            }

        }, 0, 50000);
	}

	
	public class FetchThingspeakTask extends AsyncTask<Void, Void, String>
	{
		private String temp;
		private String pre;
		private String longi;
		private String lati;

		@Override
		protected void onPreExecute() 
		{
			progressDialog.setMessage("Processing.........");
        	progressDialog.setCancelable(false);
        	progressDialog.show();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			
			try {
                URL url = new URL(THINGSPEAK_CHANNEL_URL + THINGSPEAK_CHANNEL_ID +
                        THINGSPEAK_FEEDS_LAST + THINGSPEAK_API_KEY_STRING + "=" +
                        THINGSPEAK_API_KEY + "");
                //https://api.thingspeak.com/channels/830980/feeds/last?api_key=LJTPQVQYHBTFWS2L
                
                
                
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
		}
		
		@Override
		protected void onPostExecute(String response) {
			if(progressDialog.isShowing())
        	{
        		progressDialog.dismiss();
        	}
			
			Toast.makeText(Newactivity.this, "Fetching Data.....  "+response, Toast.LENGTH_LONG).show();

    		
            
			if(response == null) {
                Toast.makeText(Newactivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
            	
            	try {
            	//	Toast.makeText(MainActivity.this, "Value", Toast.LENGTH_SHORT).show();
            		
            		
            		 JSONObject channel = (JSONObject) new JSONTokener(response).nextValue();
                    date_time = channel.get(THINGSPEAK_DATE_TIME).toString();
                    value1 = channel.getDouble(THINGSPEAK_FIELD1);
                    value2 = channel.getDouble(THINGSPEAK_FIELD2);
                   // value3=channel.getDouble(THINGSPEAK_FIELD3);
                    
                
                   
                //    Log.d("abhi", String.valueOf(date_time) + "   " + String.valueOf(value5));
                    Log.d("abhi", String.valueOf(value1) + "   " + String.valueOf(value2));
                   // Log.d("abhi", String.valueOf(value3) + "   " + String.valueOf(value4));
                 
                    String st[] = date_time.split("T");
                    String date = st[0];
                    String time = st[1].replace("Z", "");
                    System.out.println(date);
                    System.out.println(time);
                   temp= String.valueOf(value1);
                   pre= String.valueOf(value2);
                   mTvDateTime.setText("Date: " + date +" \n" + "Time: " + time);
                   float t = Float.parseFloat(temp);
           		float p = Float.parseFloat(pre);
                    if(clickedvalue.equals("T")){
                  int vv= (int)value1;
                  Toast.makeText(getApplicationContext(), "----"+vv,Toast.LENGTH_LONG).show();
                    	double  value=((9.0/5.0) * vv) + 32;
                        Toast.makeText(getApplicationContext(), "----"+value,Toast.LENGTH_LONG).show();
                    	String a=String.valueOf(value);
                    	text.setText("Temprature: "+a +"F");
                    	if(value<=60) // correct values is 60
            			{
            				mTvStatus.setText("Low");
            			}
            			else if(value>60 && value<=99) //60 && 99 
            			{
            				mTvStatus.setText("Moderate");
            			}
            			else if(value>99) //99
            			{
            				mTvStatus.setText("High");
            				Toast.makeText(getApplicationContext(), "Baby In Critical Condition", Toast.LENGTH_LONG).show();
    				    	
    						String link = "http://www.google.com/maps/place/"
    								+ lati + "," + longi;
    						
    						String hrtStatus ="High Pulse";
    						String lowHrtStatus ="Low Pulse";
    						
    						String tempStatus ="High Temp";
    						String lowtempStatus ="Low Temp";
    						
    						
    					
    						
    						checkShortestDistance(hrtStatus);
    		            	checkShortestDistanceHos(hrtStatus);
            			    
            			}
                    	
                    }
                    else
                    {
                    	text.setText("Heart Beat: "+String.valueOf(value2));

            			if(p<=20)
            			{
            				mTvStatus.setText("Low");
            				//mTvStatus.setText("High");
            				Toast.makeText(getApplicationContext(), "Baby In Critical Condition", Toast.LENGTH_LONG).show();
    				    	
    						String link = "http://www.google.com/maps/place/"
    								+ lati + "," + longi;
    						
    						String hrtStatus ="High Pulse";
    						String lowHrtStatus ="Low Pulse";
    						
    						String tempStatus ="High Temp";
    						String lowtempStatus ="Low Temp";
    						
    						checkShortestDistance(hrtStatus);
    		            	checkShortestDistanceHos(hrtStatus);
            			    
            			}
            			else if(p>20 && p<=95)// 20 && 95
            			{
            				mTvStatus.setText("Moderate");
            			}
            			else if(p>95) //95
            			{
            				//mTvStatus.setText("Low");
            				mTvStatus.setText("High");
            				Toast.makeText(getApplicationContext(), "Baby In Critical Condition", Toast.LENGTH_LONG).show();
    				    	
    						String link = "http://www.google.com/maps/place/"
    								+ lati + "," + longi;
    						
    						String hrtStatus ="High Pulse";
    						String lowHrtStatus ="Low Pulse";
    						
    						String tempStatus ="High Temp";
    						String lowtempStatus ="Low Temp";
    						
    						checkShortestDistance(hrtStatus);
    		            	checkShortestDistanceHos(hrtStatus);
            			    
            				
            			}
                    }
                    
                    /*Intent i = new Intent(getApplicationContext(), DisplayResult.class);
                    i.putExtra("clickedvalue", clickedvalue);
                    i.putExtra("date", date);
                    i.putExtra("time", time);
    				i.putExtra("value1", String.valueOf(value1));
    				i.putExtra("value2", String.valueOf(value2));*/
    				//i.putExtra("value3", String.valueOf(value3));
    				
    				
    				/*startActivity(i);*/
    				
    				
    				
    				
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            	
            	
			}
        }

		private void checkShortestDistanceHos(String hrtStatus) {
			// TODO Auto-generated method stub
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			
		
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
					
					String link = "Take Care";
					
					try {
						// Toast.makeText(this, smalldisnamephone,
						// Toast.LENGTH_SHORT).show();
						
						Thread.sleep(6000);
						SmsManager mSmsManager = SmsManager.getDefault();
						mSmsManager.sendTextMessage(smalldisnamephone, null,
								"Baby in gradle 2 at building 4 is in criticle situation  "+ "Please "+ link, null, null);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// ---------------------------------

			}
		}

		private void checkShortestDistance(String hrtStatus) {
			// TODO Auto-generated method stub
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			
			Toast.makeText(
					getApplicationContext(),
					"Your Location is - \nLat: " + latitude + "\nLong: "
							+ longitude, Toast.LENGTH_LONG).show();
			

			
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
					
					
					String link = "Take Care";
					try {

						// Toast.makeText(this, smalldisnamephone,
						// Toast.LENGTH_SHORT).show();
						
						Thread.sleep(6000);
						SmsManager mSmsManager = SmsManager.getDefault();
						mSmsManager.sendTextMessage(smalldisnamephone, null,
								"Baby in gradle 2 at building 4 is in criticle situation "+ "Please "+ link, null, null);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// ---------------------------------

				
			}
		}
	}
	
}
//class Helper extends TimerTask 
//{ 
//    public static int i = 0;
//    String text = "";
//    
//    public void getText(String val){
//    	text = val;
//    }
//    
//    public void run() 
//    { 
//    	
//        System.out.println("Timer ran " + ++i); 
//    } 
//}
//