package com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.CarritoItemAdapter;
import com.marcosilv7.narutodelivery.constantes.Constantes;
import com.marcosilv7.narutodelivery.dto.OrderDTO;
import com.marcosilv7.narutodelivery.dto.OrderDetailDTO;
import com.marcosilv7.narutodelivery.dto.ProductDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.ui.PrincipalActivity;
import com.marcosilv7.narutodelivery.ui.SeleccionarDireccionActivity;
import com.marcosilv7.narutodelivery.util.Util;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
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

    @BindView(R.id.btnProcederDireccionCarritoCompras)
    Button btnProcederDireccionCarritoCompras;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    RecyclerView.LayoutManager layoutManager;
    CarritoItemAdapter carritoItemAdapter;
    List<CarritoItemModel> carritoItemModelList;
    PrefenciasUsuario prefenciasUsuario;

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
        prefenciasUsuario = new PrefenciasUsuario(getActivity());
        EventBusActivityScope.getDefault(_mActivity).register(this);
        initView(view);
        cargarData();
        return view;
    }

    private void initView(View view) {
        toolbarTitle.setText("Mi Pedido ("+cantidadTotalProductos+")");
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
    }

    @OnClick(R.id.btnProcederDireccionCarritoCompras)
    public void onClickProcederConElPago(){
        if(cantidadTotalProductos >0 ){
            //LLenar Carrito DTO
            OrderDTO data = new OrderDTO();
            data.setUserId(prefenciasUsuario.obtenerIdUsuario());
            data.setUserPhone(prefenciasUsuario.obtenerTelefono());
            data.setUserFullName(prefenciasUsuario.obtenerNombre());
            data.setTotal(new BigDecimal(subTotal+""));
            for(CarritoItemModel carritoItem : carritoItemModelList){
                OrderDetailDTO itemDto = new OrderDetailDTO();
                itemDto.setDescription(carritoItem.getNombreProducto());
                itemDto.setDescriptionImage(carritoItem.getImage());
                itemDto.setQuantity(carritoItem.getCantidad());
                itemDto.setProductId(carritoItem.getIdProducto());
                itemDto.setUnitPrice(new BigDecimal(carritoItem.getPrecio()+""));
                itemDto.setTotal(new BigDecimal(carritoItem.getSubTotal()+""));
                data.getItems().add(itemDto);
            }
            Intent intent = new Intent(getActivity(), SeleccionarDireccionActivity.class);
            intent.putExtra(Constantes.ORDER_DATA, new Gson().toJson(data));
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(),"Debe agregar por lo menos un item",Toast.LENGTH_SHORT).show();
        }
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

    private void remover(final CarritoItemModel carritoItemModel){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Esta seguro de eliminar el item?")
                .setTitle("Remover item");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                QueryCarrito.removerItemCarrito(carritoItemModel);
                cargarData();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
        toolbarTitle.setText("Mi Pedido ("+cantidadTotalProductos+")");
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
