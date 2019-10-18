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

public class EditDiy extends Activity{

	private String TAG = EditDiy.class.getName(); 
	   
	  private ProgressDialog pDialog; 
	  private EditText etJudul, etIsi; 
	  private Button btnEdit; 
	  
	  String mIdDiy;
	  String mJudul; 
	  String mIsi; 
	   
	  @Override 
	  protected void onCreate(Bundle savedInstanceState) { 
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.forminput_diy); 
	     
	    etJudul = (EditText) findViewById(R.id.etJudul); 
	    etIsi = (EditText) findViewById(R.id.etNmIsi);
	     
	    Intent iEdit = getIntent();
	    mIdDiy = iEdit.getStringExtra(Static.ID_Diy);
	    
	    mJudul = iEdit.getStringExtra(Static.JUDUL); 
	    mIsi = iEdit.getStringExtra(Static.ISI); 
	     
	    etJudul.setText(mJudul); 
	    etIsi.setText(mIsi); 
	     
	    btnEdit = (Button) findViewById(R.id.btnAdd); 
	    btnEdit.setText("Update"); 
	    btnEdit.setOnClickListener(new OnClickListener() {
	    	
	      public void onClick(View v) { 
	        new SendDiy().execute(mIdDiy); 
	      }
	    }); 
	     
	  }
	      /** 
	       * Async task class to send json  
	       */ 
	      private class SendDiy extends AsyncTask<String, Void, String> { 
	        @Override 
	        protected void onPreExecute() { 
	          super.onPreExecute(); 
	          // Showing progress dialog 
	          pDialog = new ProgressDialog(EditDiy.this); 
	          pDialog.setMessage("Updating your data..."); 
	          pDialog.setCancelable(false); 
	          pDialog.show(); 
	        }
	        
	        @Override 
	        protected String doInBackground(String... params) { 
	          entity_diy diy = new entity_diy(); 
	          diy.setIdDiy(mIdDiy); 
	          diy.setJudul(etJudul.getText().toString()); 
	          diy.setIsi(etIsi.getText().toString()); 
	          
	          
	         String result = ""; 
	          
	         try { 
	           diy_HTTPHandler sj = new diy_HTTPHandler(); 
	           JSONObject resObj = new JSONObject(sj.sendJson(diy_HTTPHandler. MYURL + 
	    diy_HTTPHandler.UPDATE, diy)); 
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
	           Toast.makeText(EditDiy.this, "diy updated", 
	    Toast.LENGTH_LONG).show(); 
	           Intent iMain = new Intent(EditDiy.this, MainDiyAdmin.class); 
	           finish(); 
	           startActivity(iMain); 
	         } else if (result.equals(Static.FAIL)) { 
	           Toast.makeText(EditDiy.this, "Fail to update diy", 
	    Toast.LENGTH_LONG).show(); 
	         } 
	          
	       } 
	      
	     } 
	      public void onBackPressed() {
	    		Intent i = new Intent(EditDiy.this, MainDiyAdmin.class);
	    		finish();
	    		startActivity(i);
	    	}
	    } 
	      