package com.atiya.projectuts;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

 
public class AdapterProdHalal extends BaseAdapter { 
   
  private static ArrayList<entity_produk_halal> listProdHalal; 
  private LayoutInflater mInflater; 
   
  public AdapterProdHalal(Context context, ArrayList<entity_produk_halal> con) { 
	  listProdHalal = con; 
    mInflater = LayoutInflater.from(context); 
  } 
   
  @Override 
  public int getCount() { 
    return listProdHalal.size(); 
  } 
 
  @Override 
  public Object getItem(int position) { 
    return listProdHalal.get(position); 
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
     convertView = mInflater.inflate(R.layout.listitem_prodhalal, null); 
     mHolder = new ViewHolder();
     mHolder.tvID = (TextView) convertView.findViewById(R.id.tvID); 
     mHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
     mHolder.tvBrand = (TextView) convertView.findViewById(R.id.tvBrand);
     mHolder.tvMasaBerlaku = (TextView) convertView.findViewById(R.id.tvMasaBerlaku);
     convertView.setTag(mHolder); 
   } else { 
     mHolder = (ViewHolder)convertView.getTag(); 
   } 
    
   // set view content
   mHolder.tvID.setText(listProdHalal.get(position).getId()); 
   mHolder.tvName.setText(listProdHalal.get(position).getName()); 
   mHolder.tvBrand.setText(listProdHalal.get(position).getBrand()); 
   mHolder.tvMasaBerlaku.setText(listProdHalal.get(position).getMasaberlaku()); 
   return convertView; 
 } 
  
 static class ViewHolder {
   TextView tvID; 
   TextView tvName; 
   TextView tvBrand; 
   TextView tvMasaBerlaku;
 } 

} 