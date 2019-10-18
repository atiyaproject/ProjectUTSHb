package com.atiya.projectuts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class detail_beauty extends Activity{
	TextView judulbeauty, isibeauty;
	String mJudul, mIsi;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.detail_beautytalk);
			judulbeauty = (TextView) findViewById(R.id.judulbeauty);
			isibeauty = (TextView) findViewById(R.id.isibeauty);
			
			
			Intent iEdit = getIntent();
			mJudul = iEdit.getStringExtra(Static.JUDUL1);
			mIsi = iEdit.getStringExtra(Static.ISI1);
			
			judulbeauty.setText(mJudul);
			isibeauty.setText(mIsi);
	}
			public void onBackPressed() {
				Intent i = new Intent(detail_beauty.this, Main_Beauty.class);
				finish();
				startActivity(i);
			}
			
}			