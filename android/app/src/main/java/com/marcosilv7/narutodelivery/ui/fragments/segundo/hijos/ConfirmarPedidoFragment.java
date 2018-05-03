package com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.DetalleProductoGrillaAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.OrderDTO;
import com.marcosilv7.narutodelivery.dto.OrderDetailDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.ui.PedidoEnviadoActivity;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;
import com.marcosilv7.narutodelivery.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marcosilv7.narutodelivery.constantes.Constantes.ORDER_DATA;


public class ConfirmarPedidoFragment extends BaseBackFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.lblSubtotal)
    TextView lblSubtotal;

    @BindView(R.id.txtNombreCompleto)
    TextView txtNombreCompleto;

    @BindView(R.id.txtTelefono)
    TextView txtTelefono;

    @BindView(R.id.txtDireccionEntrega)
    TextView txtDireccionEntrega;

    @BindView(R.id.txtFormaPago)
    TextView txtFormaPago;

    @BindView(R.id.txtTipoFacturacion)
    TextView txtTipoFacturacion;

    @BindView(R.id.btnRealizarPedido)
    Button btnRealizarPedido;

    OrderDTO orderDTO;

    PrefenciasUsuario prefenciasUsuario;
    DetalleProductoGrillaAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public static ConfirmarPedidoFragment newInstance(OrderDTO data) {
        Bundle args = new Bundle();
        args.putParcelable(ORDER_DATA,data);
        ConfirmarPedidoFragment fragment = new ConfirmarPedidoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ConfirmarPedidoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmar_pedido, container, false);
        ButterKnife.bind(this,view);
        toolbarTitle.setText("Confirmar Orden");
        initToolbarNav(toolbar);
        initView(view);
        return view;
    }

    private void initView(View view) {
        prefenciasUsuario = new PrefenciasUsuario(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new DetalleProductoGrillaAdapter(getActivity(), new ArrayList<OrderDetailDTO>());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        orderDTO = getArguments().getParcelable(ORDER_DATA);
        if(orderDTO != null){
            lblSubtotal.setText(Util.convertirFormatoDinero(orderDTO.getTotal().doubleValue()));
            txtDireccionEntrega.setText(orderDTO.getUserAddress());
            txtNombreCompleto.setText(orderDTO.getUserFullName());
            txtTelefono.setText(orderDTO.getUserPhone());
            txtFormaPago.setText(orderDTO.getPaymentType());
            txtTipoFacturacion.setText(orderDTO.getInvoiceType());
            adapter.actualizarData(orderDTO.getItems());
        }
    }

    @OnClick(R.id.btnRealizarPedido)
    public void onClickButton(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Esta seguro de realizar el pedido ? , una vez enviado ya no se podra cancelar.")
                .setTitle("Realizar pedido");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enviarPedido();
            }

        });
        builder.show();
    }

    private void enviarPedido() {
        Call<OrderDTO> call = ServiceGenerator.createService(NarutoApi.class,getActivity()).registrarOrden(orderDTO);
        call.enqueue(new Callback<OrderDTO>() {
            @Override
            public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                if(response.code() == 200){
                    QueryCarrito.limpiarCarrito();
                    //start(DeliveryFragment.newInstance(),3);
                    Intent intent = new Intent(getActivity(), PedidoEnviadoActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<OrderDTO> call, Throwable t) {

            }
        });
    }
}
