package com.marcosilv7.narutodelivery;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CustomFragment extends Fragment {
    protected void ocultarLoadingUI(ProgressBar progressBar){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    protected void displayErrorRedMessage(View view, String message,View.OnClickListener clickListener){
        Snackbar snackbar= Snackbar.make(view,message,Snackbar.LENGTH_INDEFINITE)
                .setAction("Reintentar", clickListener);
        snackbar.show();
    }

    protected void displayErrorGeneral(View view, String message){
        Snackbar snackbar= Snackbar.make(view,message,Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    protected void displayMessageGeneral(View view, String message){
        Toast snackbar= Toast.makeText(view.getContext(),message,Toast.LENGTH_SHORT);
        snackbar.show();
    }
}
