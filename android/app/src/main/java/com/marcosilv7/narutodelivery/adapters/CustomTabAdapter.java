package com.marcosilv7.narutodelivery.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.marcosilv7.narutodelivery.ui.fragments.primero.hijos.tab.ProductoCategoriaFragment;

public class CustomTabAdapter  extends FragmentPagerAdapter {

    private String[] mTitulos;

    public CustomTabAdapter(FragmentManager fm, String... mTitulos) {
        super(fm);
        this.mTitulos = mTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return ProductoCategoriaFragment.newInstance(1L);
            case 1:
                return ProductoCategoriaFragment.newInstance(4L);
            case 2 :
                return ProductoCategoriaFragment.newInstance(3L);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTitulos.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitulos[position];
    }
}
