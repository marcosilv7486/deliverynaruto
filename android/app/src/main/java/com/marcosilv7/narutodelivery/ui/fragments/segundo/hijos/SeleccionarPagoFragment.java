package com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SeleccionarPagoFragment extends BaseBackFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.btnVerDetallePedido)
    Button btnVerDetallePedido;

    public static SeleccionarPagoFragment newInstance() {
        Bundle args = new Bundle();
        SeleccionarPagoFragment fragment = new SeleccionarPagoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccionar_pago, container, false);
        ButterKnife.bind(this,view);
        toolbarTitle.setText("Seleccionar Pago");
        initToolbarNav(toolbar);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }

    @OnClick(R.id.btnVerDetallePedido)
    void redireccionarVerPedido(){
        start(ConfirmarPedidoFragment.newInstance());
    }
}
