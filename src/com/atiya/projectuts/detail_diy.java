package com.atiya.projectuts;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class detail_diy extends Activity{
	TextView juduldiy, isidiy;
	String mJudul, mIsi;


	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.detail_diy);
			juduldiy = (TextView) findViewById(R.id.juduldiy);
			isidiy = (TextView) findViewById(R.id.isidiy);
			
			
			Intent iEdit = getIntent();
			mJudul = iEdit.getStringExtra(Static.JUDUL);
			mIsi = iEdit.getStringExtra(Static.ISI);
			
			juduldiy.setText(mJudul);
			isidiy.setText(mIsi);
	}
			public void onBackPressed() {
				Intent i = new Intent(detail_diy.this, Main_Diy.class);
				finish();
				startActivity(i);
			}
			
}

		
	