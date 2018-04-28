package com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;


public class DireccionesFragment extends BaseBackFragment {


    public DireccionesFragment() {

    }

    public static DireccionesFragment newInstance() {
        Bundle args = new Bundle();
        DireccionesFragment fragment = new DireccionesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_direcciones, container, false);
        return view;
    }

}
