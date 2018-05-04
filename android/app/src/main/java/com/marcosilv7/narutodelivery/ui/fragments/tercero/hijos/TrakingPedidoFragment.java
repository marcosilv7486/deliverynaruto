package com.marcosilv7.narutodelivery.ui.fragments.tercero.hijos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcosilv7.narutodelivery.R;

import butterknife.ButterKnife;


public class TrakingPedidoFragment extends Fragment {


    public static TrakingPedidoFragment newInstance() {
        Bundle args = new Bundle();
        TrakingPedidoFragment fragment = new TrakingPedidoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TrakingPedidoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_traking_pedido, container, false);
        ButterKnife.bind(this,view);
        return view;
    }



}
