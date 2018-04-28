package com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;

/**
 * Created by Fernando on 22/04/2018.
 */

public class TipoPagoFragment extends BaseBackFragment {
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
                DetailsProductosFragment detailsProductosFragment = DetailsProductosFragment.newInstance();
                start(detailsProductosFragment);
            }
        });

        return view;

    }
}
