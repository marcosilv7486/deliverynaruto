package com.marcosilv7.narutodelivery.ui.fragments.tercero.hijos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.OrdenPedidoAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.OrderDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeliveryFragment extends SupportFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.layoutSinDelivery)
    LinearLayout layoutSinDelivery;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.progressBar)
    LinearLayout progressBar;

    OrdenPedidoAdapter ordenPedidoAdapter;
    RecyclerView.LayoutManager layoutManager;

    PrefenciasUsuario preferencias;

    public DeliveryFragment() {

    }

    public static DeliveryFragment newInstance() {
        Bundle args = new Bundle();
        DeliveryFragment fragment = new DeliveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delivery, container, false);
        ButterKnife.bind(this,view);
        toolbarTitle.setText("Ordenes pendientes de entrega");
        preferencias = new PrefenciasUsuario(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        ordenPedidoAdapter = new OrdenPedidoAdapter(new ArrayList<OrderDTO>(),getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ordenPedidoAdapter);
        recyclerView.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        cargarData();
    }

    private void cargarData() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        layoutSinDelivery.setVisibility(View.GONE);
        Call<List<OrderDTO>> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                .obtenerOrdenesPorUsuario(preferencias.obtenerIdUsuario());
        call.enqueue(new Callback<List<OrderDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                layoutSinDelivery.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                if(response.code() == 200){
                    ordenPedidoAdapter.actualizarData(response.body());
                    if(response.body().isEmpty()){
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        layoutSinDelivery.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                layoutSinDelivery.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        cargarData();
    }
}
