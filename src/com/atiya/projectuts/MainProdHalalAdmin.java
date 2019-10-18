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

public class MainProdHalalAdmin extends Activity{
	
	private String TAG = MainProdHalalAdmin.class.getSimpleName();
	
	private ProgressDialog pDialog;
	private ListView listView;
	private TextView tvEmpty;
	private Button btnNew;
	private String[] arrayItem = { Static.UPDATE, Static.DELETE };
	ArrayList<entity_produk_halal> listprodhalal = new ArrayList<entity_produk_halal>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_produk_halal);
		listView = (ListView) findViewById(R.id.listProdHalal);
		tvEmpty = (TextView) findViewById(R.id.tvEmpty);
		btnNew = (Button) findViewById(R.id.btnNew);
		// Add new berita
		btnNew.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				Intent iNew = new Intent(MainProdHalalAdmin.this, prodhalal_AddAct.class);
				finish();
				startActivity(iNew);
			}
		});
		// Load produks
		new GetProdHal().execute();
		// Show dialog for Update/Delete berita
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				AlertDialog.Builder dialogItem = new AlertDialog.Builder(
						MainProdHalalAdmin.this);
				final entity_produk_halal produkhalal = listprodhalal.get(position);
				dialogItem.setTitle(produkhalal.getName());
				final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainProdHalalAdmin.this, android.R.layout.simple_list_item_1,
						arrayItem);
				dialogItem.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int positionDialog) {
								if (adapter.getItem(positionDialog).equals(Static.UPDATE)) {
									Intent iEdit = new Intent(MainProdHalalAdmin.this,EditProdHalal.class);
									iEdit.putExtra(Static.ID_SERTIFIKAT, produkhalal.getId());
									iEdit.putExtra(Static.NAME,produkhalal.getName());
									iEdit.putExtra(Static.BRAND,produkhalal.getBrand());
									iEdit.putExtra(Static.MASA,produkhalal.getMasaberlaku());
									 MainProdHalalAdmin.this.finish();
									startActivity(iEdit);
								} else {
									new Deleteprodukhalal().execute(produkhalal);
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
	private class GetProdHal extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainProdHalalAdmin.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			prodhalal_HTTPHandler sh = new prodhalal_HTTPHandler();
			// Making a request to url and getting response
			String jsonStr = sh.callJson();
			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					// Getting JSON Array node
					JSONArray produks = jsonObj.getJSONArray(Static.PRODUKHALAL);
					if (!produks.getJSONObject(0).equals(Static.EMPTY)) {
						// looping through All produks
						for (int i = 0; i < produks.length(); i++) {
							JSONObject c = produks.getJSONObject(i);
							entity_produk_halal produkhalal = new entity_produk_halal();
							produkhalal.setId(c.getString(Static.ID_SERTIFIKAT));
							produkhalal.setName(c.getString(Static.NAME));
							produkhalal.setBrand(c.getString(Static.BRAND));
							produkhalal.setMasaberlaku(c.getString(Static.MASA));
							// adding berita to berita list
							listprodhalal.add(produkhalal);
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
			if (listprodhalal.size() > 0) {
				AdapterProdHalal adapter = new AdapterProdHalal(getApplicationContext(),listprodhalal);
				listView.setAdapter(adapter);
			} else {
				tvEmpty.setText("Table is Empty");
			}
		}
	}

	/**
	 * Async task class to send json
	 */
	private class Deleteprodukhalal extends AsyncTask<entity_produk_halal, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainProdHalalAdmin.this);
			pDialog.setMessage("Deleting a data...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(entity_produk_halal... produks) {
			entity_produk_halal produkhalal = produks[0];
			String result = "";
			try {
				prodhalal_HTTPHandler sj = new prodhalal_HTTPHandler();
				JSONObject resObj = new JSONObject(sj.sendJson(prodhalal_HTTPHandler.MYURL
						+ prodhalal_HTTPHandler.DELETE, produkhalal));
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
				Toast.makeText(MainProdHalalAdmin.this, "Produk deleted",
						Toast.LENGTH_LONG).show();
				listprodhalal.clear();
				new GetProdHal().execute();
			} else if (result.equals(Static.FAIL)) {

				Toast.makeText(MainProdHalalAdmin.this, "Fail to delete berita",
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
		Intent i = new Intent(MainProdHalalAdmin.this, MainAdmin_MenuUtama.class);
		finish();
		startActivity(i);
	}
}