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

public class MainDiyAdmin extends Activity{
	private String TAG = MainDiyAdmin.class.getSimpleName();
	private ProgressDialog pDialog;
	private ListView listView;
	private TextView tvEmpty;
	private Button btnNew;
	private String[] arrayItem = { Static.UPDATE, Static.DELETE };
	ArrayList<entity_diy> listdiy = new ArrayList<entity_diy>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_diy);
		listView = (ListView) findViewById(R.id.listDiy);
		tvEmpty = (TextView) findViewById(R.id.tvEmpty);
		btnNew = (Button) findViewById(R.id.btnNew);
		// Add new diyy
		btnNew.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				Intent iNew = new Intent(MainDiyAdmin.this, Diy_AddAct.class);
				finish();
				startActivity(iNew);
			}
		});
		// Load diys
		new GetDiy().execute();
		// Show dialog for Update/Delete diyy
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				AlertDialog.Builder dialogItem = new AlertDialog.Builder(
						MainDiyAdmin.this);
				final entity_diy diy = listdiy.get(position);
				dialogItem.setTitle(diy.getJudul());
				final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainDiyAdmin.this, android.R.layout.simple_list_item_1,
						arrayItem);
				dialogItem.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int positionDialog) {
								if (adapter.getItem(positionDialog).equals(Static.UPDATE)) {
									Intent iEdit = new Intent(MainDiyAdmin.this,EditDiy.class);
									iEdit.putExtra(Static.ID_Diy, diy.getIdDiy());
									iEdit.putExtra(Static.JUDUL,diy.getJudul());
									iEdit.putExtra(Static.ISI,diy.getIsi());
									 MainDiyAdmin.this.finish();
									startActivity(iEdit);
								} else {
									new Deletediy().execute(diy);
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
	private class GetDiy extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainDiyAdmin.this);
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
	private class Deletediy extends AsyncTask<entity_diy, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainDiyAdmin.this);
			pDialog.setMessage("Deleting a data...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(entity_diy... diys) {
			entity_diy diy = diys[0];
			String result = "";
			try {
				diy_HTTPHandler sj = new diy_HTTPHandler();
				JSONObject resObj = new JSONObject(sj.sendJson(diy_HTTPHandler.MYURL
						+ diy_HTTPHandler.DELETE, diy));
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
				Toast.makeText(MainDiyAdmin.this, "Diy deleted",
						Toast.LENGTH_LONG).show();
				listdiy.clear();
				new GetDiy().execute();
			} else if (result.equals(Static.FAIL)) {

				Toast.makeText(MainDiyAdmin.this, "Fail to delete diyy",
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
		Intent i = new Intent(MainDiyAdmin.this, MainAdmin_MenuUtama.class);
		finish();
		startActivity(i);
	}
}