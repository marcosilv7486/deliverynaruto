package com.marcosilv7.narutodelivery;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.marcosilv7.narutodelivery.adapters.ProductoFamilyAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.ProductFamilyDTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductosFragment extends CustomFragment {

    ArrayList<ProductFamilyDTO> data;


    RecyclerView recyclerView;
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    ProductoFamilyAdapter adapter;

    public ProductosFragment() {
        data = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos, container, false);
        recyclerView = view.findViewById(R.id.recyclerFamiliasProductos);
        progressBar = view.findViewById(R.id.progressBarFamiliasProductos);
        layoutManager = new GridLayoutManager(getActivity(),2);
        adapter = new ProductoFamilyAdapter(getActivity(), data, new FamilyProductOnClickListener() {
            @Override
            public void onClick(ProductFamilyDTO data) {
                cargarProductosPorFamilia(data);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        cargarData();
        return view;
    }

    private void cargarData() {
        Call<ArrayList<ProductFamilyDTO>> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                .obtenerFamiliasProductos();
        call.enqueue(new Callback<ArrayList<ProductFamilyDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductFamilyDTO>> call, Response<ArrayList<ProductFamilyDTO>> response) {
                ocultarLoadingUI(progressBar);
                if(response.code() == 200){
                    adapter.actualizarData(response.body());
                }else {

                }
            }
            @Override
            public void onFailure(Call<ArrayList<ProductFamilyDTO>> call, Throwable t) {

            }
        });
    }

    private void cargarProductosPorFamilia(ProductFamilyDTO familyDTO){
        Log.d("ProductosFragment","ENTRANDO... "+familyDTO.getName());
    }

    public interface FamilyProductOnClickListener{
        void onClick(ProductFamilyDTO data);
    }

}
