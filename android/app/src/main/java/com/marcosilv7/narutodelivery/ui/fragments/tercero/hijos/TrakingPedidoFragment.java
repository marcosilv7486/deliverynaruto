package com.marcosilv7.narutodelivery.ui.fragments.tercero.hijos;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.DetalleProductoGrillaAdapter;
import com.marcosilv7.narutodelivery.constantes.Constantes;
import com.marcosilv7.narutodelivery.dto.OrderDTO;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;
import com.marcosilv7.narutodelivery.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrakingPedidoFragment extends BaseBackFragment {

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txtNombreCompleto)
    TextInputLayout txtNombreCompleto;

    @BindView(R.id.txtDireccionEntrega)
    TextInputLayout txtDireccionEntrega;

    @BindView(R.id.txtTelefono)
    TextInputLayout txtTelefono;

    @BindView(R.id.txtFormaPago)
    TextInputLayout txtFormaPago;

    @BindView(R.id.txtTipoFacturacion)
    TextInputLayout txtTipoFacturacion;

    @BindView(R.id.lblSubtotal)
    TextInputLayout lblSubtotal;

    @BindView(R.id.textInputLayoutNumeroRuc)
    TextInputLayout textInputLayoutNumeroRuc;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.mapa)
    ImageView mapa;


    DetalleProductoGrillaAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    OrderDTO orderDTO;

    public static TrakingPedidoFragment newInstance(OrderDTO orderDTO) {
        Bundle args = new Bundle();
        args.putString(Constantes.ORDER_DATA,new Gson().toJson(orderDTO));
        TrakingPedidoFragment fragment = new TrakingPedidoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TrakingPedidoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_traking_pedido, container, false);
        ButterKnife.bind(this,view);
        orderDTO = new Gson().fromJson(getArguments().getString(Constantes.ORDER_DATA),OrderDTO.class);
        toolbarTitle.setText("Pedido Nº "+orderDTO.getNumber());
        initToolbarNav(toolbar);
        initView(view);
        return view;
    }

    private void initView(View view) {
        txtNombreCompleto.getEditText().setText(orderDTO.getUserFullName());
        txtNombreCompleto.getEditText().setFocusable(false);

        txtDireccionEntrega.getEditText().setText(orderDTO.getUserAddress());
        txtDireccionEntrega.getEditText().setFocusable(false);

        txtTelefono.getEditText().setText(orderDTO.getUserPhone());
        txtTelefono.getEditText().setFocusable(false);

        txtFormaPago.getEditText().setText(orderDTO.getPaymentType());
        txtFormaPago.getEditText().setFocusable(false);

        txtTipoFacturacion.getEditText().setText(orderDTO.getInvoiceType());
        txtTipoFacturacion.getEditText().setFocusable(false);

        lblSubtotal.getEditText().setText(Util.convertirFormatoDinero(orderDTO.getTotal().doubleValue()));
        lblSubtotal.getEditText().setFocusable(false);

        textInputLayoutNumeroRuc.getEditText().setText(orderDTO.getRucNumber()==null ?   "-"  : orderDTO.getRucNumber());
        textInputLayoutNumeroRuc.getEditText().setFocusable(false);

        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new DetalleProductoGrillaAdapter(getActivity(), orderDTO.getItems());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        Picasso.get().load(Util.obtenerUrlMapaStatic(orderDTO.getLatUserAddress(),orderDTO.getLonUserAddress()))
                .fit().into(mapa);
    }


}
