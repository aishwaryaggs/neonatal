package com.neonatal.monitoring.system;





import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Thingspeak extends Activity {
	private Button mbtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thingspeak);

		mbtn=(Button)findViewById(R.id.button1);
		
		mbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_VIEW);
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		       intent.setData(Uri.parse("https://api.thingspeak.com/channels/"));
		        //intent.setData(Uri.parse("https://api.thingspeak.com/?login=vindievinodh@gmail.com&password=Mathvin1"));
		        startActivity(intent); 
				
			}
		});
		
		
	}

	
}
