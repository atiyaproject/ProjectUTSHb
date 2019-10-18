package com.atiya.projectuts;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;

public class MainAdmin_MenuUtama extends Activity {

	ImageButton btnadmin, btnph, btnbt, btncosmetic, btndiy, btnabus; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_uts);
		
	
		btnph = (ImageButton) findViewById(R.id.ph);
		btnph.setOnClickListener(new clickPh());
		btnbt = (ImageButton) findViewById(R.id.imagebt);
		btnbt.setOnClickListener(new clickBt());
		btncosmetic = (ImageButton) findViewById(R.id.imageCos);
		btncosmetic.setOnClickListener(new clickCosmetic());
		btndiy = (ImageButton) findViewById(R.id.imageDiy);
		btndiy.setOnClickListener(new clickDiy());
		btnabus = (ImageButton) findViewById(R.id.imageAbus);
		btnabus.setOnClickListener(new clickAboutus());
		
	}
	
	//class clickAdmin implements ImageButton.OnClickListener {
	//	public void onClick(View v) {
	//		Intent i = new Intent(Main_MenuUtama.this, Login.class);
		//	finish();
		//	startActivity(i);
	//	}
	//}

	class clickPh implements ImageButton.OnClickListener {
		public void onClick(View v) {
			Intent i = new Intent(MainAdmin_MenuUtama.this, MainProdHalalAdmin.class);
			finish();
			startActivity(i);
		}
	}

	class clickBt implements ImageButton.OnClickListener {
		public void onClick(View v) {
			Intent i = new Intent(MainAdmin_MenuUtama.this, MainBeautyAdmin.class);
			finish();
			startActivity(i);
		}
	}

	class clickCosmetic implements ImageButton.OnClickListener {
		public void onClick(View v) {
			Intent i = new Intent(MainAdmin_MenuUtama.this, MainAdmin_RinCos.class);
			finish();
			startActivity(i);
		}
	}
	
	class clickDiy implements ImageButton.OnClickListener {
		public void onClick(View v) {
			Intent i = new Intent(MainAdmin_MenuUtama.this, MainDiyAdmin.class);
			finish();
			startActivity(i);
		}
	}

	class clickAboutus implements ImageButton.OnClickListener {
		public void onClick(View v) {
			Intent i = new Intent(MainAdmin_MenuUtama.this, about_us.class);
			finish();
			startActivity(i);
		}
	}

	public void onBackPressed() {
		Intent i = new Intent(MainAdmin_MenuUtama.this, menuawal_act.class);
		finish();
		startActivity(i);
	}
	
	
}
