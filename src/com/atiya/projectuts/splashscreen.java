package com.atiya.projectuts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashscreen extends Activity {

private static int Durasi = 4000;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.splashscreen);

new Handler().postDelayed(new Runnable() {
@Override
public void run() {
Intent i = new Intent(splashscreen.this, menuawal_act.class);
startActivity(i);
finish();
}
}, Durasi);
}
}
