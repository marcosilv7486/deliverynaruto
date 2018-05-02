package com.marcosilv7.narutodelivery.ui.fragments.primero;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.base.BaseMainFragment;
import com.marcosilv7.narutodelivery.ui.fragments.primero.hijos.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrimeroFragment extends BaseMainFragment {


    public PrimeroFragment() {
        // Required empty public constructor
    }

    public static PrimeroFragment newInstance() {
        Bundle args = new Bundle();
        PrimeroFragment fragment = new PrimeroFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_primero, container, false);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(HomeFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, HomeFragment.newInstance());
        }
    }
}
