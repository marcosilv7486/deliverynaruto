package com.marcosilv7.narutodelivery;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.marcosilv7.narutodelivery.adapters.ProductoAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.ProductDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductosFragment extends CustomFragment {

    public static final String PRODUCT_FRAGMENT="ProductosFragment";

    ArrayList<ProductDTO> data;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    ProductoAdapter adapter;
    Long idFamilia = -1L;

    public ProductosFragment() {
        data = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_productos, container, false);
        recyclerView = view.findViewById(R.id.recyclerProductos);
        progressBar = view.findViewById(R.id.progressBarProductos);
        layoutManager = new GridLayoutManager(getActivity(),2);
        adapter = new ProductoAdapter(getActivity(), data);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        idFamilia = getArguments().getLong(CategoriasFragment.ID_FAMILIA,-1L);
        cargarData();
        return  view;
    }

    private void cargarData() {
        Call<ArrayList<ProductDTO>> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                .obtenerProductosPorFamilia(idFamilia);
        call.enqueue(new Callback<ArrayList<ProductDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductDTO>> call, Response<ArrayList<ProductDTO>> response) {
                ocultarLoadingUI(progressBar);
                if(response.code() == 200){
                    adapter.actualizarData(response.body());
                }else {

                }
            }
            @Override
            public void onFailure(Call<ArrayList<ProductDTO>> call, Throwable t) {

            }
        });
    }

}
