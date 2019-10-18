package com.atiya.projectuts;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;

public class menuawal_act extends Activity {

ImageButton imguser, imgadmin;

protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_awal);
		
		imguser = (ImageButton) findViewById(R.id.imguser);
		imguser.setOnClickListener(new clickuser());
//		btnbt = (ImageButton) findViewById(R.id.imagebt);
//		btnbt.setOnClickListener(new clickBt());
		imgadmin = (ImageButton) findViewById(R.id.imgadmin);
		imgadmin.setOnClickListener(new clickadmin());
}

class clickuser implements ImageButton.OnClickListener {
	public void onClick(View v) {
		Intent i = new Intent(menuawal_act.this, Main_MenuUtama.class);
		finish();
		startActivity(i);
	}
}

class clickadmin implements ImageButton.OnClickListener {
	public void onClick(View v) {
		Intent i = new Intent(menuawal_act.this, Login.class);
		finish();
		startActivity(i);
	}
}

}
