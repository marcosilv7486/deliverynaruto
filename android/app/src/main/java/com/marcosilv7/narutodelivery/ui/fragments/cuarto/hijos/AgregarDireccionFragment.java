package com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.AddressDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;
import com.marcosilv7.narutodelivery.util.Util;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AgregarDireccionFragment extends BaseBackFragment implements Validator.ValidationListener {

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.previewMapaAgregarDireccion)
    ImageView previewMapaAgregarDireccion;

    @BindView(R.id.btnAgregarDireccion)
    Button btnAgregarDireccion;

    @BindView(R.id.txtTelefonoAgregarDireccion)
    @Length(max = 10,message = "Maximo 10 numeros")
    TextView txtTelefonoAgregarDireccion;

    @BindView(R.id.txtAliasAgregarDireccion)
    @NotEmpty(message = "Campo requerido")
    @Length(max = 30,message = "Maximo 30 caracteres")
    TextView txtAliasAgregarDireccion;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    AddressDTO data;

    Validator validator;
    PrefenciasUsuario prefenciasUsuario;


    static String KEY_PLACE_LATLONG ="key_latlong";
    static String KEY_PLACE_ADDRESS ="key_address";

    public AgregarDireccionFragment() {

    }

    public static AgregarDireccionFragment newInstance(Place place) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_PLACE_LATLONG,place.getLatLng());
        args.putString(KEY_PLACE_ADDRESS,place.getAddress().toString());
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
        validator = new Validator(this);
        validator.setValidationListener(this);
        prefenciasUsuario = new PrefenciasUsuario(getActivity());
        toolbarTitle.setText("Agregar Direccion de Entrega");
        initToolbarNav(toolbar);
        LatLng latLng =  getArguments().getParcelable(KEY_PLACE_LATLONG);
        String address =  getArguments().getString(KEY_PLACE_ADDRESS);
        if(latLng!=null){
            data = new AddressDTO();
            data.setLatitude(latLng.latitude);
            data.setLongitude(latLng.longitude);
            data.setAddress(address);
            Glide.with(getActivity())
                    .load(Util.obtenerUrlMapaStatic(data.getLatitude(),data.getLongitude()))
                    .apply(RequestOptions.centerCropTransform().placeholder(R.drawable.ic_panorama_gray_24dp))
                    .into(previewMapaAgregarDireccion);
        }
        return view;
    }

    @OnClick(R.id.btnAgregarDireccion)
    public void intentarGuardar(){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        data.setAlias(txtAliasAgregarDireccion.getText().toString());
        data.setPhone(txtTelefonoAgregarDireccion.getText().toString());
        Call<AddressDTO> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                .crearDireccionPorUsuario(prefenciasUsuario.obtenerIdUsuario(),data);
        call.enqueue(new Callback<AddressDTO>() {
            @Override
            public void onResponse(Call<AddressDTO> call, Response<AddressDTO> response) {
                if(response.code() == 200){
                    setFragmentResult(DireccionesFragment.REFRESH_DATA_REQUEST,null);
                    start(DireccionesFragment.newInstance(),2);
                }
            }

            @Override
            public void onFailure(Call<AddressDTO> call, Throwable t) {

            }
        });

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

}
