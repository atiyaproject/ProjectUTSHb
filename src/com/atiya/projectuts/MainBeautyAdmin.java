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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainBeautyAdmin extends Activity{
	private String TAG = MainBeautyAdmin.class.getSimpleName();
	private ProgressDialog pDialog;
	private ListView listView;
	private TextView tvEmpty;
	private Button btnNew;
	private String[] arrayItem = { Static.UPDATE, Static.DELETE };
	ArrayList<entity_beauty_talk> listbeauty = new ArrayList<entity_beauty_talk>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_beautytalk);
		listView = (ListView) findViewById(R.id.listBeauty);
		tvEmpty = (TextView) findViewById(R.id.tvEmpty);
		btnNew = (Button) findViewById(R.id.btnNew);
		// Add new beautytalk
		btnNew.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				Intent iNew = new Intent(MainBeautyAdmin.this, Beauty_AddAct.class);
				finish();
				startActivity(iNew);
			}
		});
		// Load beautytalks
		new GetBeauty().execute();
		// Show dialog for Update/Delete beautytalk
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				AlertDialog.Builder dialogItem = new AlertDialog.Builder(
						MainBeautyAdmin.this);
				final entity_beauty_talk beautytalk = listbeauty.get(position);
				dialogItem.setTitle(beautytalk.getJudul());
				final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainBeautyAdmin.this, android.R.layout.simple_list_item_1,
						arrayItem);
				dialogItem.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int positionDialog) {
								if (adapter.getItem(positionDialog).equals(Static.UPDATE)) {
									Intent iEdit = new Intent(MainBeautyAdmin.this,EditBeauty.class);
									iEdit.putExtra(Static.ID_BEAUTY, beautytalk.getIdBeauty());
									iEdit.putExtra(Static.JUDUL1,beautytalk.getJudul());
									iEdit.putExtra(Static.ISI1,beautytalk.getIsi());
									 MainBeautyAdmin.this.finish();
									startActivity(iEdit);
								} else {
									new Deletebeautytalk().execute(beautytalk);
								}
							}
						}).show();
				return false;
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
			pDialog = new ProgressDialog(MainBeautyAdmin.this);
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
	private class Deletebeautytalk extends AsyncTask<entity_beauty_talk, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainBeautyAdmin.this);
			pDialog.setMessage("Deleting a data...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(entity_beauty_talk... beautytalks) {
			entity_beauty_talk beautytalk = beautytalks[0];
			String result = "";
			try {
				beauty_HTTPHandler sj = new beauty_HTTPHandler();
				JSONObject resObj = new JSONObject(sj.sendJson(beauty_HTTPHandler.MYURL
						+ beauty_HTTPHandler.DELETE, beautytalk));
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
				Toast.makeText(MainBeautyAdmin.this, "BeautyTalk deleted",
						Toast.LENGTH_LONG).show();
				listbeauty.clear();
				new GetBeauty().execute();
			} else if (result.equals(Static.FAIL)) {

				Toast.makeText(MainBeautyAdmin.this, "Fail to delete beautytalk",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_ut, menu);
		return true;
	}
	
	public void onBackPressed() {
		Intent i = new Intent(MainBeautyAdmin.this, MainAdmin_MenuUtama.class);
		finish();
		startActivity(i);
	}
}