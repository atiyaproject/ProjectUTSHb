package com.atiya.projectuts;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class about_us extends Activity{
	
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        
	}
	
        public void onBackPressed() {
    		Intent i = new Intent(about_us.this, Main_MenuUtama.class);
    		finish();
    		startActivity(i);
    	}
}
