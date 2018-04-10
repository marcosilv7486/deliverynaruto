package com.marcosilv7.narutodelivery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrincipalActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    ProductosFragment productosFragment;
    CarritoFragment carritoFragment;
    DeliveryFragment deliveryFragment;

    public static final String PRODUCTOS_FRAGMENT = "PRODUCTOS_FRAGMENT";
    public static final String CARRITO_FRAGMENT = "CARRITO_FRAGMENT";
    public static final String DELIVERY_FRAGMENT = "DELIVERY_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        productosFragment = new ProductosFragment();
        carritoFragment = new CarritoFragment();
        deliveryFragment = new DeliveryFragment();
        //Iniciar el fragment Primario
        setFragment(productosFragment,PRODUCTOS_FRAGMENT);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id. navigation_productos : {
                        setFragment(productosFragment,PRODUCTOS_FRAGMENT);
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