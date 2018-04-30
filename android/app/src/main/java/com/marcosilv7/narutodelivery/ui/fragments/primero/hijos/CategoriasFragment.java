package com.marcosilv7.narutodelivery.ui.fragments.primero.hijos;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.ProductoFamilyAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;

import java.util.ArrayList;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriasFragment extends SupportFragment {

    ArrayList<ProductFamilyDTO> data;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    ProductoFamilyAdapter adapter;
    PrefenciasUsuario prefenciasUsuario;

    public static final String ID_FAMILIA="ID_FAMILIA";

    public CategoriasFragment() {
        data = new ArrayList<>();
    }

    public static CategoriasFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CategoriasFragment fragment = new CategoriasFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias_productos, container, false);
        inintView(view);
        cargarData();
        return view;
    }

    private void inintView(View view) {
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
        prefenciasUsuario = new PrefenciasUsuario(getActivity());
    }

    private void cargarData() {
        Call<ArrayList<ProductFamilyDTO>> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                .obtenerFamiliasProductos();
        call.enqueue(new Callback<ArrayList<ProductFamilyDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductFamilyDTO>> call, Response<ArrayList<ProductFamilyDTO>> response) {
                progressBar.setVisibility(View.GONE);
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
        ProductosFragment fragment = ProductosFragment.newInstance(familyDTO.getId());
        start(fragment);
    }

    public interface FamilyProductOnClickListener{
        void onClick(ProductFamilyDTO data);
    }


}
