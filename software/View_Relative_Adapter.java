package com.neonatal.monitoring.system;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class View_Relative_Adapter extends BaseAdapter {
	
	 private Context mContext;
	    private ArrayList<String> mArraylListId;
	    private ArrayList<String> mArraylListName;
	    private ArrayList<String> mArraylListStartTime;
	    private ArrayList<String> mArraylListDevice;
	    private ArrayList<String> mArraylListAction;
	    private LayoutInflater mLayoutInflater;

	    private static final String TAG = "Abhi MyAdapter";
	    
	    public View_Relative_Adapter(Context context, ArrayList<String> mArraylListId, ArrayList<String> mArraylListName,
	    		ArrayList<String> mArraylListStartTime, ArrayList<String> mArraylListDevice, ArrayList<String> mArraylListAction) {
	    	
	        this.mContext = context;
	        this.mArraylListId = mArraylListId;
	        this.mArraylListName = mArraylListName;
	        this.mArraylListStartTime = mArraylListStartTime;
	        this.mArraylListDevice = mArraylListDevice;
	        this.mArraylListAction = mArraylListAction;
	        mLayoutInflater = LayoutInflater.from(mContext);
	    }



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	 public void setmAimalList(ArrayList<String> mArraylListId, ArrayList<String> mArraylListName,
	    		ArrayList<String> mArraylListStartTime, ArrayList<String> mArraylListDevice, ArrayList<String> mArraylListAction) {
	        this.mArraylListId = mArraylListId;
	        this.mArraylListName = mArraylListName;
	        this.mArraylListStartTime = mArraylListStartTime;
	        this.mArraylListDevice = mArraylListDevice;
	        this.mArraylListAction = mArraylListAction;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        if (convertView == null){
	            convertView = mLayoutInflater.inflate(R.layout.item_layout, parent, false);
	        }
	        
	        TextView tv_tvid = (TextView)convertView.findViewById(R.id.tv_tvid);
	        TextView tv_tvname = (TextView)convertView.findViewById(R.id.tv_tvname);
	        TextView tv_tvStartTime = (TextView)convertView.findViewById(R.id.tv_tvStartTime);
	        TextView tv_tvDevice = (TextView)convertView.findViewById(R.id.tv_tvDevice);
	        TextView tv_tvAction = (TextView)convertView.findViewById(R.id.tv_tvAction);
	        
	        tv_tvid.setText(mArraylListId.get(position));
	        tv_tvname.setText(mArraylListName.get(position));
	        tv_tvStartTime.setText(mArraylListStartTime.get(position));
	        tv_tvDevice.setText(mArraylListDevice.get(position));
	        tv_tvAction.setText(mArraylListAction.get(position));
	        
	        Log.i(TAG, "getView: position "+position);
	        return convertView;
	    }

	}
