package com.marcosilv7.narutodelivery.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.marcosilv7.narutodelivery.BuildConfig;
import com.marcosilv7.narutodelivery.constantes.Constantes;
import com.marcosilv7.narutodelivery.dto.AccessToken;
import com.marcosilv7.narutodelivery.dto.TokenDTO;
import com.marcosilv7.narutodelivery.preferencias.PrefenciasUsuario;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static String BASE_URL = "http://104.236.54.195:8080/narutoapp/api/v1/";
    private static OkHttpClient.Builder httpClient;
    private static Retrofit.Builder builder;
    private static PrefenciasUsuario prefenciasUsuario;

    public static final String TAG = ServiceGenerator.class.getSimpleName();

    public static <S> S createService(Class<S> serviceClass,final Context context) {
        prefenciasUsuario = new PrefenciasUsuario(context);
        httpClient = new OkHttpClient.Builder();
        builder = new Retrofit.Builder()
                .baseUrl(ServiceGenerator.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        //Agregar la seguridad en caso de que expire el accessToken
        httpClient.authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) {
                Log.d(TAG,"Entrando... authenticate");
                if (responseCount(response) >= 2) {
                    return null; // If we've failed 3 times, give up.
                }
                if(response.request().url().toString().contains(NarutoApi.REST_LOGIN)){
                    Log.d(TAG, "authenticate: REST_LOGIN 401");
                    return null;
                }
                if(response.request().url().toString().contains(NarutoApi.REST_REFRESH_TOKEN)){
                    Log.d(TAG, "authenticate: REST_TOKEN 401");
                    return null;
                }
                //Obtener el refresh token
                NarutoApi tokenClient = createService(NarutoApi.class,context);
                Call<TokenDTO> call = tokenClient.obtenerNuevoAccessToken(prefenciasUsuario.obtenerRefreshToken());
                try {
                    Log.d(TAG,"Entrando... obtenerNuevoAccessToken");
                    retrofit2.Response<TokenDTO> tokenResponse = call.execute();
                    if (tokenResponse.code() == 200) {
                        TokenDTO nuevoToken = tokenResponse.body();
                        prefenciasUsuario.actualizarAccessToken(nuevoToken.getToken());
                        return response.request().newBuilder()
                                .header(Constantes.HEADER_TOKEN, Constantes.TIPO_TOKEN + nuevoToken.getToken())
                                .build();
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    return null;
                }
            }

            private int responseCount(Response response) {
                int result = 1;
                while ((response = response.priorResponse()) != null) {
                    result++;
                }
                return result;
            }
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }


}
