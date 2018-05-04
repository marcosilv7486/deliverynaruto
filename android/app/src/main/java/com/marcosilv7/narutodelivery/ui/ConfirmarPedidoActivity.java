package com.marcosilv7.narutodelivery.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.DetalleProductoGrillaAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.constantes.Constantes;
import com.marcosilv7.narutodelivery.dto.OrderDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.ui.base.CustomSupportActivity;
import com.marcosilv7.narutodelivery.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marcosilv7.narutodelivery.constantes.Constantes.ORDER_DATA;

public class ConfirmarPedidoActivity extends CustomSupportActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.lblSubtotal)
    TextView lblSubtotal;

    @BindView(R.id.txtNombreCompleto)
    TextView txtNombreCompleto;

    @BindView(R.id.txtTelefono)
    TextView txtTelefono;

    @BindView(R.id.txtDireccionEntrega)
    TextView txtDireccionEntrega;

    @BindView(R.id.txtFormaPago)
    TextView txtFormaPago;

    @BindView(R.id.txtTipoFacturacion)
    TextView txtTipoFacturacion;

    @BindView(R.id.textInputLayoutNumeroRuc)
    TextInputLayout textInputLayoutNumeroRuc;

    @BindView(R.id.txtNumeroRuc)
    EditText txtNumeroRuc;

    @BindView(R.id.btnRealizarPedido)
    Button btnRealizarPedido;

    OrderDTO orderDTO;

    PrefenciasUsuario prefenciasUsuario;
    DetalleProductoGrillaAdapter adapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pedido);
        ButterKnife.bind(this);
        toolbarTitle.setText("Confirmar Orden");
        initToolbarNav(toolbar);
        String json = getIntent().getStringExtra(ORDER_DATA);
        orderDTO = new Gson().fromJson(json,OrderDTO.class);
        initView();
    }

    private void initView() {
        if(orderDTO != null){
            prefenciasUsuario = new PrefenciasUsuario(this);
            layoutManager = new LinearLayoutManager(this);
            adapter = new DetalleProductoGrillaAdapter(this, orderDTO.getItems());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            lblSubtotal.setText(Util.convertirFormatoDinero(orderDTO.getTotal().doubleValue()));
            txtDireccionEntrega.setText(orderDTO.getUserAddress());
            txtDireccionEntrega.setFocusable(false);
            txtNombreCompleto.setText(orderDTO.getUserFullName());
            txtNombreCompleto.setFocusable(false);
            txtTelefono.setText(orderDTO.getUserPhone());
            txtTelefono.setFocusable(false);
            txtFormaPago.setText(orderDTO.getPaymentType());
            txtFormaPago.setFocusable(false);
            txtTipoFacturacion.setText(orderDTO.getInvoiceType());
            txtTipoFacturacion.setFocusable(false);
            adapter.actualizarData(orderDTO.getItems());
            textInputLayoutNumeroRuc.setVisibility(View.GONE);
            txtNumeroRuc.setFocusable(false);
            if(!TextUtils.isEmpty(orderDTO.getRucNumber())){
                textInputLayoutNumeroRuc.setVisibility(View.VISIBLE);
                textInputLayoutNumeroRuc.getEditText().setText(orderDTO.getRucNumber());
            }
        }
    }

    @OnClick(R.id.btnRealizarPedido)
    public void onClickButton(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esta seguro de realizar el pedido ? , una vez enviado ya no se podra cancelar.")
                .setTitle("Realizar pedido");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enviarPedido();
            }

        });
        builder.show();
    }

    private void enviarPedido() {
        Call<OrderDTO> call = ServiceGenerator.createService(NarutoApi.class,this).registrarOrden(orderDTO);
        call.enqueue(new Callback<OrderDTO>() {
            @Override
            public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                if(response.code() == 200){
                    QueryCarrito.limpiarCarrito();
                    Intent intent = new Intent(ConfirmarPedidoActivity.this, PedidoEnviadoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constantes.ORDER_DATA,new Gson().toJson(orderDTO));
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<OrderDTO> call, Throwable t) {

            }
        });
    }


}
