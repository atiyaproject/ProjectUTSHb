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

public class EditProdHalal extends Activity{

	private String TAG = EditProdHalal.class.getName(); 
	   
	  private ProgressDialog pDialog; 
	  private EditText etId, etName, etBrand, etMB; 
	  private Button btnEdit; 
	   
	  String mId; 
	  String mName; 
	  String mBrand; 
	  String mMB; 
	   
	  @Override 
	  protected void onCreate(Bundle savedInstanceState) { 
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.forminput_prodhalal); 
	     
	    etId = (EditText) findViewById(R.id.etID);
	    etName = (EditText) findViewById(R.id.etNP); 
	    etBrand = (EditText) findViewById(R.id.etNmBrand); 
	    etMB = (EditText) findViewById(R.id.etTgl); 
	     
	    Intent iEdit = getIntent(); 
	    mId = iEdit.getStringExtra(Static.ID_SERTIFIKAT); 
	    mName = iEdit.getStringExtra(Static.NAME); 
	    mBrand = iEdit.getStringExtra(Static.BRAND); 
	    mMB = iEdit.getStringExtra(Static.MASA); 
	     
	    etId.setText(mId);
	    etName.setText(mName); 
	    etBrand.setText(mBrand); 
	    etMB.setText(mMB); 
	     
	    btnEdit = (Button) findViewById(R.id.btnAdd); 
	    btnEdit.setText("Update"); 
	    btnEdit.setOnClickListener(new OnClickListener() {
	    	
	      public void onClick(View v) { 
	        new SendProdHalal().execute(mId); 
	      }
	    }); 
	     
	  }
	      /** 
	       * Async task class to send json  
	       */ 
	      private class SendProdHalal extends AsyncTask<String, Void, String> { 
	        @Override 
	        protected void onPreExecute() { 
	          super.onPreExecute(); 
	          // Showing progress dialog 
	          pDialog = new ProgressDialog(EditProdHalal.this); 
	          pDialog.setMessage("Updating your data..."); 
	          pDialog.setCancelable(false); 
	          pDialog.show(); 
	        }
	        
	        @Override 
	        protected String doInBackground(String... params) { 
	          entity_produk_halal produk_halal = new entity_produk_halal(); 
	          produk_halal.setId(mId); 
	          produk_halal.setName(etName.getText().toString()); 
	          produk_halal.setBrand(etBrand.getText().toString()); 
	          produk_halal.setMasaberlaku(etMB.getText().toString());
	          
	          
	         String result = ""; 
	          
	         try { 
	           prodhalal_HTTPHandler sj = new prodhalal_HTTPHandler(); 
	           JSONObject resObj = new JSONObject(sj.sendJson(prodhalal_HTTPHandler. MYURL + 
	    prodhalal_HTTPHandler.UPDATE, produk_halal)); 
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
	           Toast.makeText(EditProdHalal.this, "produk_halal updated", 
	    Toast.LENGTH_LONG).show(); 
	           Intent iMain = new Intent(EditProdHalal.this, MainProdHalalAdmin.class); 
	           finish(); 
	           startActivity(iMain); 
	         } else if (result.equals(Static.FAIL)) { 
	           Toast.makeText(EditProdHalal.this, "Fail to update produk_halal", 
	    Toast.LENGTH_LONG).show(); 
	         } 
	          
	       } 
	      
	     } 
	      public void onBackPressed() {
	    		Intent i = new Intent(EditProdHalal.this, MainProdHalalAdmin.class);
	    		finish();
	    		startActivity(i);
	    	}
	    } 
	      