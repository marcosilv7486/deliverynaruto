package com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.DetalleProductoGrillaAdapter;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;
import com.marcosilv7.narutodelivery.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ConfirmarPedidoFragment extends BaseBackFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.lblSubtotal)
    TextView lblSubtotal;

    PrefenciasUsuario prefenciasUsuario;
    DetalleProductoGrillaAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

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
        prefenciasUsuario = new PrefenciasUsuario(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new DetalleProductoGrillaAdapter(getActivity(), QueryCarrito.obtenerCarritoActual());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        lblSubtotal.setText(Util.convertirFormatoDinero(QueryCarrito.obtenerImporteTotal()));
    }

}
