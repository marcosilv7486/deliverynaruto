package com.marcosilv7.narutodelivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Fernando on 22/04/2018.
 */

public class TipoPagoFragment extends Fragment {
    public static TipoPagoFragment newInstance() {

        Bundle args = new Bundle();

        TipoPagoFragment fragment = new TipoPagoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    Button btnDetallePago;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tipo_pago, container, false);
        btnDetallePago = (Button) view.findViewById(R.id.btnProceder);

        btnDetallePago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout, DetailsProductosFragment.newInstance(), "DetailsProductosFragment");
                transaction.commit();
            }
        });

        return view;

    }
}
