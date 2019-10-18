package com.atiya.projectuts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;

public class MainAdmin_RinCos extends Activity {

	Button btnvegan, btnhalal; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.isi_cosmetic);
		
		//btnadmin = (ImageButton) findViewById(R.id.imgadmin);
	//	btnadmin.setOnClickListener(new clickAdmin());
		btnvegan = (Button) findViewById(R.id.buttonhalal);
		btnvegan.setOnClickListener(new clickVegan());
		btnhalal = (Button) findViewById(R.id.buttonvegan);
		btnhalal.setOnClickListener(new clickHalal());
		
	}
	
	//class clickAdmin implements ImageButton.OnClickListener {
	//	public void onClick(View v) {
	//		Intent i = new Intent(Main_MenuUtama.this, Login.class);
		//	finish();
		//	startActivity(i);
	//	}
	//}

	class clickVegan implements Button.OnClickListener {
		public void onClick(View v) {
			Intent i = new Intent(MainAdmin_RinCos.this, MainVeganAdmin.class);
			finish();
			startActivity(i);
		}
	}

	class clickHalal implements Button.OnClickListener {
		public void onClick(View v) {
			Intent i = new Intent(MainAdmin_RinCos.this, MainHalalAdmin.class);
			finish();
			startActivity(i);
		}
	}

	
	public void onBackPressed() {
		Intent i = new Intent(MainAdmin_RinCos.this, MainAdmin_MenuUtama.class);
		finish();
		startActivity(i);
	}
	
	
}
