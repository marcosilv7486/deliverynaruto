package com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiPerfilFragment extends BaseBackFragment {


    public MiPerfilFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_mi_perfil, container, false);
    }

}
