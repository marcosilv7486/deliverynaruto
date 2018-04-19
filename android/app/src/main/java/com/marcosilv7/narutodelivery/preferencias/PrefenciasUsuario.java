package com.marcosilv7.narutodelivery.preferencias;

import android.content.Context;
import android.content.SharedPreferences;

import com.marcosilv7.narutodelivery.dto.LoginResponseDTO;
import com.marcosilv7.narutodelivery.dto.TokenDTO;

public class PrefenciasUsuario {

    public static final String PREFERENCIAS_SEGURIDAD="seguridad";
    public static final String PREFERENCIAS_KEY_USER_ID="user_id";
    public static final String PREFERENCIAS_KEY_USER_ACCESS_TOKEN="user_access_token";
    public static final String PREFERENCIAS_KEY_USER_REFRESH_TOKEN="refresh_token";
    public static final String PREFERENCIAS_KEY_USER_FULL_NAME="user_full_name";
    public static final String PREFERENCIAS_KEY_USER_AVATAR="user_avatar";
    public static final String PREFERENCIAS_KEY_LOGIN="login";

    private SharedPreferences sharedPreferences;

    public PrefenciasUsuario(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCIAS_SEGURIDAD,Context.MODE_PRIVATE);
    }

    public void guardarDatosLogin(LoginResponseDTO loginResponseDTO){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCIAS_KEY_USER_ID,loginResponseDTO.getData().getUserId().toString());
        editor.putString(PREFERENCIAS_KEY_USER_ACCESS_TOKEN,loginResponseDTO.getData().getToken());
        editor.putString(PREFERENCIAS_KEY_USER_REFRESH_TOKEN,loginResponseDTO.getData().getRefreshtoken());
        editor.putString(PREFERENCIAS_KEY_USER_FULL_NAME,loginResponseDTO.getData().getFullName());
        editor.putString(PREFERENCIAS_KEY_USER_AVATAR,loginResponseDTO.getData().getAvatar());
        editor.putBoolean(PREFERENCIAS_KEY_LOGIN,true);
        editor.apply();
    }

    public void eliminarDatosLogin() {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCIAS_KEY_USER_ID,"");
        editor.putString(PREFERENCIAS_KEY_USER_ACCESS_TOKEN,"");
        editor.putString(PREFERENCIAS_KEY_USER_REFRESH_TOKEN,"");
        editor.putString(PREFERENCIAS_KEY_USER_FULL_NAME,"");
        editor.putString(PREFERENCIAS_KEY_USER_AVATAR,"");
        editor.putBoolean(PREFERENCIAS_KEY_LOGIN,false);
        editor.apply();
    }

    public void actualizarAccessToken(String accessToken){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCIAS_KEY_USER_ACCESS_TOKEN,accessToken);
        editor.apply();
    }

    public String obtenerAccessToken(){
        return sharedPreferences.getString(PREFERENCIAS_KEY_USER_ACCESS_TOKEN,"");
    }

    public String obtenerRefreshToken(){
        return sharedPreferences.getString(PREFERENCIAS_KEY_USER_REFRESH_TOKEN,"");
    }

    public boolean verificarLogin(){
        return sharedPreferences.getBoolean(PREFERENCIAS_KEY_LOGIN,false);
    }
}
