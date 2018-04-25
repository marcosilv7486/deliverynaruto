package com.marcosilv7.narutodelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.marcosilv7.narutodelivery.ui.PrincipalActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TipoPagoActivity extends AppCompatActivity {


    private LinearLayout tipopago;
    private LinearLayout operacionexitosa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_pago);
        ButterKnife.bind(TipoPagoActivity.this);
        tipopago = (LinearLayout) findViewById(R.id.tipopago);
        operacionexitosa = (LinearLayout) findViewById(R.id.operacionexitosa);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RadioButton rbnEfectivo = (RadioButton) findViewById(R.id.rbtEfectivo);
        RadioButton rbnTarjeta = (RadioButton) findViewById(R.id.rbnTarjeta);
        tipopago.setVisibility(View.VISIBLE);
        operacionexitosa.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnProceder)
    public void entregar(){
        procederpago();}

    @OnClick(R.id.btnVolverMenu)
    public void volvermenu(){
        regresarmenu();}

    public void procederpago(){
        RadioGroup grupoun = (RadioGroup) findViewById(R.id.GrupoRbnEfectivo);

        if (grupoun.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(this,"No se ha sellecionado el tipo de pago",Toast.LENGTH_SHORT).show();
            return;
        }
        tipopago.setVisibility(View.GONE);
        operacionexitosa.setVisibility(View.VISIBLE);
        }
        public void regresarmenu(){
            Intent intent = new Intent(TipoPagoActivity.this, PrincipalActivity.class);
            startActivity(intent);
        }
}
