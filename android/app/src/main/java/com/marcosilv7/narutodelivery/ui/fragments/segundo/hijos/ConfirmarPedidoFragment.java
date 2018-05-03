package com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ConfirmarPedidoFragment extends BaseBackFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    public static ConfirmarPedidoFragment newInstance() {
        Bundle args = new Bundle();
        ConfirmarPedidoFragment fragment = new ConfirmarPedidoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ConfirmarPedidoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmar_pedido, container, false);
        ButterKnife.bind(this,view);
        toolbarTitle.setText("Confirmar Orden");
        initToolbarNav(toolbar);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }

}
