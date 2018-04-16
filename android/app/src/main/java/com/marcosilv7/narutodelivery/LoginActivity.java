package com.marcosilv7.narutodelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.LoginRequestDTO;
import com.marcosilv7.narutodelivery.dto.LoginResponseDTO;
import com.marcosilv7.narutodelivery.dto.ProfileUserDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    public PrefenciasUsuario prefenciasUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefenciasUsuario = new PrefenciasUsuario(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        login();
    }


    public void login(){
        Call<LoginResponseDTO> call = ServiceGenerator.createService(NarutoApi.class,this)
                .validarAutenticacion(new LoginRequestDTO( "marcosilv7486@gmail.com","123456"));
        call.enqueue(new Callback<LoginResponseDTO>() {

            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if(response.isSuccessful()){
                    prefenciasUsuario.guardarDatosLogin(response.body());
                    Log.i(TAG,"LOGIN CORRECTO");
                    Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Log.d(TAG,"Error :( "+response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {

            }
        });
    }
}
