package com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.EscogerDireccionAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.constantes.Constantes;
import com.marcosilv7.narutodelivery.dto.AddressDTO;
import com.marcosilv7.narutodelivery.dto.OrderDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.ui.base.BaseBackFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marcosilv7.narutodelivery.constantes.Constantes.ORDER_DATA;


public class SeleccionarDireccionFragment extends BaseBackFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    @BindView(R.id.rvListaDirecciones)
    RecyclerView rvListaDirecciones;

    RecyclerView.LayoutManager layoutManager;
    EscogerDireccionAdapter escogerDireccionAdapter;
    PrefenciasUsuario prefenciasUsuario;
    OrderDTO orderDTO;

    public SeleccionarDireccionFragment() {

    }

    public static SeleccionarDireccionFragment newInstance(OrderDTO data) {
        Bundle args = new Bundle();
        args.putParcelable(Constantes.ORDER_DATA,data);
        SeleccionarDireccionFragment fragment = new SeleccionarDireccionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seleccionar_direccion, container, false);
        ButterKnife.bind(this,view);
        toolbarTitle.setText("Escoger dirección");
        initToolbarNav(toolbar);
        initView(view);
        return view;
    }

    private void initView(View view) {
        prefenciasUsuario = new PrefenciasUsuario(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        escogerDireccionAdapter = new EscogerDireccionAdapter(new ArrayList<AddressDTO>(), getActivity(), new eventos() {
            @Override
            public void onClickCardView(AddressDTO addressDTO) {
                iniciarFragmentSeleccionarPago(addressDTO);
            }
        });
        rvListaDirecciones.setLayoutManager(layoutManager);
        rvListaDirecciones.setAdapter(escogerDireccionAdapter);
        rvListaDirecciones.setHasFixedSize(true);
    }

    private void iniciarFragmentSeleccionarPago(AddressDTO addressDTO) {
        if(orderDTO != null){
            orderDTO.setUserAddress(addressDTO.getAddress());
            orderDTO.setUserPhone(addressDTO.getPhone());
            orderDTO.setLatUserAddress(addressDTO.getLatitude());
            orderDTO.setLonUserAddress(addressDTO.getLongitude());
        }
        start(SeleccionarPagoFragment.newInstance(orderDTO));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        orderDTO = getArguments().getParcelable(ORDER_DATA);
        cargarData();
    }

    private void cargarData() {
        Call<ArrayList<AddressDTO>> call = ServiceGenerator.createService(NarutoApi.class,getActivity())
                .obtenerDireccionesPorUsuario(prefenciasUsuario.obtenerIdUsuario());
        call.enqueue(new Callback<ArrayList<AddressDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AddressDTO>> call, Response<ArrayList<AddressDTO>> response) {
                if(response.code() == 200){
                    escogerDireccionAdapter.actualizarData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AddressDTO>> call, Throwable t) {
            }
        });
    }

    public interface eventos{
        void onClickCardView(AddressDTO addressDTO);
    }
}
