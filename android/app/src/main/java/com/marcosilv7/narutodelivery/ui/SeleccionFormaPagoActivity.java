package com.marcosilv7.narutodelivery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.constantes.Constantes;
import com.marcosilv7.narutodelivery.dto.OrderDTO;
import com.marcosilv7.narutodelivery.ui.base.CustomSupportActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.marcosilv7.narutodelivery.constantes.Constantes.ORDER_DATA;

public class SeleccionFormaPagoActivity extends CustomSupportActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.btnVerDetallePedido)
    Button btnVerDetallePedido;

    @BindView(R.id.totalAPagar)
    TextView totalAPagar;

    @BindView(R.id.rbtEfectivo)
    RadioButton rbtEfectivo;

    @BindView(R.id.rbnPOS)
    RadioButton rbnPOS;

    @BindView(R.id.rbnTarjeta)
    RadioButton rbnTarjeta;

    @BindView(R.id.rbnFactura)
    RadioButton rbnFactura;

    @BindView(R.id.rbtBoleta)
    RadioButton rbtBoleta;

    @BindView(R.id.textImputLayoutEfectivo)
    TextInputLayout textImputLayoutEfectivo;

    @BindView(R.id.textInputLayoutNumeroRuc)
    TextInputLayout textInputLayoutNumeroRuc;

    @BindView(R.id.grupoRbnFormaPago)
    RadioGroup grupoRbnFormaPago;

    OrderDTO orderDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_forma_pago);
        ButterKnife.bind(this);
        toolbarTitle.setText("Seleccionar Pago");
        initToolbarNav(toolbar);
        Intent intent = getIntent();
        String json = intent.getStringExtra(ORDER_DATA);
        orderDTO = new Gson().fromJson(json,OrderDTO.class);
        initView();
    }

    private void initView() {
        rbtEfectivo.setChecked(true);
        rbtEfectivo.callOnClick();
        rbtBoleta.setChecked(true);
        rbtBoleta.callOnClick();
    }

    @OnClick(R.id.rbtEfectivo)
    public void onClickRbtEfectivo(){
        if(textImputLayoutEfectivo.getVisibility() == View.GONE){
            textImputLayoutEfectivo.setVisibility(View.VISIBLE);
            textImputLayoutEfectivo.setError(null);
        }
    }

    @OnClick(R.id.rbnPOS)
    public void onClickRbnPos(){
        if(textImputLayoutEfectivo.getVisibility() == View.VISIBLE){
            textImputLayoutEfectivo.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.rbnTarjeta)
    public void onClickRbtTarjeta(){
        if(textImputLayoutEfectivo.getVisibility() == View.VISIBLE){
            textImputLayoutEfectivo.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.rbnFactura)
    public void onClickRbnFactura(){
        if(textInputLayoutNumeroRuc.getVisibility() == View.GONE){
            textInputLayoutNumeroRuc.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.rbtBoleta)
    public void onClickRbnBoleta(){
        if(textInputLayoutNumeroRuc.getVisibility() == View.VISIBLE){
            textInputLayoutNumeroRuc.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btnVerDetallePedido)
    public void redireccionarVerPedido(){
        if(orderDTO != null){
            //FORMA DE PAGO
            if(rbtEfectivo.isChecked()){
                String formaPago = rbtEfectivo.getText().toString();
                orderDTO.setPaymentType(formaPago);
                String efectivo = textImputLayoutEfectivo.getEditText().getText().toString();
            }
            if(rbnPOS.isChecked()){
                String formaPago = rbnPOS.getText().toString();
                orderDTO.setPaymentType(formaPago);
            }
            if(rbnTarjeta.isChecked()){
                String formaPago = rbnTarjeta.getText().toString();
                orderDTO.setPaymentType(formaPago);
            }
            //TIPO DE FACTURACION
            if(rbnFactura.isChecked()){
                String tipoFacturacion  = rbnFactura.getText().toString();
                String numeroRuc = textInputLayoutNumeroRuc.getEditText().getText().toString();
                orderDTO.setRucNumber(numeroRuc);
                orderDTO.setInvoiceType(tipoFacturacion);
            }
            if(rbtBoleta.isChecked()){
                String tipoFacturacion  = rbtBoleta.getText().toString();
                orderDTO.setInvoiceType(tipoFacturacion);
            }
        }
        Intent intent = new Intent(SeleccionFormaPagoActivity.this,ConfirmarPedidoActivity.class);
        intent.putExtra(Constantes.ORDER_DATA,new Gson().toJson(orderDTO));
        startActivity(intent);
    }
}
