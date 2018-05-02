package com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.CarritoItemAdapter;
import com.marcosilv7.narutodelivery.events.ItemTouchOnSpiwed;
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.realm.models.CarritoModel;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.ui.PrincipalActivity;
import com.marcosilv7.narutodelivery.util.Util;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;



public class CarritoFragment extends SupportFragment {

    @BindView(R.id.recyclerCarritoProductos)
    RecyclerView recyclerView;

    @BindView(R.id.layoutCarritoVacio)
    LinearLayout layoutCarritoVacio;

    @BindView(R.id.lblTotalCarritoItem)
    TextView lblTotalCarritoItem;

    @BindView(R.id.rlCarritoCompras)
    LinearLayout rlCarritoCompras;

    @BindView(R.id.btnProcederPagoCarritoCompras)
    Button btnProcederPagoCarritoCompras;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    RecyclerView.LayoutManager layoutManager;
    CarritoItemAdapter carritoItemAdapter;
    CarritoModel carritoModel;

    public CarritoFragment() {
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
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        ButterKnife.bind(this,view);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        cargarData();
        return view;
    }

    private void initView(View view) {
        toolbarTitle.setText("Mi Pedido");
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
    }

    @OnClick(R.id.btnProcederPagoCarritoCompras)
    public void onClickProcederConElPago(){
        EntregaPedidoFragment entregaPedidoFragment = EntregaPedidoFragment.newInstance();
        start(entregaPedidoFragment);
    }


    private void dismiunirEnUnoCarrito(CarritoItemModel carritoItemModel) {
        if(carritoItemModel.getCantidad() == 1){
            return;
        }
        QueryCarrito.disminuirEnUno(carritoItemModel);
        ((PrincipalActivity)getActivity()).actualizarCantidadCarrito(-1);
        cargarData();
    }

    private void aumentarEnUnoCarrito(CarritoItemModel carritoItemModel) {
        QueryCarrito.aumentarEnUno(carritoItemModel);
        ((PrincipalActivity)getActivity()).actualizarCantidadCarrito(1);
        cargarData();
    }

    private void agregar(CarritoItemModel carritoItemModel){
        int cantidad = carritoItemModel.getCantidad();
        QueryCarrito.agregarItemCarrito(carritoItemModel);
        ((PrincipalActivity)getActivity()).actualizarCantidadCarrito(cantidad);
        cargarData();
    }

    private void remover(CarritoItemModel carritoItemModel){
        int cantidad = carritoItemModel.getCantidad();
        QueryCarrito.eliminarItemCarrito(carritoItemModel);
        ((PrincipalActivity)getActivity()).actualizarCantidadCarrito(cantidad*-1);
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
        cargarData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }

}
