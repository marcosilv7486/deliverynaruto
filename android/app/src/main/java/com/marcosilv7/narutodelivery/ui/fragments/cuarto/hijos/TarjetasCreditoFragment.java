package com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.TarjetaAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.AddressDTO;
import com.marcosilv7.narutodelivery.dto.PaymentMethodDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.ui.AgregarTarjetaActivity;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TarjetasCreditoFragment extends BaseBackFragment {

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fabAddTarjeta)
    FloatingActionButton fabAddTarjeta;

    @BindView(R.id.rvListaTarjetas)
    RecyclerView rvListaTarjetas;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.layoutSinTarjetas)
    LinearLayout layoutSinTarjetas;

    PrefenciasUsuario prefenciasUsuario;
    RecyclerView.LayoutManager layoutManager;
    TarjetaAdapter tarjetaAdapter;

    public static final int RESULT_CREATE_CREDIT_CARD=1;


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
        layoutManager = new LinearLayoutManager(getActivity());
        prefenciasUsuario = new PrefenciasUsuario(getActivity());
        tarjetaAdapter = new TarjetaAdapter(new ArrayList<PaymentMethodDTO>(),getActivity());
        rvListaTarjetas.setLayoutManager(layoutManager);
        rvListaTarjetas.setAdapter(tarjetaAdapter);
        rvListaTarjetas.setHasFixedSize(true);
        return view;
    }

    @OnClick(R.id.fabAddTarjeta)
    public void agregarTarjeta(){
        Intent intent = new Intent(getActivity(), AgregarTarjetaActivity.class);
        startActivityForResult(intent,RESULT_CREATE_CREDIT_CARD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CREATE_CREDIT_CARD){
            Snackbar.make(rvListaTarjetas,"Tarjeta agregada correctamente",Snackbar.LENGTH_SHORT).show();
            cargarData();
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        cargarData();
    }

    private void cargarData() {
        progressBar.setVisibility(View.VISIBLE);
        Call<ArrayList<PaymentMethodDTO>> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                .obtenerMetodosDePagoPorUsuario(prefenciasUsuario.obtenerIdUsuario());
        call.enqueue(new Callback<ArrayList<PaymentMethodDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<PaymentMethodDTO>> call, Response<ArrayList<PaymentMethodDTO>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.code() == 200){
                    tarjetaAdapter.actualizarData(response.body());
                    if(response.body().isEmpty()){
                        layoutSinTarjetas.setVisibility(View.VISIBLE);
                        rvListaTarjetas.setVisibility(View.GONE);
                    }else {
                        layoutSinTarjetas.setVisibility(View.GONE);
                        rvListaTarjetas.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PaymentMethodDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
