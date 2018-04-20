package com.marcosilv7.narutodelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends AppCompatActivity {

    @NotEmpty(message = "Debe ingresar un nombre de Contacto")
    @BindView(R.id.txtNombreContactoCrudDireccion) EditText txtNombreContactoCrudDireccion;

    @NotEmpty(message = "Debe ingresar una direccion")
    @BindView(R.id.txtDireccionCrudDireccion) EditText txtDireccionCrudDireccion;

    @NotEmpty(message = "Debe ingresar un Distrito")
    @BindView(R.id.txtDistritoCrudDireccion) EditText txtDistritoCrudDireccion;

    @NotEmpty(message = "Debe ingresar un telefono")
    @BindView(R.id.txtTelefonoCrudDireccion) EditText txtTelefonoCrudDireccion;

    Validator validator;
    private PrefenciasUsuario prefenciasUsuario;
    private LinearLayout layoutDireccionNueva;
    private LinearLayout layoutDireccionMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(AddressActivity.this);
        prefenciasUsuario = new PrefenciasUsuario(this);
        layoutDireccionNueva = (LinearLayout) findViewById(R.id.direccionnueva);
        layoutDireccionMemoria = (LinearLayout) findViewById(R.id.direccionmemoria);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(prefenciasUsuario.verificarLogin()==true && !prefenciasUsuario.obtenerNombre().toString().isEmpty()
                && !prefenciasUsuario.obtenerDireccion().toString().isEmpty()) {
            layoutDireccionMemoria.setVisibility(View.VISIBLE);
            layoutDireccionNueva.setVisibility(View.GONE);
            TextView detalledireccion = (TextView)findViewById(R.id.txtDatosDireccion);

            String nombre = prefenciasUsuario.obtenerNombre();
            String direccion = prefenciasUsuario.obtenerDireccion();
            String distrito = prefenciasUsuario.obtenerDistrito();
            String telefono = prefenciasUsuario.obtenerTelefono();
            detalledireccion.setText("   " + nombre + "\n" + "   " + direccion + "\n" + "   " + distrito + "\n" + "   " + telefono);
        }
        else {
            layoutDireccionMemoria.setVisibility(View.GONE);
            layoutDireccionNueva.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btnEditarDireccion)
    public void asignardireccion(){
    seteardireccion();}

    @OnClick(R.id.btnContinuar)
    public void irapago(){
        continuarpago();}

    @OnClick(R.id.btnEliminarDireccion)
    public void borrardireccion(){
        eliminardireccion();}

    @OnClick(R.id.btnEntregarDireccion)
    public void entregar(){
        entregarpedido();}


    public void seteardireccion(){
        layoutDireccionMemoria.setVisibility(View.GONE);
        layoutDireccionNueva.setVisibility(View.VISIBLE);
        txtNombreContactoCrudDireccion.setText(prefenciasUsuario.obtenerNombre());
        txtDireccionCrudDireccion.setText(prefenciasUsuario.obtenerDireccion());
        txtDistritoCrudDireccion.setText(prefenciasUsuario.obtenerDistrito());
        txtTelefonoCrudDireccion.setText(prefenciasUsuario.obtenerTelefono());
    }

    public void continuarpago(){
        if(txtNombreContactoCrudDireccion.getText().toString().isEmpty()){
            Toast.makeText(this,"Ingrese un nombre de contacto",Toast.LENGTH_SHORT).show();
            txtNombreContactoCrudDireccion.setFocusable(true);
            return;
        }
        if(txtDireccionCrudDireccion.getText().toString().isEmpty()){
            Toast.makeText(this,"Ingrese una Direccionireccion",Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtDistritoCrudDireccion.getText().toString().isEmpty()){
            Toast.makeText(this,"Ingrese un Distrito",Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtTelefonoCrudDireccion.getText().toString().isEmpty()){
            Toast.makeText(this,"Ingrese su telefono",Toast.LENGTH_SHORT).show();
            return;
        }
        String nombre = txtNombreContactoCrudDireccion.getText().toString();
        String direccion = txtDireccionCrudDireccion.getText().toString();
        String distrito = txtDistritoCrudDireccion.getText().toString();
        String telefono = txtTelefonoCrudDireccion.getText().toString();
        prefenciasUsuario.actualizardireccion(nombre,direccion,distrito,telefono);
        //progressBar.setVisibility(View.VISIBLE);
        layoutDireccionMemoria.setVisibility(View.VISIBLE);
        layoutDireccionNueva.setVisibility(View.GONE);
        //progressBar.setVisibility(View.GONE);
            Intent intent = getIntent();
            finish();
            startActivity(intent);

    }

    public void eliminardireccion(){
        prefenciasUsuario.eliminardireccion();
        layoutDireccionMemoria.setVisibility(View.GONE);
        layoutDireccionNueva.setVisibility(View.VISIBLE);
    }

    public void entregarpedido(){
        Intent intent = new Intent(AddressActivity.this, TipoPagoActivity.class);
        startActivity(intent);
    }
}
