package com.marcosilv7.narutodelivery.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.LoginRequestDTO;
import com.marcosilv7.narutodelivery.dto.LoginResponseDTO;
import com.marcosilv7.narutodelivery.dto.ProfileUserDTO;
import com.marcosilv7.narutodelivery.dto.RegisterUserDTO;
import com.marcosilv7.narutodelivery.dto.error.ErrorResponse;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.util.Util;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarUsuarioActivity extends AppCompatActivity implements Validator.ValidationListener {


    @NotEmpty(message = "Debe ingresar un nombre")
    @Length(min = 2,max = 80,message = "Debe tener entre 2 y 40 caracteres")
    @BindView(R.id.txtNombreCrearUsuario) EditText txtNombreCrearUsuario;

    @NotEmpty(message = "Debe ingresar un email")
    @Email(message = "Formato incorrecto")
    @BindView(R.id.txtEmailCrearUsuario) EditText txtEmailCrearUsuario;

    @NotEmpty(message = "Debe ingresar una contraseña")
    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC,
            message = "Minimo 8 caracteres y formato alfanumerico")
    @BindView(R.id.txtPasswordlCrearUsuario) EditText txtPasswordlCrearUsuario;

    @ConfirmPassword(message = "Las contraseñas no son iguales")
    @NotEmpty(message = "Debe confirmar su contraseña")
    @BindView(R.id.txtConfirmarPasswordCrearUsuario) EditText txtConfirmarPasswordCrearUsuario;


    @Length(max = 9,message = "Maximo tener 9 caracteres")
    @BindView(R.id.txtTelefonoCrearUsuario) EditText txtTelefonoCrearUsuario;

    @BindView(R.id.btnCrearUsuario) Button btnCrearUsuario;
    @BindView(R.id.btnLoginCrearUsuario) TextView btnLoginCrearUsuario;

    @BindView(R.id.txtBirthDayCrearUsuario) TextView txtBirthDayCrearUsuario;

    @BindView(R.id.btnCalendarioCrearUsuario) ImageView btnCalendarioCrearUsuario;

    Validator validator;

    PrefenciasUsuario prefenciasUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        prefenciasUsuario = new PrefenciasUsuario(this);
        initView();
    }

    private void initView(){
        btnCalendarioCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                new android.app.DatePickerDialog(
                        RegistrarUsuarioActivity.this,
                        new android.app.DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtBirthDayCrearUsuario.setText(date);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
        txtBirthDayCrearUsuario.setFocusable(false);
        txtBirthDayCrearUsuario.setClickable(false);
        txtBirthDayCrearUsuario.setEnabled(false);
    }

    @OnClick(R.id.btnLoginCrearUsuario)
    public void redireccionarLogin(){
        Intent intent = new Intent(RegistrarUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.btnCrearUsuario)
    public void intentarCrearUsuario(){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setEmail(txtEmailCrearUsuario.getText().toString());
        registerUserDTO.setName(txtNombreCrearUsuario.getText().toString());
        registerUserDTO.setLastName(txtNombreCrearUsuario.getText().toString());
        registerUserDTO.setPhone(txtTelefonoCrearUsuario.getText().toString());
        registerUserDTO.setPassword(txtPasswordlCrearUsuario.getText().toString());
        Date birthDay = Util.generarFecha(txtBirthDayCrearUsuario.getText().toString(),"dd/MM/yyyy");
        if(birthDay != null){
            registerUserDTO.setBithDay(birthDay);
        }
        Call<ProfileUserDTO> call = ServiceGenerator.createService(NarutoApi.class,RegistrarUsuarioActivity.this)
                .registrarUsuario(registerUserDTO);
        call.enqueue(new Callback<ProfileUserDTO>() {
            @Override
            public void onResponse(Call<ProfileUserDTO> call, Response<ProfileUserDTO> response) {
                Log.d("REQUEST",call.request().body().toString());
                if(response.code()  == 200){
                    //Login Indirecto
                    loginIndirecto();
                }else {
                    ErrorResponse errorResponse = Util.parseError(response,RegistrarUsuarioActivity.this);
                    Snackbar.make(btnLoginCrearUsuario,errorResponse.obtenerMensajeError(),Snackbar.LENGTH_SHORT).show();;
                }
            }

            @Override
            public void onFailure(Call<ProfileUserDTO> call, Throwable t) {
            }
        });
    }

    private void loginIndirecto() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(txtEmailCrearUsuario.getText().toString(),
                txtPasswordlCrearUsuario.getText().toString());
        Call<LoginResponseDTO> call = ServiceGenerator.createService(NarutoApi.class,this)
                .validarAutenticacion(loginRequestDTO);
        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if(response.isSuccessful()){
                    prefenciasUsuario.guardarDatosLogin(response.body());
                    Intent intent = new Intent(RegistrarUsuarioActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
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
