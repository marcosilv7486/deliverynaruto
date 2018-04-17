package com.marcosilv7.narutodelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.LoginRequestDTO;
import com.marcosilv7.narutodelivery.dto.LoginResponseDTO;
import com.marcosilv7.narutodelivery.dto.error.ErrorResponse;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.util.Util;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    public static final String TAG = LoginActivity.class.getSimpleName();

    public PrefenciasUsuario prefenciasUsuario;

    @NotEmpty(message = "Debe ingresar su correo electronico")
    @Email(message = "El formato no es el correcto")
    @BindView(R.id.txtEmailLogin)
    EditText inputEmail;

    @BindView(R.id.txtPasswordLogin)
    @NotEmpty(message = "Debe ingresar su contraseña")
    @Length(min = 8,message = "La contraseña debe ser de 8 caracteres")
    EditText inputPassword;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btn_signup)
    TextView btnCrearCuenta;

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        prefenciasUsuario = new PrefenciasUsuario(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(prefenciasUsuario.verificarLogin()) {
            Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_login)
    public void login(){
        validator.validate();
    }

    @OnClick(R.id.btn_signup)
    public void registrarUsuario(){
        Intent intent = new Intent(LoginActivity.this, RegistrarUsuarioActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        //Validaciones
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(inputEmail.getText().toString(),
                inputPassword.getText().toString());
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
                    Snackbar.make(btnCrearCuenta,errorResponse.getError(),Snackbar.LENGTH_SHORT).show();;
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
