package com.marcosilv7.narutodelivery.ui.fragments.primero.hijos.tab;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.ProductoAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.ProductDTO;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.ui.PrincipalActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductoCategoriaFragment extends SupportFragment {

    Long categoriaId;
    static String CATEGORIA_ID;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    ProductoAdapter adapter;


    public static ProductoCategoriaFragment newInstance(Long categoriaId) {
        Bundle args = new Bundle();
        args.putLong(CATEGORIA_ID,categoriaId);
        ProductoCategoriaFragment fragment = new ProductoCategoriaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_producto_categoria, container, false);
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        layoutManager = new GridLayoutManager(getActivity(),2);
        adapter = new ProductoAdapter(getActivity(), new ArrayList<ProductDTO>(),
                new EventosProductos() {
                    @Override
                    public void agregarAlCarrito(ProductDTO data) {
                        agregarProductoCarrito(data);
                    }

                    @Override
                    public void verDetalle(ProductDTO data) {

                    }
                });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        categoriaId = getArguments().getLong(CATEGORIA_ID,-1L);
        cargarData();
    }

    private void cargarData() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        Call<ArrayList<ProductDTO>> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                .obtenerProductosPorFamilia(categoriaId);
        call.enqueue(new Callback<ArrayList<ProductDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductDTO>> call, Response<ArrayList<ProductDTO>> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if(response.code() == 200){
                    adapter.actualizarData(response.body());
                }else {

                }
            }
            @Override
            public void onFailure(Call<ArrayList<ProductDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    public interface EventosProductos {
        void agregarAlCarrito(ProductDTO data);
        void verDetalle(ProductDTO data);
    }

    public void agregarProductoCarrito(ProductDTO productDTO){
        QueryCarrito.agregarProductDTO(productDTO);
        ((PrincipalActivity)getActivity()).actualizarCantidadCarrito(QueryCarrito.obtenerCantidadActualCarrito());
        EventBusActivityScope.getDefault(_mActivity).post(productDTO);

    }
}
