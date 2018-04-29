package com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.constantes.Constantes;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;
import com.marcosilv7.narutodelivery.ui.fragments.primero.hijos.CategoriasFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AgregarDireccionFragment extends BaseBackFragment {

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.previewMapaAgregarDireccion)
    ImageView previewMapaAgregarDireccion;

    static String KEY_PLACE_LATLONG ="key_latlong";
    static String KEY_PLACE_ADDRESS ="key_address";
    static String KEY_PLACE_NAME ="key_name";

    public AgregarDireccionFragment() {

    }

    public static AgregarDireccionFragment newInstance(Place place) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_PLACE_LATLONG,place.getLatLng());
        args.putString(KEY_PLACE_ADDRESS,place.getAddress().toString());
        args.putString(KEY_PLACE_NAME,place.getName().toString());
        AgregarDireccionFragment fragment = new AgregarDireccionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_agregar_direccion, container, false);
        ButterKnife.bind(this,view);
        toolbarTitle.setText("Agregar Direccion de Entrega");
        LatLng latLng =  getArguments().getParcelable(KEY_PLACE_LATLONG);
        if(latLng!=null){
            String url = Constantes.URL_PREVIEW_GMAP;
            url = url.replace(Constantes.PARAM_LATITUD,latLng.latitude+"");
            url = url.replace(Constantes.PARAM_LONGITUD,latLng.longitude+"");
            Glide.with(getActivity())
                    .load(url+Constantes.API_KEY_GOOGLE)
                    .apply(RequestOptions.centerCropTransform().placeholder(R.drawable.ic_panorama_gray_24dp))
                    .into(previewMapaAgregarDireccion);
        }
        return view;
    }

}
