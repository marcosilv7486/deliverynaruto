package com.marcosilv7.narutodelivery.ui;

import android.content.Intent;
import android.os.Bundle;

import com.marcosilv7.narutodelivery.R;

import me.yokeyword.fragmentation.SupportActivity;

public class PedidoEnviadoActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_enviado);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,3);
    }
}
