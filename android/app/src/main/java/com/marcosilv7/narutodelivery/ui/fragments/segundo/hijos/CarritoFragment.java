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
import com.marcosilv7.narutodelivery.dto.ProductDTO;
import com.marcosilv7.narutodelivery.events.ItemTouchOnSpiwed;
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.ui.PrincipalActivity;
import com.marcosilv7.narutodelivery.util.Util;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

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
    List<CarritoItemModel> carritoItemModelList;

    int cantidadTotalProductos = 0;
    double subTotal=0.0;

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
        QueryCarrito.modificarItemCarrito(carritoItemModel,-1);
        cargarData();
    }

    private void aumentarEnUnoCarrito(CarritoItemModel carritoItemModel) {
        QueryCarrito.modificarItemCarrito(carritoItemModel,1);
        cargarData();
    }

    private void agregar(CarritoItemModel carritoItemModel){
        QueryCarrito.resucitarItemCarrito(carritoItemModel);
        cargarData();
    }

    private void remover(CarritoItemModel carritoItemModel){
        QueryCarrito.removerItemCarrito(carritoItemModel);
        cargarData();
    }


    private void cargarData() {
        carritoItemModelList = QueryCarrito.obtenerCarritoActual();
        if(carritoItemModelList == null || carritoItemModelList.isEmpty()){
            layoutCarritoVacio.setVisibility(View.VISIBLE);
            rlCarritoCompras.setVisibility(View.GONE);
        }else {
            rlCarritoCompras.setVisibility(View.VISIBLE);
            layoutCarritoVacio.setVisibility(View.GONE);
            carritoItemAdapter.actualizarData(carritoItemModelList);
        }
        cantidadTotalProductos = 0;
        subTotal = 0.0;
        for(CarritoItemModel item : carritoItemModelList){
            cantidadTotalProductos += item.getCantidad();
            subTotal += item.getSubTotal();
        }
        lblTotalCarritoItem.setText(Util.convertirFormatoDinero(subTotal));
        ((PrincipalActivity)getActivity()).actualizarCantidadCarrito(cantidadTotalProductos);
    }

    public interface clickOperacionesCantidad{
        void agregarClickListener(CarritoItemModel carritoItemModel);
        void agregarEnUnoClickListener(CarritoItemModel carritoItemModel);
        void disminuirEnUnoClickListener(CarritoItemModel carritoItemModel);
        void removerClickListener(CarritoItemModel carritoItemModel);
    }

    @Subscribe
    public void agregarProductoDTO(ProductDTO productDTO){
        cargarData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }

}
