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

public class prodhalal_AddAct extends Activity{

	private String TAG = prodhalal_AddAct.class.getSimpleName(); 
	   
	  private ProgressDialog pDialog; 
	  private EditText etId, etName, etBrand, etTB; 
	  private Button btnAdd; 
	   
	  @Override 
	  protected void onCreate(Bundle savedInstanceState) { 
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.forminput_prodhalal); 
	    
	    etId = (EditText) findViewById(R.id.etID);
	    etName = (EditText) findViewById(R.id.etNP); 
	    etBrand = (EditText) findViewById(R.id.etNmBrand); 
	    etTB = (EditText) findViewById(R.id.etTgl); 
	     
	    btnAdd = (Button) findViewById(R.id.btnAdd); 
	    btnAdd.setOnClickListener(new OnClickListener() { 
	       
	      @Override 
	      public void onClick(View v) { 
	        new SendProducts().execute(); 
	      } 
	    }); 
	     
	  } 
	   
	  /** 
	   * Async task class to send json  
	   */ 
	  private class SendProducts extends AsyncTask<Void, Void, String> { 
		  
		  
	  
		    @Override 
		    protected void onPreExecute() { 
		      super.onPreExecute(); 
		      // Showing progress dialog 
		      pDialog = new ProgressDialog(prodhalal_AddAct.this); 
		      pDialog.setMessage("Adding your data..."); 
		      pDialog.setCancelable(false); 
		      pDialog.show(); 
		 
		    } 
		     
		    @Override 
		    protected String doInBackground(Void... params) { 
		 
		      entity_produk_halal produkhalal = new entity_produk_halal();
		      produkhalal.setId(etId.getText().toString());
		      produkhalal.setName(etName.getText().toString()); 
		      produkhalal.setBrand(etBrand.getText().toString()); 
		      produkhalal.setMasaberlaku(etTB.getText().toString()); 
		       
		      String result = ""; 
		       
		      try { 
		        prodhalal_HTTPHandler sj = new prodhalal_HTTPHandler(); 
		        JSONObject resObj = new JSONObject(sj.sendJson(prodhalal_HTTPHandler. MYURL + 
		prodhalal_HTTPHandler.INSERT, produkhalal)); 
		        Log.e(TAG, "Kesalahan : " + resObj); 
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
		        Toast.makeText(prodhalal_AddAct.this, "entity_produk_halal added", 
		Toast.LENGTH_LONG).show(); 
		        Intent iMain = new Intent(prodhalal_AddAct.this, MainProdHalalAdmin.class); 
		        finish(); 
		        startActivity(iMain); 
		      } else if (result.equals(Static.FAIL)) { 
		        Toast.makeText(prodhalal_AddAct.this, "Fail to add produkhalal", 
		Toast.LENGTH_LONG).show(); 
		      } 
		       
		    } 
		   
		  }
	  
	  public void onBackPressed() {
  		Intent i = new Intent(prodhalal_AddAct.this, MainProdHalalAdmin.class);
  		finish();
  		startActivity(i);
  	}
		} 
