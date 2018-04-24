package com.marcosilv7.narutodelivery;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.FrameLayout;

import com.marcosilv7.narutodelivery.events.TabSelectedEvent;
import com.marcosilv7.narutodelivery.ui.base.BaseMainFragment;
import com.marcosilv7.narutodelivery.ui.fragments.cuarto.CuartoFragment;
import com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos.MiPerfilFragment;
import com.marcosilv7.narutodelivery.ui.fragments.primero.hijos.CategoriasFragment;
import com.marcosilv7.narutodelivery.ui.fragments.primero.PrimeroFragment;
import com.marcosilv7.narutodelivery.ui.fragments.primero.hijos.ProductosFragment;
import com.marcosilv7.narutodelivery.ui.fragments.segundo.SegundoFragment;
import com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos.CarritoFragment;
import com.marcosilv7.narutodelivery.ui.fragments.tercero.hijos.DeliveryFragment;
import com.marcosilv7.narutodelivery.ui.fragments.tercero.TerceroFragment;
import com.marcosilv7.narutodelivery.ui.view.BottomBar;
import com.marcosilv7.narutodelivery.ui.view.BottomBarTab;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class PrincipalActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener {


    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private SupportFragment[] mFragments = new SupportFragment[4];
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        SupportFragment firstFragment = findFragment(PrimeroFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = PrimeroFragment.newInstance();
            mFragments[SECOND] = SegundoFragment.newInstance();
            mFragments[THIRD] = TerceroFragment.newInstance();
            mFragments[FOUR] = CuartoFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR]);
        } else {
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(SegundoFragment.class);
            mFragments[THIRD] = findFragment(TerceroFragment.class);
            mFragments[FOUR] = CuartoFragment.newInstance();
        }
        initView();
    }

    private void initView() {
        mBottomBar
                .addItem(new BottomBarTab(this, R.drawable.ic_home_black_24dp, "Inicio"))
                .addItem(new BottomBarTab(this, R.drawable.ic_shopping_cart_black_24dp, "Carrito"))
                .addItem(new BottomBarTab(this, R.drawable.ic_motorcycle_black_24dp, "Delivery"))
                 .addItem(new BottomBarTab(this, R.drawable.ic_person_black_24dp, "Mi Cuenta"));
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                final SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();
                if (count > 1) {
                    if (currentFragment instanceof PrimeroFragment) {
                        currentFragment.popToChild(CategoriasFragment.class, false);
                    } else if (currentFragment instanceof SegundoFragment) {
                        currentFragment.popToChild(CarritoFragment.class, false);
                    } else if (currentFragment instanceof TerceroFragment) {
                        currentFragment.popToChild(DeliveryFragment.class, false);
                    }else if (currentFragment instanceof CuartoFragment) {
                        currentFragment.popToChild(MiPerfilFragment.class, false);
                    }

                    return;
                }
            }
        });
    }


    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }


}
