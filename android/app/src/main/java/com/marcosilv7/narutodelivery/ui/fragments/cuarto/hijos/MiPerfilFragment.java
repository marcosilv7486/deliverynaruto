package com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.ui.LoginActivity;
import com.marcosilv7.narutodelivery.ui.PrincipalActivity;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;


public class MiPerfilFragment extends BaseBackFragment {

    //Button button;

    TextView btnLogoutMiperfil;
    PrefenciasUsuario prefenciasUsuario;

    TextView textViewDireccionMiPerfil;


    public MiPerfilFragment() {
    }

    public static MiPerfilFragment newInstance() {
        Bundle args = new Bundle();
        MiPerfilFragment fragment = new MiPerfilFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mi_perfil, container, false);
        btnLogoutMiperfil = view.findViewById(R.id.btnLogoutMiperfil);
        prefenciasUsuario = new PrefenciasUsuario(getActivity());
        textViewDireccionMiPerfil = view.findViewById(R.id.btnDireccionesMiperfil);
        textViewDireccionMiPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DireccionesFragment fragment = DireccionesFragment.newInstance();
                start(fragment);
            }
        });
        btnLogoutMiperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        /*button = view.findViewById(R.id.btnSitio);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });*/
        return view;
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Esta seguro de cerrar la sesion?")
                .setTitle("Cerrar sesion");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                prefenciasUsuario.eliminarDatosLogin();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
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


}
