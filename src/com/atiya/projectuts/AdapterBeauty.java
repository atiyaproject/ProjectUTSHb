package com.atiya.projectuts;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

 
public class AdapterBeauty extends BaseAdapter { 
	private static ArrayList<entity_beauty_talk> listBeauty; 
	  private LayoutInflater mInflater; 
	   
	  public AdapterBeauty(Context context, ArrayList<entity_beauty_talk> con) { 
		listBeauty = con; 
	    mInflater = LayoutInflater.from(context); 
	  } 
	   
	  @Override 
	  public int getCount() { 
	    return listBeauty.size(); 
	  } 
	 
	  @Override 
	  public Object getItem(int position) { 
	    return listBeauty.get(position); 
	  } 
	 
	  @Override 
	  public long getItemId(int position) { 
	    return position; 
	  } 
	 
	  @Override 
	  public View getView(int position, View convertView, ViewGroup parent) { 
	    ViewHolder mHolder;

	    
	   // Initiate view holder 
	   if (convertView == null) { 
	     convertView = mInflater.inflate(R.layout.listitem_beauty, null); 
	     mHolder = new ViewHolder();
	     mHolder.tvJudul = (TextView) convertView.findViewById(R.id.tvJudul); 
	     mHolder.tvIsi = (TextView) convertView.findViewById(R.id.tvIsi); 
	     convertView.setTag(mHolder); 
	   } else { 
	     mHolder = (ViewHolder)convertView.getTag(); 
	   } 
	    
	   // set view content
	 
	   mHolder.tvJudul.setText(listBeauty.get(position).getJudul()); 
	   mHolder.tvIsi.setText(listBeauty.get(position).getIsi()); 
	   return convertView; 
	 } 
	  
	 static class ViewHolder {

	   TextView tvJudul; 
	   TextView tvIsi;
	 } 

	} 