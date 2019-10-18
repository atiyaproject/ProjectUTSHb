package com.atiya.projectuts;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Beauty extends Activity{
	private String TAG = Main_Beauty.class.getSimpleName();
	private ProgressDialog pDialog;
	private ListView listView;
	private TextView tvEmpty;
	private Button btnNew;
	private String[] arrayItem = { Static.UPDATE, Static.DELETE };
	ArrayList<entity_beauty_talk> listbeauty = new ArrayList<entity_beauty_talk>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beautytalk);
		listView = (ListView) findViewById(R.id.listBeauty);
		tvEmpty = (TextView) findViewById(R.id.tvEmpty);
//		btnNew = (Button) findViewById(R.id.btnNew);
//		// Add new beautytalk
//		btnNew.setOnClickListener(new OnClickListener() {
//	
//			public void onClick(View v) {
//				Intent iNew = new Intent(Main_Beauty.this, Beauty_AddAct.class);
//				finish();
//				startActivity(iNew);
//			}
//		});
		// Load beautytalks
		new GetBeauty().execute();
		// Show dialog for Update/Delete beautytalk
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				final entity_beauty_talk beautytalk = listbeauty.get(position);
									Intent iEdit = new Intent(Main_Beauty.this,detail_beauty.class);
									iEdit.putExtra(Static.ID_BEAUTY, beautytalk.getIdBeauty());
									iEdit.putExtra(Static.JUDUL1,beautytalk.getJudul());
									iEdit.putExtra(Static.ISI1,beautytalk.getIsi());
									 Main_Beauty.this.finish();
									startActivity(iEdit);
								
			}
		});
	}

	/**
	 * Async task class to get json by making HTTP call
	 */
	private class GetBeauty extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(Main_Beauty.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			beauty_HTTPHandler sh = new beauty_HTTPHandler();
			// Making a request to url and getting response
			String jsonStr = sh.callJson();
			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					// Getting JSON Array node
					JSONArray beautytalks = jsonObj.getJSONArray(Static.BEAUTY);
					if (!beautytalks.getJSONObject(0).equals(Static.EMPTY)) {
						// looping through All beautytalks
						for (int i = 0; i < beautytalks.length(); i++) {
							JSONObject c = beautytalks.getJSONObject(i);
							entity_beauty_talk beautytalk = new entity_beauty_talk();
							beautytalk.setIdBeauty(c.getString(Static.ID_BEAUTY));
							beautytalk.setJudul(c.getString(Static.JUDUL1));
							beautytalk.setIsi(c.getString(Static.ISI1));
							// adding beautytalk to beautytalk list
							listbeauty.add(beautytalk);
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
			if (listbeauty.size() > 0) {
				AdapterBeauty adapter = new AdapterBeauty(getApplicationContext(),listbeauty);
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
		Intent i = new Intent(Main_Beauty.this, Main_MenuUtama.class);
		finish();
		startActivity(i);
	}
}