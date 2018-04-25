package com.marcosilv7.narutodelivery.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.constantes.Constantes;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iniView();
    }

    private void iniView(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(userRegistred()) {
                    Intent mainIntent = new Intent().setClass(SplashActivity.this, PrincipalActivity.class);
                    startActivity(mainIntent);
                }else{
                    Intent mainIntent = new Intent().setClass(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                }
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, Constantes.DURACION_SPLASH);
    }

    private boolean userRegistred(){
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return false;
    }
}
