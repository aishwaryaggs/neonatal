package com.neonatal.monitoring.system;



import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddHospital extends Activity {
	private EditText etPoliceStation;
	private EditText etphone;
	private EditText etlatitude;
	private EditText etlomgitude;
	private Button button1;
	private Button button2;
	
	//Database
    private MyDbHalper mMyDbHalper;
    private SQLiteDatabase mDatabase;
	private Button getNearestPoliceStation;
	
	GPSTracker gps;
	double latitude;
	double longitude;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_hospital);
		etPoliceStation = (EditText)findViewById(R.id.etHospital);
        etphone = (EditText)findViewById(R.id.hosphone);
     /*   etlatitude = (EditText)findViewById(R.id.hoslatitude);
        etlomgitude = (EditText)findViewById(R.id.hoslongitude);*/
        
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        
        mMyDbHalper = new MyDbHalper(AddHospital.this,
                "locationFinderDb", null, 1);
        mDatabase = mMyDbHalper.getWritableDatabase();
        
        gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        
        
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String name = etPoliceStation.getText().toString().trim();
		        String phone = etphone.getText().toString().trim();
		  /*      String lat = etlatitude.getText().toString().trim();
		        String  along = etlomgitude.getText().toString().trim();*/
		        
		        ContentValues cv = new ContentValues();
		        cv.put("name", name);
		        cv.put("phone", phone);
		     /*   cv.put("latitude", lat);
		        cv.put("longitude", along);*/
		        
		        //Insert Query
		        long id = mDatabase.insert("hospital", null, cv);

		        if(id > 0){
		            Toast.makeText(AddHospital.this, "data Inserted, ID: " + " "+ id, Toast.LENGTH_SHORT).show();
		        }
		        else {
		            Toast.makeText(AddHospital.this, "Error:",  Toast.LENGTH_SHORT).show();
		        }
			}
		});
        
        
        button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddHospital.this, ViewHospital.class);
				startActivity(intent);
			}
		});
	}
}

