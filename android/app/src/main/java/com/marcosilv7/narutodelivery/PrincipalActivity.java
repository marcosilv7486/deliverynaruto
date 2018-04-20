package com.marcosilv7.narutodelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;
import com.marcosilv7.narutodelivery.realm.querys.QueryCarrito;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrincipalActivity extends AppCompatActivity {

    public PrefenciasUsuario prefenciasUsuario;
    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    CategoriasFragment categoriasFragment;
    CarritoFragment carritoFragment;
    DeliveryFragment deliveryFragment;

    public static final String PRODUCTOS_FRAGMENT = "PRODUCTOS_FRAGMEN";
    public static final String CARRITO_FRAGMENT = "CARRITO_FRAGMENT";
    public static final String DELIVERY_FRAGMENT = "DELIVERY_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        categoriasFragment = new CategoriasFragment();
        carritoFragment = new CarritoFragment();
        deliveryFragment = new DeliveryFragment();
        prefenciasUsuario = new PrefenciasUsuario(this);
        //Iniciar el fragment Primario
        setFragment(categoriasFragment,PRODUCTOS_FRAGMENT);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id. navigation_productos : {
                        setFragment(categoriasFragment,PRODUCTOS_FRAGMENT);
                        return true;
                    }
                    case R.id.navigation_carrito : {
                        setFragment(carritoFragment,CARRITO_FRAGMENT);
                        return true;
                    }
                    case R.id.navigation_delivery_actual : {
                        setFragment(deliveryFragment,DELIVERY_FRAGMENT);
                        return true;
                    }
                   /* case R.id.navigation_logout : {
                        logout();
                        return true;
                    }*/
                    default: return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment,String nombreFragmento){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment, nombreFragmento);
        transaction.commit();
    }
}
