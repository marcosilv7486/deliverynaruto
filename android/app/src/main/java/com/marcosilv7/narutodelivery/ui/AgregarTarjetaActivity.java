package com.marcosilv7.narutodelivery.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.braintreepayments.cardform.OnCardFormValidListener;
import com.braintreepayments.cardform.view.CardForm;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.PaymentMethodDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos.TarjetasCreditoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarTarjetaActivity extends SupportActivity {

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.card_form)
    CardForm cardForm;

    @BindView(R.id.btnAgregarTarjeta)
    Button btnAgregarTarjeta;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.llFormAgregarTarjeta)
    LinearLayout llFormAgregarTarjeta;

    PrefenciasUsuario prefenciasUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarjeta);
        ButterKnife.bind(this);
        prefenciasUsuario = new PrefenciasUsuario(this);
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
        PaymentMethodDTO data = new PaymentMethodDTO();
        data.setCvv(cardForm.getCvv());
        data.setFavorite(false);
        data.setMonthExp(Integer.valueOf(cardForm.getExpirationMonth()));
        data.setYearExp(Integer.valueOf(cardForm.getExpirationYear()));
        data.setNumberCreditCard(cardForm.getCardNumber());
        data.setPostalCode(cardForm.getPostalCode());
        Call<PaymentMethodDTO> call = ServiceGenerator.createService(NarutoApi.class,this)
                .crearMetodoDePago(prefenciasUsuario.obtenerIdUsuario(),data);
        llFormAgregarTarjeta.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<PaymentMethodDTO>() {
            @Override
            public void onResponse(Call<PaymentMethodDTO> call, Response<PaymentMethodDTO> response) {
                llFormAgregarTarjeta.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if(response.code() == 200){
                    setResult(TarjetasCreditoFragment.RESULT_CREATE_CREDIT_CARD);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<PaymentMethodDTO> call, Throwable t) {
                llFormAgregarTarjeta.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.btnAgregarTarjeta)
    public void onClickGuardar(){
        cardForm.validate();
    }

}
