package com.neonatal.monitoring.system;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;






import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayResult extends Activity {

	private static final String THINGSPEAK_CHANNEL_ID = "830980";    //987748
	private static final String THINGSPEAK_API_KEY = "LJTPQVQYHBTFWS2L";//GCSJ8OH6G5RGBHQW
	private static final String THINGSPEAK_API_KEY_STRING = "api_key";  //api_key

	private static final String THINGSPEAK_DATE_TIME = "created_at";
	
	private static final String THINGSPEAK_FIELD1 = "field1";
	private static final String THINGSPEAK_FIELD2 = "field2";
	

	private static final String THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/channels/";
	private static final String THINGSPEAK_FEEDS_LAST = "/feeds/last?"; 
	
	
	private String date_time;
	private double value1;
	private double value2;
//	private double value3;
	
	//private double value5;
	
	
   
	private Button mBtnTemp;
	
	
	
	private ProgressDialog progressDialog;
	private Button mBtnLogout;
	private Button mBtnpressure;
	
	
	private String temp;
	private String pressure;
	
	
	private String clickedvalue;
	
	
	private TextView mTvDateTime;
	private TextView mTvType;
	private TextView mTvValue;
	private TextView mTvStatus;
	private String date;
	private String time;
	private String gas;
	
	double latitude;
	double longitude;
	private String[] result;
	private String dt;
	private String response;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_result);
		
		Intent i = getIntent();
		clickedvalue = i.getStringExtra("clickedvalue");
		date = i.getStringExtra("date");
		time = i.getStringExtra("time");
		
		temp = i.getStringExtra("value1");
		pressure=i.getStringExtra("value2");
		//gas = i.getStringExtra("value5");
				
		//temp="5.2";
		
		//="9.6";
		//dt=date+time;
		
	
		mTvDateTime = (TextView)findViewById(R.id.tv_DateTime);
		mTvType = (TextView)findViewById(R.id.tv_type);
		mTvValue = (TextView)findViewById(R.id.tv_value);
		mTvStatus = (TextView)findViewById(R.id.tv_status);
		
		
		float t = Float.parseFloat(temp);
		float p = Float.parseFloat(pressure);
		
		
		
	/*	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("date",date));
		nameValuePairs.add(new BasicNameValuePair("time",time));
		nameValuePairs.add(new BasicNameValuePair("temp",temp));
		nameValuePairs.add(new BasicNameValuePair("pressure",pressure));
		
		
		try {
			response = CustomHttpClient.executeHttpPost(global.URL + "adddata.jsp", nameValuePairs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		Toast.makeText(getApplicationContext(), "response: " + response, Toast.LENGTH_LONG).show();
		
		/*if (response.trim().equalsIgnoreCase("true"))
		{
			
			
			SharedPreferences.Editor editor = pref.edit();
            editor.putString("AdminId", AdminId);
            editor.putString("password", password);
            editor.commit();
            
			Intent intent = new Intent(LoginActivity.this, Admin.class);
			startActivity(intent);
			
		}*/
		
		
		
		
		
		
		
		
		
		
		
		
		Toast.makeText(getApplicationContext(), "clickedValue   "+clickedvalue, Toast.LENGTH_LONG).show();
		
		mTvDateTime.setText("Date: " + date +" \n" + "Time: " + time);
		
		

		
		
		
		
		
	 if(clickedvalue.equalsIgnoreCase("T"))
		{
			mTvType.setText("Temperature");
			mTvValue.setText(temp);
			
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("date",date));
			nameValuePairs.add(new BasicNameValuePair("time",time));
			nameValuePairs.add(new BasicNameValuePair("temp",temp));
		
			
			if(t<=20)
			{
				mTvStatus.setText("Low");
			}
			else if(t>20 && t<=25)
			{
				mTvStatus.setText("Moderate");
			}
			else if(t>25)
			{
				mTvStatus.setText("High");
			    
			}
			
			
		
		
		
		else if(clickedvalue.equalsIgnoreCase("P"))
		{
			mTvType.setText("Pressure");
			mTvValue.setText(pressure);
			
			
			/*ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("date",date));
			nameValuePairs.add(new BasicNameValuePair("time",time));
			nameValuePairs.add(new BasicNameValuePair("pressure",pressure));*/
			
			
			
			
			if(t<=20)
			{
				mTvStatus.setText("Low");
			}
			else if(t>20 && t<=25)
			{
				mTvStatus.setText("Moderate");
			}
			else if(t>25)
			{
				mTvStatus.setText("High");
				
			}
		}
	 
		
		
		
        

	}
	
	}
}



