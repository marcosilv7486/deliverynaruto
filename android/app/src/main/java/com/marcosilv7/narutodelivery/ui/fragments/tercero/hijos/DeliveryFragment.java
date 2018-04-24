package com.marcosilv7.narutodelivery.ui.fragments.tercero.hijos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcosilv7.narutodelivery.R;

import me.yokeyword.fragmentation.SupportFragment;


public class DeliveryFragment extends SupportFragment {

    public DeliveryFragment() {
    }

    public static DeliveryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DeliveryFragment fragment = new DeliveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery, container, false);
    }


}
