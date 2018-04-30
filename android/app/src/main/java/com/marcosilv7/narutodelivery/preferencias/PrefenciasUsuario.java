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
    public static final String PREFERENCIAS_KEY_USER_DIRECCION="direccion";
    public static final String PREFERENCIAS_KEY_USER_DISTRITO="distrito";
    public static final String PREFERENCIAS_KEY_USER_TELEFONO="telefono";

    private SharedPreferences sharedPreferences;

    public PrefenciasUsuario(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCIAS_SEGURIDAD,Context.MODE_PRIVATE);
    }

    public void guardarDatosLogin(LoginResponseDTO loginResponseDTO){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCIAS_KEY_USER_ID,loginResponseDTO.getData().getUserId().toString());
        editor.putString(PREFERENCIAS_KEY_USER_ACCESS_TOKEN,loginResponseDTO.getData().getToken());
        editor.putString(PREFERENCIAS_KEY_USER_REFRESH_TOKEN,loginResponseDTO.getData().getRefreshtoken());
        editor.putString(PREFERENCIAS_KEY_USER_FULL_NAME,"Marcos Silverio");
        editor.putString(PREFERENCIAS_KEY_USER_AVATAR,loginResponseDTO.getData().getAvatar());
        editor.putString(PREFERENCIAS_KEY_USER_DIRECCION,"Urb Matellini 2da Etapa");
        editor.putString(PREFERENCIAS_KEY_USER_DISTRITO,"Chorrillos");
        editor.putString(PREFERENCIAS_KEY_USER_TELEFONO,"957290129");
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
        editor.putString(PREFERENCIAS_KEY_USER_FULL_NAME,"");
        editor.putString(PREFERENCIAS_KEY_USER_DIRECCION,"");
        editor.putString(PREFERENCIAS_KEY_USER_DISTRITO,"");
        editor.putString(PREFERENCIAS_KEY_USER_TELEFONO,"");
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

    public String obtenerNombre() {
        return sharedPreferences.getString(PREFERENCIAS_KEY_USER_FULL_NAME,"");
    }

    public String obtenerDireccion() {
        return sharedPreferences.getString(PREFERENCIAS_KEY_USER_DIRECCION,"");
    }

    public String obtenerDistrito() {
        return sharedPreferences.getString(PREFERENCIAS_KEY_USER_DISTRITO,"");
    }

    public String obtenerTelefono() {
        return sharedPreferences.getString(PREFERENCIAS_KEY_USER_TELEFONO,"");
    }

    public Long obtenerIdUsuario() {
        return Long.valueOf(sharedPreferences.getString(PREFERENCIAS_KEY_USER_ID,"0"));
    }

    public void actualizardireccion(String nombre, String direccion, String distrito, String telefono){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCIAS_KEY_USER_FULL_NAME,nombre);
        editor.putString(PREFERENCIAS_KEY_USER_DIRECCION,direccion);
        editor.putString(PREFERENCIAS_KEY_USER_DISTRITO,distrito);
        editor.putString(PREFERENCIAS_KEY_USER_TELEFONO,telefono);
        editor.apply();
    }

        public void eliminardireccion(){
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCIAS_KEY_USER_FULL_NAME,"");
        editor.putString(PREFERENCIAS_KEY_USER_DIRECCION,"");
        editor.putString(PREFERENCIAS_KEY_USER_DISTRITO,"");
        editor.putString(PREFERENCIAS_KEY_USER_TELEFONO,"");
        editor.apply();
    }
}
