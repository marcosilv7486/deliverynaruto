package com.marcosilv7.narutodelivery;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    PrefenciasUsuario prefenciasUsuario;

    @Override
    public void onTokenRefresh() {
        prefenciasUsuario = new PrefenciasUsuario(getApplicationContext());
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(MyFirebaseInstanceIDService.class.getSimpleName(), "Refreshed token: " + refreshedToken);
        Log.d(MyFirebaseInstanceIDService.class.getSimpleName(), "Refreshed token: " + prefenciasUsuario.obtenerIdUsuario());
    }
}
