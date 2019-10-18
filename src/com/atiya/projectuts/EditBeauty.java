package com.atiya.projectuts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditBeauty extends Activity{

	private String TAG = EditBeauty.class.getName(); 
	private ProgressDialog pDialog; 
	  private EditText etJudul, etIsi; 
	  private Button btnEdit; 
	  
	  String mIdBeauty;
	  String mJudul; 
	  String mIsi; 
	   
	  @Override 
	  protected void onCreate(Bundle savedInstanceState) { 
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.forminput_beauty); 
	     
	    etJudul = (EditText) findViewById(R.id.etJudul); 
	    etIsi = (EditText) findViewById(R.id.etIsi);
	     
	    Intent iEdit = getIntent();
	    mIdBeauty = iEdit.getStringExtra(Static.ID_BEAUTY);
	    
	    mJudul = iEdit.getStringExtra(Static.JUDUL1); 
	    mIsi = iEdit.getStringExtra(Static.ISI1); 
	     
	    etJudul.setText(mJudul); 
	    etIsi.setText(mIsi); 
	     
	    btnEdit = (Button) findViewById(R.id.btnAdd); 
	    btnEdit.setText("Update"); 
	    btnEdit.setOnClickListener(new OnClickListener() {
	    	
	      public void onClick(View v) { 
	        new SendBeauty().execute(mIdBeauty); 
	      }
	    }); 
	     
	  }
	      /** 
	       * Async task class to send json  
	       */ 
	      private class SendBeauty extends AsyncTask<String, Void, String> { 
	        @Override 
	        protected void onPreExecute() { 
	          super.onPreExecute(); 
	          // Showing progress dialog 
	          pDialog = new ProgressDialog(EditBeauty.this); 
	          pDialog.setMessage("Updating your data..."); 
	          pDialog.setCancelable(false); 
	          pDialog.show(); 
	        }
	        
	        @Override 
	        protected String doInBackground(String... params) { 
	          entity_beauty_talk beautytalk = new entity_beauty_talk(); 
	          beautytalk.setIdBeauty(mIdBeauty); 
	          beautytalk.setJudul(etJudul.getText().toString()); 
	          beautytalk.setIsi(etIsi.getText().toString()); 
	          
	          
	         String result = ""; 
	          
	         try { 
	           beauty_HTTPHandler sj = new beauty_HTTPHandler(); 
	           JSONObject resObj = new JSONObject(sj.sendJson(beauty_HTTPHandler. MYURL + 
	        		   beauty_HTTPHandler.UPDATE, beautytalk)); 
	           JSONArray resArr = resObj.getJSONArray(Static.POSTS); 
	           result = resArr.getString(0); 
	         } catch (JSONException e) { 
	           Log.i(TAG, "JSON parse error " + e.getMessage()); 
	         } 
	          
	         return result; 
	       }
	        
	        
	       @Override 
	       protected void onPostExecute(String result) { 
	         super.onPostExecute(result); 
	         // Dismiss the progress dialog 
	         if (pDialog.isShowing()) 
	           pDialog.dismiss(); 
	          
	         Log.d(TAG, result); 
	         /** 
	          * Show insert information 
	          * */ 
	         if (result.equals(Static.SUCCESS)) { 
	           Toast.makeText(EditBeauty.this, "Beauty updated", 
	    Toast.LENGTH_LONG).show(); 
	           Intent iMain = new Intent(EditBeauty.this, MainBeautyAdmin.class); 
	           finish(); 
	           startActivity(iMain); 
	         } else if (result.equals(Static.FAIL)) { 
	           Toast.makeText(EditBeauty.this, "Fail to update Beauty", 
	    Toast.LENGTH_LONG).show(); 
	         } 
	          
	       } 
	      
	     } 
	      public void onBackPressed() {
	    		Intent i = new Intent(EditBeauty.this, MainBeautyAdmin.class);
	    		finish();
	    		startActivity(i);
	    	}
	    } 
	    