package com.neonatal.monitoring.system;

import java.util.ArrayList;






import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewHospital extends Activity {
	private ArrayList<String> mArraylListName;
	private ArrayList<String> mArraylListPhone;
	private ArrayList<String> mArraylListLatitude;
	private ArrayList<String> mArraylListLongitude;
	private ArrayList<String> mArraylListId;
	private ListView listView;

	private ViewPoliceAdapter mViewRuleAdapter;

	// Database
	private MyDbHalper mMyDbHalper;
	private SQLiteDatabase mDatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_hospital);

		mMyDbHalper = new MyDbHalper(ViewHospital.this, "locationFinderDb",
				null, 1);
		mDatabase = mMyDbHalper.getReadableDatabase();

		listView = (ListView) findViewById(R.id.listview);

		mArraylListId = new ArrayList<String>();
		mArraylListName = new ArrayList<String>();
		mArraylListPhone = new ArrayList<String>();
		mArraylListLatitude = new ArrayList<String>();
		mArraylListLongitude = new ArrayList<String>();

		try {

			String query = "SELECT * FROM hospital";

			Cursor c = mDatabase.rawQuery(query, null);
			if (c.moveToFirst()) {
				do {
					mArraylListId.add(c.getString(c.getColumnIndex("id")));
					mArraylListName.add(c.getString(c.getColumnIndex("name")));
					mArraylListPhone
							.add(c.getString(c.getColumnIndex("phone")));
					mArraylListLatitude.add(c.getString(c
							.getColumnIndex("latitude")));
					mArraylListLongitude.add(c.getString(c
							.getColumnIndex("longitude")));
				} while (c.moveToNext());
			}

			mViewRuleAdapter = new ViewPoliceAdapter(ViewHospital.this,
					mArraylListId, mArraylListName, mArraylListPhone,
					mArraylListLatitude, mArraylListLongitude);
			listView.setAdapter(mViewRuleAdapter);

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					String idd = mArraylListId.get(position);
					Toast.makeText(ViewHospital.this, "idd: " + idd,
							Toast.LENGTH_SHORT).show();

					String where = "id=?";
					int numberOFEntriesDeleted = mDatabase.delete("hospital",
							where, new String[] { idd });
				}
				
			});

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
