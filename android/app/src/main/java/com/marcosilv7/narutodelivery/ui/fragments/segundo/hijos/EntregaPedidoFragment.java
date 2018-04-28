package com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class EntregaPedidoFragment extends BaseBackFragment {

    @NotEmpty(message = "Debe ingresar un nombre de Contacto")
    @BindView(R.id.txtNombreContactoCrudDireccion)
    EditText txtNombreContactoCrudDireccion;

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


    public EntregaPedidoFragment() {
        // Required empty public constructor
    }

    public static EntregaPedidoFragment newInstance() {
        
        Bundle args = new Bundle();
        
        EntregaPedidoFragment fragment = new EntregaPedidoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    Button btnDetalleProducto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_entrega_pedido, container, false);
        btnDetalleProducto = (Button)view.findViewById(R.id.btnEntregarDireccion);
        //ButterKnife.bind(getActivity());
        prefenciasUsuario = new PrefenciasUsuario(getActivity());
        layoutDireccionNueva = (LinearLayout) view.findViewById(R.id.direccionnueva);
        layoutDireccionMemoria = (LinearLayout) view.findViewById(R.id.direccionmemoria);



        if(prefenciasUsuario.verificarLogin()==true && !prefenciasUsuario.obtenerNombre().toString().isEmpty()
                && !prefenciasUsuario.obtenerDireccion().toString().isEmpty()) {
            layoutDireccionMemoria.setVisibility(View.VISIBLE);
            layoutDireccionNueva.setVisibility(View.GONE);
            TextView detalledireccion = (TextView)view.findViewById(R.id.txtDatosDireccion);

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

        btnDetalleProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipoPagoFragment tipoPagoFragment = TipoPagoFragment.newInstance();
                start(tipoPagoFragment);
            }
        });


        return  view ;
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
        entregarpedido();
    }


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
            Toast.makeText(getActivity(),"Ingrese un nombre de contacto",Toast.LENGTH_SHORT).show();
            txtNombreContactoCrudDireccion.setFocusable(true);
            return;
        }
        if(txtDireccionCrudDireccion.getText().toString().isEmpty()){
            Toast.makeText(getActivity(),"Ingrese una Direccionireccion",Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtDistritoCrudDireccion.getText().toString().isEmpty()){
            Toast.makeText(getActivity(),"Ingrese un Distrito",Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtTelefonoCrudDireccion.getText().toString().isEmpty()){
            Toast.makeText(getActivity(),"Ingrese su telefono",Toast.LENGTH_SHORT).show();
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

        //Intent intent = getIntent();
        // finish();
        // startActivity(intent);

    }

    public void eliminardireccion(){
        prefenciasUsuario.eliminardireccion();
        layoutDireccionMemoria.setVisibility(View.GONE);
        layoutDireccionNueva.setVisibility(View.VISIBLE);
    }

    public void entregarpedido(){

    }

}
