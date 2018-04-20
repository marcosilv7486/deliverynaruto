package com.marcosilv7.narutodelivery;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marcosilv7.narutodelivery.ProductosFragment.PRODUCT_FRAGMENT;


public class CategoriasFragment extends CustomFragment {

    ArrayList<ProductFamilyDTO> data;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    ProductoFamilyAdapter adapter;
    public static final String ID_FAMILIA="ID_FAMILIA";


    public CategoriasFragment() {
        data = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias_productos, container, false);
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
        Log.d("CategoriasFragment","ENTRANDO... "+familyDTO.getName());
        ProductosFragment productosFragment = new ProductosFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ID_FAMILIA,familyDTO.getId());
        productosFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout,productosFragment,PRODUCT_FRAGMENT);
        transaction.addToBackStack(PRODUCT_FRAGMENT);
        transaction.commit();
    }

    public interface FamilyProductOnClickListener{
        void onClick(ProductFamilyDTO data);
    }

}
