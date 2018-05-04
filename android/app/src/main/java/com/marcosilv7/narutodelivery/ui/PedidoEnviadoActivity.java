package com.marcosilv7.narutodelivery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.marcosilv7.narutodelivery.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class PedidoEnviadoActivity extends SupportActivity {

    @BindView(R.id.btnCerrarCarrito)
    Button btnCerrarCarrito;

    public static final int REFRESH_DELIVERY=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_enviado);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.btnCerrarCarrito)
    public void cerrarCarrito(){
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,REFRESH_DELIVERY);
    }
}
