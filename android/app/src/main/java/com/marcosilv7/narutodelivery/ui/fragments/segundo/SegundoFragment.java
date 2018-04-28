package com.marcosilv7.narutodelivery.ui.fragments.segundo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.base.BaseMainFragment;
import com.marcosilv7.narutodelivery.ui.fragments.primero.hijos.CategoriasFragment;
import com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos.CarritoFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SegundoFragment extends BaseMainFragment {


    public SegundoFragment() {
        // Required empty public constructor
    }

    public static SegundoFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SegundoFragment fragment = new SegundoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_segundo, container, false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(CarritoFragment.class) == null) {
            loadRootFragment(R.id.fl_second_container, CarritoFragment.newInstance());
        }
    }

}
