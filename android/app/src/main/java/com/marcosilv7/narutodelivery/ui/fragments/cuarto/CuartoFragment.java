package com.marcosilv7.narutodelivery.ui.fragments.cuarto;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.base.BaseMainFragment;
import com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos.MiPerfilFragment;
import com.marcosilv7.narutodelivery.ui.fragments.primero.hijos.CategoriasFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CuartoFragment extends BaseMainFragment {


    public CuartoFragment() {
        // Required empty public constructor
    }

    public static CuartoFragment newInstance() {
        Bundle args = new Bundle();
        CuartoFragment fragment = new CuartoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cuarto, container, false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(MiPerfilFragment.class) == null) {
            loadRootFragment(R.id.fl_cuarto_container, MiPerfilFragment.newInstance());
        }
    }

}
