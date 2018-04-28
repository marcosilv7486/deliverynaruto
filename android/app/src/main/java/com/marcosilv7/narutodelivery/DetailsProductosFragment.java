package com.marcosilv7.narutodelivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.adapters.CarritoDetalleAdapter;
import com.marcosilv7.narutodelivery.adapters.CarritoItemAdapter;
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.realm.models.CarritoModel;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;
import com.marcosilv7.narutodelivery.util.Util;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Fernando on 22/04/2018.
 */

public class DetailsProductosFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CarritoModel carritoModel;
    CarritoDetalleAdapter carritoItemAdapter;
    TextView lblValorTotal;

    public static DetailsProductosFragment newInstance() {

        Bundle args = new Bundle();

        DetailsProductosFragment fragment = new DetailsProductosFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_productos, container, false);
        lblValorTotal = view.findViewById(R.id.lblValorTotal);
        recyclerView = view.findViewById(R.id.recyclerDetailsProductos);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        carritoItemAdapter = new CarritoDetalleAdapter(getActivity(), new ArrayList<CarritoItemModel>());
        //cargarData();
        recyclerView.setAdapter(carritoItemAdapter);
        recyclerView.setHasFixedSize(true);
        cargarData();
        return view;
    }

    void cargarData(){
        carritoModel = QueryCarrito.obtenerCarritoActual();
        if(carritoModel == null || carritoModel.getItems().isEmpty()){

        }else {
            ArrayList<CarritoItemModel> data = new ArrayList<>(carritoModel.getItems());
            carritoItemAdapter.actualizarData(data);
            lblValorTotal.setText(Util.convertirFormatoDinero(carritoModel.getTotal()));
        }
    }
}
