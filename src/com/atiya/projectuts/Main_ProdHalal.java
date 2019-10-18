package com.atiya.projectuts;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main_ProdHalal extends Activity{
	
	private String TAG = Main_ProdHalal.class.getSimpleName();
	
	private ProgressDialog pDialog;
	private ListView listView;
	private TextView tvEmpty;
	//private Button btnNew;
	private String[] arrayItem = { Static.UPDATE, Static.DELETE };
	ArrayList<entity_produk_halal> listprodhalal = new ArrayList<entity_produk_halal>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.produk_halal);
		
		listView = (ListView) findViewById(R.id.listProdHalal);
		tvEmpty = (TextView) findViewById(R.id.tvEmpty);
//		btnNew = (Button) findViewById(R.id.btnNew);
//		Add new berita
//		btnNew.setOnClickListener(new OnClickListener() {
//	
//			public void onClick(View v) {
//				Intent iNew = new Intent(Main_ProdHalal.this, prodhalal_AddAct.class);
//				finish();
//				startActivity(iNew);
//			}
//		});
		
		// Load produks
		new GetProdHal().execute();
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
				final entity_produk_halal produkhalal = listprodhalal.get(position);
				
								
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
			pDialog = new ProgressDialog(Main_ProdHalal.this);
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
				//tvEmpty.setText("Table is Empty");
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_ut, menu);
		return true;
	}
	
	public void onBackPressed() {
		Intent i = new Intent(Main_ProdHalal.this, Main_MenuUtama.class);
		finish();
		startActivity(i);
	}
}