package com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.DireccionAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.AddressDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DireccionesFragment extends BaseBackFragment {

    public static int PLACE_PICKER_REQUEST = 1;
    public static int REFRESH_DATA_REQUEST = 2;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fabAddDireccionEntrega)
    FloatingActionButton fabAddDireccionEntrega;

    @BindView(R.id.rvListaDirecciones)
    RecyclerView recyclerView;

    @BindView(R.id.layoutSinDirecciones)
    LinearLayout layoutSinDirecciones;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    RecyclerView.LayoutManager layoutManager;
    DireccionAdapter direccionAdapter;
    PrefenciasUsuario prefenciasUsuario;

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
        initToolbarNav(toolbar);
        layoutManager = new LinearLayoutManager(getActivity());
        prefenciasUsuario = new PrefenciasUsuario(getActivity());
        direccionAdapter = new DireccionAdapter(new ArrayList<AddressDTO>(), getActivity(), new OperacionesDireccionEntrega() {
            @Override
            public void eliminar(AddressDTO data) {
                eliminarDireccionPorUsuario(data,prefenciasUsuario.obtenerIdUsuario());
            }

            @Override
            public void marcarComoFavorito(AddressDTO data) {
                marcarFaforitoDireccionPorUsuario(data,prefenciasUsuario.obtenerIdUsuario());
            }

            @Override
            public void editar(AddressDTO data) {

            }

        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(direccionAdapter);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    private void marcarFaforitoDireccionPorUsuario(final AddressDTO data, final Long userId) {
        data.setFavorite(!data.isFavorite());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Esta seguro de modificar la direccion "+data.getAlias()+" ?")
                .setTitle("Modificar direccion");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Call<AddressDTO> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                        .modificarDireccionPorUsuario(userId,data.getId(),data);
                call.enqueue(new Callback<AddressDTO>() {
                    @Override
                    public void onResponse(Call<AddressDTO> call, Response<AddressDTO> response) {
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        if(response.code() == 200){
                            Snackbar.make(recyclerView,"Direccion actualizada correctamente",Snackbar.LENGTH_SHORT).show();
                            cargarData();
                        }
                    }
                    @Override
                    public void onFailure(Call<AddressDTO> call, Throwable t) {
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                });
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

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        cargarData();
    }

    private void cargarData() {
        progressBar.setVisibility(View.VISIBLE);
        Call<ArrayList<AddressDTO>> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                .obtenerDireccionesPorUsuario(prefenciasUsuario.obtenerIdUsuario());
        call.enqueue(new Callback<ArrayList<AddressDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AddressDTO>> call, Response<ArrayList<AddressDTO>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.code() == 200){
                    direccionAdapter.actualizarData(response.body());
                    if(response.body().isEmpty()){
                        layoutSinDirecciones.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else {
                        layoutSinDirecciones.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AddressDTO>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void eliminarDireccionPorUsuario(final AddressDTO data, final Long userId){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Esta seguro de eliminar la direccion "+data.getAlias()+" ?")
                .setTitle("Eliminar direccion");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Call<Void> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                        .eliminarDireccionPorUsuario(userId,data.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        if(response.code() == 200){
                            Snackbar.make(recyclerView,"Direccion eliminada correctamente",Snackbar.LENGTH_SHORT).show();
                            cargarData();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                });
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
                startForResult(agregarDireccionFragment,REFRESH_DATA_REQUEST);
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == REFRESH_DATA_REQUEST){
            cargarData();
        }
    }

    public interface OperacionesDireccionEntrega{
        void eliminar(AddressDTO data);
        void marcarComoFavorito(AddressDTO data);
        void editar(AddressDTO data);
    }
}
