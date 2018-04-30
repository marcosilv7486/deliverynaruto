package com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.AgregarTarjetaActivity;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TarjetasCreditoFragment extends BaseBackFragment {

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fabAddTarjeta)
    FloatingActionButton fabAddTarjeta;


    public TarjetasCreditoFragment() {
    }

    public static TarjetasCreditoFragment newInstance() {
        Bundle args = new Bundle();
        TarjetasCreditoFragment fragment = new TarjetasCreditoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tarjetas_credito, container, false);
        ButterKnife.bind(this,view);
        toolbarTitle.setText("Mis Tarjetas");
        initToolbarNav(toolbar);
        return view;
    }

    @OnClick(R.id.fabAddTarjeta)
    public void agregarTarjeta(){
        Intent intent = new Intent(getActivity(), AgregarTarjetaActivity.class);
        startActivity(intent);
    }
}
