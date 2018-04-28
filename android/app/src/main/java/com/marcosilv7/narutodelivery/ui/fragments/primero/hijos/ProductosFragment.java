package com.marcosilv7.narutodelivery.ui.fragments.primero.hijos;


import android.os.Bundle;
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
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.ui.PrincipalActivity;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;

import java.util.ArrayList;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductosFragment extends BaseBackFragment {


    ArrayList<ProductDTO> data;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    ProductoAdapter adapter;
    Long idFamilia = -1L;

    public static ProductosFragment newInstance(Long idProducto) {
        Bundle args = new Bundle();
        args.putLong(CategoriasFragment.ID_FAMILIA,idProducto);
        ProductosFragment fragment = new ProductosFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        adapter = new ProductoAdapter(getActivity(), data, new OnClickListenerProducto() {
            @Override
            public void onClick(ProductDTO productDTO) {
                agregarItemCarrito(productDTO);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        idFamilia = getArguments().getLong(CategoriasFragment.ID_FAMILIA,-1L);
        cargarData();
        return view;
    }

    private void agregarItemCarrito(ProductDTO productDTO) {
        CarritoItemModel carritoItemModel = new CarritoItemModel();
        carritoItemModel.setImage(productDTO.getImage());
        carritoItemModel.setCantidad(1);
        carritoItemModel.setFamiliaProducto(productDTO.getFamily());
        carritoItemModel.setNombreProducto(productDTO.getName());
        carritoItemModel.setPrecio(productDTO.getPrice().doubleValue());
        carritoItemModel.setSubTotal(productDTO.getPrice().doubleValue());
        carritoItemModel.setIdProducto(productDTO.getId());
        QueryCarrito.agregarItemCarrito(carritoItemModel);
        //TODO displayMessageGeneral(recyclerView,"Se agrego al carrito");
        EventBusActivityScope.getDefault(_mActivity).post("HOLA");
        ((PrincipalActivity)getActivity()).actualizarCantidadCarrito(1);
    }

    private void cargarData() {
        Call<ArrayList<ProductDTO>> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                .obtenerProductosPorFamilia(idFamilia);
        call.enqueue(new Callback<ArrayList<ProductDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductDTO>> call, Response<ArrayList<ProductDTO>> response) {
               //TODO  ocultarLoadingUI(progressBar);
                progressBar.setVisibility(View.GONE);
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

    public interface OnClickListenerProducto{
        void onClick(ProductDTO productDTO);
    }
}
