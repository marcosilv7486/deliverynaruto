package com.marcosilv7.narutodelivery.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.braintreepayments.cardform.OnCardFormValidListener;
import com.braintreepayments.cardform.view.CardForm;
import com.marcosilv7.narutodelivery.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class AgregarTarjetaActivity extends SupportActivity {

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.card_form)
    CardForm cardForm;

    @BindView(R.id.btnAgregarTarjeta)
    Button btnAgregarTarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarjeta);
        ButterKnife.bind(this);
        toolbarTitle.setText("Agregar Tarjeta");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarTarjetaActivity.this.onBackPressedSupport();
            }
        });
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .setup(this);
        cardForm.setOnCardFormValidListener(new OnCardFormValidListener() {
            @Override
            public void onCardFormValid(boolean valid) {
                if(valid){
                    guardarTarjeta();
                }
            }
        });


    }

    private void guardarTarjeta() {
        //TODO ENVIAR AL REST
    }

    @OnClick(R.id.btnAgregarTarjeta)
    public void onClickGuardar(){
        cardForm.validate();
    }





}
