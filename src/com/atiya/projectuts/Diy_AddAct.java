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

public class Diy_AddAct extends Activity{

	private String TAG = Diy_AddAct.class.getSimpleName(); 
	   
	  private ProgressDialog pDialog; 
	  private EditText etJudul, etNmIsi; 
	  private Button btnAdd; 
	   
	  @Override 
	  protected void onCreate(Bundle savedInstanceState) { 
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.forminput_diy); 
	    
	    etJudul = (EditText) findViewById(R.id.etJudul); 
	    etNmIsi = (EditText) findViewById(R.id.etNmIsi); 
	     
	    btnAdd = (Button) findViewById(R.id.btnAdd); 
	    btnAdd.setOnClickListener(new OnClickListener() { 
	       
	      @Override 
	      public void onClick(View v) { 
	        new SendDiys().execute(); 
	      } 
	    }); 
	     
	  } 
	   
	  /** 
	   * Async task class to send json  
	   */ 
	  private class SendDiys extends AsyncTask<Void, Void, String> { 
		  
		  
	  
		    @Override 
		    protected void onPreExecute() { 
		      super.onPreExecute(); 
		      // Showing progress dialog 
		      pDialog = new ProgressDialog(Diy_AddAct.this); 
		      pDialog.setMessage("Adding your data..."); 
		      pDialog.setCancelable(false); 
		      pDialog.show(); 
		 
		    } 
		     
		    @Override 
		    protected String doInBackground(Void... params) { 
		 
		      entity_diy diy = new entity_diy();
		      diy.setJudul(etJudul.getText().toString()); 
		      diy.setIsi(etNmIsi.getText().toString()); 
		       
		      String result = ""; 
		       
		      try { 
		        diy_HTTPHandler sj = new diy_HTTPHandler(); 
		        JSONObject resObj = new JSONObject(sj.sendJson(diy_HTTPHandler. MYURL + 
		diy_HTTPHandler.INSERT, diy)); 
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
		        Toast.makeText(Diy_AddAct.this, "entity_diy added", 
		Toast.LENGTH_LONG).show(); 
		        Intent iMain = new Intent(Diy_AddAct.this, MainDiyAdmin.class); 
		        finish(); 
		        startActivity(iMain); 
		      } else if (result.equals(Static.FAIL)) { 
		        Toast.makeText(Diy_AddAct.this, "Fail to add diy", 
		Toast.LENGTH_LONG).show(); 
		      } 
		       
		    } 
		   
		  }
	  
	  public void onBackPressed() {
  		Intent i = new Intent(Diy_AddAct.this, MainDiyAdmin.class);
  		finish();
  		startActivity(i);
  	}
		} 
