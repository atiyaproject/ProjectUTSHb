package com.atiya.projectuts;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	String pasw ;
    String nama;
    String namaku ="admin";
    String pswd ="admin";
    EditText name;
    EditText pass,tampil;
  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        name = (EditText)findViewById(R.id.editname);
        pass = (EditText)findViewById(R.id.editpass);
        Button submit = (Button)findViewById(R.id.buttonlogin);
        submit.setOnClickListener(new click());
        }
        @SuppressLint("ShowToast")
        class click implements Button.OnClickListener{
        @SuppressLint("ShowToast")
        public void onClick(View v){
        nama = name.getText().toString();
        pasw = pass.getText().toString();
        if((pasw.equals(pswd))&&(nama.equals(namaku))){
               Toast.makeText(getApplicationContext(),"Selamat datang, anda berhasil login..." ,31).show();
               Intent i = new Intent(Login.this, MainAdmin_MenuUtama.class);
               startActivity(i);
        }else
               Toast.makeText(getApplicationContext(),"Maaf..., Username atau password salah",23).show();
               name.setText("");
               pass.setText("");
        }
        }
        
        public void onBackPressed() {
    		Intent i = new Intent(Login.this, menuawal_act.class);
    		finish();
    		startActivity(i);
    	}
        
        
}
