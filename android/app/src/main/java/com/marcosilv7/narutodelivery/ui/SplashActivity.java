package com.marcosilv7.narutodelivery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.constantes.Constantes;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash)
    ImageView imageView;

    PrefenciasUsuario prefenciasUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Picasso.get().load(R.drawable.splash_logo)
                .fit()
                .into(imageView);
        prefenciasUsuario = new PrefenciasUsuario(this);
        iniView();
    }

    private void iniView(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(prefenciasUsuario.verificarLogin()) {
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
}
