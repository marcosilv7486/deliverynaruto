package com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.CarritoItemAdapter;
import com.marcosilv7.narutodelivery.events.ItemTouchOnSpiwed;
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.realm.models.CarritoModel;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.util.Util;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarritoFragment extends SupportFragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CarritoItemAdapter carritoItemAdapter;
    CarritoModel carritoModel;
    LinearLayout layoutCarritoVacio;
    TextView lblTotalCarritoItem;
    RelativeLayout rlCarritoCompras;
    Button btnProcederPagoCarritoCompras;

    public CarritoFragment() {
        // Required empty public constructor
    }

    public static CarritoFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CarritoFragment fragment = new CarritoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        cargarData();
        return view;
    }

    private void initView(View view) {
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
        btnProcederPagoCarritoCompras = view.findViewById(R.id.btnProcederPagoCarritoCompras);
        btnProcederPagoCarritoCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              iniciarFragmentEntregaPedido();

            }
        });
        ItemTouchHelper.Callback callback = new ItemTouchOnSpiwed(carritoItemAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void iniciarFragmentEntregaPedido() {
        EntregaPedidoFragment entregaPedidoFragment = EntregaPedidoFragment.newInstance();
        start(entregaPedidoFragment);
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
            lblTotalCarritoItem.setText(Util.convertirFormatoDinero(carritoModel.getTotal()));
        }
    }

    public interface clickOperacionesCantidad{
        void agregarClickListener(CarritoItemModel carritoItemModel);
        void agregarEnUnoClickListener(CarritoItemModel carritoItemModel);
        void disminuirEnUnoClickListener(CarritoItemModel carritoItemModel);
        void removerClickListener(CarritoItemModel carritoItemModel);
    }

    @Subscribe
    public void demo(String text){
        Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
        cargarData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }

}