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

public class Beauty_AddAct extends Activity{

	private String TAG = Beauty_AddAct.class.getSimpleName(); 
	private ProgressDialog pDialog; 
	private EditText etJudul, etNmIsi; 
	private Button btnAdd; 
	   
	  @Override 
	  protected void onCreate(Bundle savedInstanceState) { 
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.forminput_beauty); 
	    
	    etJudul = (EditText) findViewById(R.id.etJudul); 
	    etNmIsi = (EditText) findViewById(R.id.etIsi); 
	     
	    btnAdd = (Button) findViewById(R.id.btnAdd); 
	    btnAdd.setOnClickListener(new OnClickListener() { 
	       
	      @Override 
	      public void onClick(View v) { 
	        new SendBeautys().execute(); 
	      } 
	    }); 
	     
	  } 
	   
	  /** 
	   * Async task class to send json  
	   */ 
	  private class SendBeautys extends AsyncTask<Void, Void, String> { 
		  
		  
	  
		    @Override 
		    protected void onPreExecute() { 
		      super.onPreExecute(); 
		      // Showing progress dialog 
		      pDialog = new ProgressDialog(Beauty_AddAct.this); 
		      pDialog.setMessage("Adding your data..."); 
		      pDialog.setCancelable(false); 
		      pDialog.show(); 
		 
		    } 
		     
		    @Override 
		    protected String doInBackground(Void... params) { 
		 
		      entity_beauty_talk beautytalk = new entity_beauty_talk();
		      beautytalk.setJudul(etJudul.getText().toString()); 
		      beautytalk.setIsi(etNmIsi.getText().toString()); 
		       
		      String result = ""; 
		       
		      try { 
		        beauty_HTTPHandler sj = new beauty_HTTPHandler(); 
		        JSONObject resObj = new JSONObject(sj.sendJson(beauty_HTTPHandler. MYURL +
		        		beauty_HTTPHandler.INSERT, beautytalk)); 
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
		        Toast.makeText(Beauty_AddAct.this, "entity_beauty added", 
		Toast.LENGTH_LONG).show(); 
		        Intent iMain = new Intent(Beauty_AddAct.this, MainBeautyAdmin.class); 
		        finish(); 
		        startActivity(iMain); 
		      } else if (result.equals(Static.FAIL)) { 
		        Toast.makeText(Beauty_AddAct.this, "Fail to add Beauty", 
		Toast.LENGTH_LONG).show(); 
		      } 
		       
		    } 
		   
		  }
	  
	  public void onBackPressed() {
		Intent i = new Intent(Beauty_AddAct.this, MainBeautyAdmin.class);
		finish();
		startActivity(i);
	}
		} 
