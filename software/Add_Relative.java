package com.neonatal.monitoring.system;

import android.app.Activity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Relative extends Activity {
	
	private EditText etRelative;
	private EditText etphone;
	
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
		setContentView(R.layout.activity_add__relative);
		
		etRelative = (EditText)findViewById(R.id.et_relativename);
        etphone = (EditText)findViewById(R.id.phone);
        
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        
        mMyDbHalper = new MyDbHalper(Add_Relative.this,
                "locationFinderDb", null, 1);
        mDatabase = mMyDbHalper.getWritableDatabase();
    
        
        
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String name = etRelative.getText().toString().trim();
		        String phone = etphone.getText().toString().trim();

		        ContentValues cv = new ContentValues();
		        cv.put("name", name);
		        cv.put("phone", phone);
		      /*  cv.put("latitude", lat);
		        cv.put("longitude", along);*/
		        
		        
		     // Insert Query
		        long id = mDatabase.insert("police", null, cv);

		        if(id > 0){
		            Toast.makeText(Add_Relative.this, "data Inserted, ID: " + " "+ id, Toast.LENGTH_SHORT).show();
		        }
		        else {
		            Toast.makeText(Add_Relative.this, "Error:",  Toast.LENGTH_SHORT).show();
		        }
				
			}
		});
        
        
        button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Add_Relative.this, View_Relative.class);
				startActivity(intent);
			}
		});
        
        
        
        
        
	}

}

   