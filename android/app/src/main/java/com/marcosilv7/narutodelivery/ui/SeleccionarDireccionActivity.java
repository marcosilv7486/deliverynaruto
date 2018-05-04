package com.marcosilv7.narutodelivery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.adapters.EscogerDireccionAdapter;
import com.marcosilv7.narutodelivery.api.NarutoApi;
import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.constantes.Constantes;
import com.marcosilv7.narutodelivery.dto.AddressDTO;
import com.marcosilv7.narutodelivery.dto.OrderDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marcosilv7.narutodelivery.constantes.Constantes.ORDER_DATA;

public class SeleccionarDireccionActivity extends SupportActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_direccion);
        ButterKnife.bind(this);
        toolbarTitle.setText("Escoger dirección");
        prefenciasUsuario = new PrefenciasUsuario(this);
        layoutManager = new LinearLayoutManager(this);
        escogerDireccionAdapter = new EscogerDireccionAdapter(new ArrayList<AddressDTO>(), this, new SeleccionarDireccionActivity.eventos() {
            @Override
            public void onClickCardView(AddressDTO addressDTO) {
                iniciarActivityFormapago(addressDTO);
            }
        });
        rvListaDirecciones.setLayoutManager(layoutManager);
        rvListaDirecciones.setAdapter(escogerDireccionAdapter);
        rvListaDirecciones.setHasFixedSize(true);
        Intent intent = getIntent();
        if(intent != null){
            String json = intent.getStringExtra(ORDER_DATA);
            orderDTO = new Gson().fromJson(json,OrderDTO.class);
            cargarData();
        }
    }

    private void cargarData() {
        Call<ArrayList<AddressDTO>> call = ServiceGenerator.createService(NarutoApi.class,this)
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

    private void iniciarActivityFormapago(AddressDTO addressDTO) {
        if(orderDTO != null){
            orderDTO.setUserAddress(addressDTO.getAddress());
            orderDTO.setUserPhone(addressDTO.getPhone());
            orderDTO.setLatUserAddress(addressDTO.getLatitude());
            orderDTO.setLonUserAddress(addressDTO.getLongitude());
        }
        Intent intent = new Intent(this,SeleccionFormaPagoActivity.class);
        intent.putExtra(Constantes.ORDER_DATA,new Gson().toJson(orderDTO));
        startActivity(intent);
    }

    public interface eventos{
        void onClickCardView(AddressDTO addressDTO);
    }
}
