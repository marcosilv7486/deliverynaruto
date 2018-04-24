package com.marcosilv7.narutodelivery.ui.fragments.tercero;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.base.BaseMainFragment;
import com.marcosilv7.narutodelivery.ui.fragments.tercero.hijos.DeliveryFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TerceroFragment extends BaseMainFragment {


    public TerceroFragment() {
        // Required empty public constructor
    }

    public static TerceroFragment newInstance() {

        Bundle args = new Bundle();

        TerceroFragment fragment = new TerceroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tercero, container, false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(DeliveryFragment.class) == null) {
            loadRootFragment(R.id.fl_third_container, DeliveryFragment.newInstance());
        }
    }
}
