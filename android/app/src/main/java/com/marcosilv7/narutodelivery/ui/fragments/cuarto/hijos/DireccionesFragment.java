package com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DireccionesFragment extends BaseBackFragment {

    int PLACE_PICKER_REQUEST = 1;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.fabAddDireccionEntrega)
    FloatingActionButton fabAddDireccionEntrega;

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
        ButterKnife.bind(this,view);
        toolbarTitle.setText("Mis Direcciones de Entrega");
        return view;
    }

    @OnClick(R.id.fabAddDireccionEntrega)
    public void onClickAgregarDireccion(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace( getActivity(),data);
                AgregarDireccionFragment agregarDireccionFragment = AgregarDireccionFragment.newInstance(place);
                start(agregarDireccionFragment);
            }
        }
    }

}
