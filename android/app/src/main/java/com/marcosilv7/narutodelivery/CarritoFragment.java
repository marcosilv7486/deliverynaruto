package com.marcosilv7.narutodelivery;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.adapters.CarritoItemAdapter;
import com.marcosilv7.narutodelivery.adapters.ProductoAdapter;
import com.marcosilv7.narutodelivery.adapters.ProductoFamilyAdapter;
import com.marcosilv7.narutodelivery.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.events.ItemTouchOnSpiwed;
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.realm.models.CarritoModel;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.util.Util;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarritoFragment extends CustomFragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CarritoItemAdapter carritoItemAdapter;
    CarritoModel carritoModel;
    LinearLayout layoutCarritoVacio;
    TextView lblTotalCarritoItem;
    RelativeLayout rlCarritoCompras;

    public CarritoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        recyclerView = view.findViewById(R.id.recyclerCarritoProductos);
        lblTotalCarritoItem = view.findViewById(R.id.lblTotalCarritoItem);
        rlCarritoCompras = view.findViewById(R.id.rlCarritoCompras);
        layoutManager = new LinearLayoutManager(getActivity());
        carritoItemAdapter = new CarritoItemAdapter(getActivity(), new ArrayList<CarritoItemModel>(),
                new clickOperacionesCantidad() {
            @Override
            public void agregarClickListener(CarritoItemModel carritoItemModel) {
                agregar(carritoItemModel);
            }

            @Override
            public void agregarEnUnoClickListener(CarritoItemModel carritoItemModel) {
                aumentarEnUnoCarrito(carritoItemModel);
            }

            @Override
            public void disminuirEnUnoClickListener(CarritoItemModel carritoItemModel) {
                dismiunirEnUnoCarrito(carritoItemModel);
            }

            @Override
            public void removerClickListener(CarritoItemModel carritoItemModel) {
                remover(carritoItemModel);
            }
        });
        layoutCarritoVacio = view.findViewById(R.id.layoutCarritoVacio);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(carritoItemAdapter);
        recyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new ItemTouchOnSpiwed(carritoItemAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        cargarData();
        return view;
    }

    private void dismiunirEnUnoCarrito(CarritoItemModel carritoItemModel) {
        if(carritoItemModel.getCantidad() == 1){
            return;
        }
        QueryCarrito.disminuirEnUno(carritoItemModel);
        cargarData();
    }

    private void aumentarEnUnoCarrito(CarritoItemModel carritoItemModel) {
        QueryCarrito.aumentarEnUno(carritoItemModel);
        cargarData();
    }

    private void agregar(CarritoItemModel carritoItemModel){
        QueryCarrito.agregarItemCarrito(carritoItemModel);
        cargarData();
    }

    private void remover(CarritoItemModel carritoItemModel){
        QueryCarrito.eliminarItemCarrito(carritoItemModel);
        cargarData();
    }

    private void cargarData() {
        carritoModel = QueryCarrito.obtenerCarritoActual();
        if(carritoModel == null || carritoModel.getItems().isEmpty()){
            layoutCarritoVacio.setVisibility(View.VISIBLE);
            rlCarritoCompras.setVisibility(View.GONE);
        }else {
            rlCarritoCompras.setVisibility(View.VISIBLE);
            layoutCarritoVacio.setVisibility(View.GONE);
            ArrayList<CarritoItemModel> data = new ArrayList<>(carritoModel.getItems());
            carritoItemAdapter.actualizarData(data);
        }
        lblTotalCarritoItem.setText(Util.convertirFormatoDinero(carritoModel.getTotal()));
    }

    public interface clickOperacionesCantidad{
        void agregarClickListener(CarritoItemModel carritoItemModel);
        void agregarEnUnoClickListener(CarritoItemModel carritoItemModel);
        void disminuirEnUnoClickListener(CarritoItemModel carritoItemModel);
        void removerClickListener(CarritoItemModel carritoItemModel);
    }
}
