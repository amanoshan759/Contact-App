package com.developtech.contactappwithsqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                    SharedPreferences sp = getSharedPreferences("spdata", MODE_PRIVATE);
                    SharedPreferences.Editor e = sp.edit();
                    if (sp.contains("Login")) {
                        Intent i = new Intent(SplashActivity.this, ContactActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                    e.clear();

                    SplashActivity.this.finish();
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        };
        t.start();

    }
}
