package com.neonatal.monitoring.system;


import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_activity extends Activity {
	Button btnlogin,btnclear,btnregister;
	EditText txtusername,txtpassword;
	
	
	
     SQLiteDatabase database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_activity);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
  		StrictMode.setThreadPolicy(policy); 
		try
		{
			btnlogin=(Button)findViewById(R.id.btnlogin);
			btnclear=(Button)findViewById(R.id.btncanel);
			
			txtusername=(EditText)findViewById(R.id.txtusername);
		    txtpassword=(EditText)findViewById(R.id.txtpassword);
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	
	public void login(View V)
	{
		String uid=txtusername .getText().toString();
		String upass=txtpassword.getText().toString();
		
		if(uid.equals(""))
		{
			Toast.makeText(getApplicationContext(), "Please Enter User Name!!!!", Toast.LENGTH_LONG).show();
		}
		if(upass.equals(""))
		{
			Toast.makeText(getApplicationContext(), "Please Enter Password!!!!", Toast.LENGTH_LONG).show();
		}
		
		else {
			if(uid.equals("admin") && upass.equals("admin"))
			{
				Toast.makeText(getApplicationContext(), "Loged In Successfully......", Toast.LENGTH_LONG).show();
				Intent i=new Intent(getApplicationContext(), MainActivity.class);
				//Intent i=new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
			}
			else {
				Toast.makeText(getApplicationContext(), "User name or password is not maching!!!!", Toast.LENGTH_LONG).show();
			}
		}
			
 }
	

	
 public void clear(View V)
 {
	
		txtpassword.setText("");
		txtusername.setText("");
		Toast.makeText(getApplicationContext(), "data is cleared", Toast.LENGTH_LONG).show();
	 
 }




}






