package com.marcosilv7.narutodelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private long splashDelay = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.activity_splash);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(userRegistred()) {
                    Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }else{
                    Intent mainIntent = new Intent().setClass(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                }
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, splashDelay);
    }

    private boolean userRegistred(){
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return false;
    }
}
