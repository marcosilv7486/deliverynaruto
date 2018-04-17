package com.marcosilv7.narutodelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.LoginRequestDTO;
import com.marcosilv7.narutodelivery.dto.LoginResponseDTO;
import com.marcosilv7.narutodelivery.dto.error.ErrorResponse;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    public PrefenciasUsuario prefenciasUsuario;

    @BindView(R.id.txtEmailLogin)
    EditText inputEmail;
    @BindView(R.id.txtPasswordLogin)
    EditText inputPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        prefenciasUsuario = new PrefenciasUsuario(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick(R.id.btn_login)
    public void login(){
        //Validaciones
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(inputEmail.getText().toString(),
                inputPassword.getText().toString());
        if (TextUtils.isEmpty(loginRequestDTO.getUsername())) {
            Toast.makeText(getApplicationContext(), "Ingresar correo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(loginRequestDTO.getPassword())) {
            Toast.makeText(getApplicationContext(), "Ingresar contraseña!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        Call<LoginResponseDTO> call = ServiceGenerator.createService(NarutoApi.class,this)
                .validarAutenticacion(loginRequestDTO);
        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    prefenciasUsuario.guardarDatosLogin(response.body());
                    Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    ErrorResponse errorResponse = Util.parseError(response,LoginActivity.this);
                    Toast.makeText(LoginActivity.this, errorResponse.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
