package com.atiya.projectuts;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Diy extends Activity{
	private String TAG = Main_Diy.class.getSimpleName();
	private ProgressDialog pDialog;
	private ListView listView;
	private TextView tvEmpty;
//	private Button btnNew;
//	private String[] arrayItem = { Static.UPDATE, Static.DELETE };
	ArrayList<entity_diy> listdiy = new ArrayList<entity_diy>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diy);
		listView = (ListView) findViewById(R.id.listDiy);
		tvEmpty = (TextView) findViewById(R.id.tvEmpty);
//		btnNew = (Button) findViewById(R.id.btnNew);
//		// Add new diyy
//		btnNew.setOnClickListener(new OnClickListener() {
//	
//			public void onClick(View v) {
//				Intent iNew = new Intent(Main_Diy.this, Diy_AddAct.class);
//				finish();
//				startActivity(iNew);
//			}
//		});
		// Load diys
		new GetDiy().execute();
		// Show dialog for Update/Delete diyy
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				final entity_diy diy = listdiy.get(position);
				Intent iEdit = new Intent(Main_Diy.this,detail_diy.class);
									iEdit.putExtra(Static.ID_Diy, diy.getIdDiy());
									iEdit.putExtra(Static.JUDUL,diy.getJudul());
									iEdit.putExtra(Static.ISI,diy.getIsi());
									 Main_Diy.this.finish();
									startActivity(iEdit);
								
			}
		});
	}

	/**
	 * Async task class to get json by making HTTP call
	 */
	private class GetDiy extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(Main_Diy.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			diy_HTTPHandler sh = new diy_HTTPHandler();
			// Making a request to url and getting response
			String jsonStr = sh.callJson();
			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					// Getting JSON Array node
					JSONArray diys = jsonObj.getJSONArray(Static.DIY);
					if (!diys.getJSONObject(0).equals(Static.EMPTY)) {
						// looping through All diys
						for (int i = 0; i < diys.length(); i++) {
							JSONObject c = diys.getJSONObject(i);
							entity_diy diy = new entity_diy();
							diy.setIdDiy(c.getString(Static.ID_Diy));
							diy.setJudul(c.getString(Static.JUDUL));
							diy.setIsi(c.getString(Static.ISI));
							// adding diyy to diyy list
							listdiy.add(diy);
						}
					}
				} catch (final JSONException e) {
					Log.e(TAG, "Json parsing error: " + e.getMessage());
				}
			} else {
				Log.e(TAG, "Couldn't get json from server.");
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(
								getApplicationContext(),
								"Couldn't get json from server. Check LogCat for possible errors!",
								Toast.LENGTH_LONG).show();
					}
				});
			}
			return null;
		}


		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			if (listdiy.size() > 0) {
				AdapterDiy adapter = new AdapterDiy(getApplicationContext(),listdiy);
				listView.setAdapter(adapter);
			} else {
				tvEmpty.setText("Table is Empty");
			}
		}
	}

	/**
	 * Async task class to send json
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_ut, menu);
		return true;
	}
	
	public void onBackPressed() {
		Intent i = new Intent(Main_Diy.this, Main_MenuUtama.class);
		finish();
		startActivity(i);
	}
}