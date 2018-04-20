package com.marcosilv7.narutodelivery.api;

import com.marcosilv7.narutodelivery.constantes.Constantes;
import com.marcosilv7.narutodelivery.dto.LoginRequestDTO;
import com.marcosilv7.narutodelivery.dto.LoginResponseDTO;
import com.marcosilv7.narutodelivery.dto.PageDTO;
import com.marcosilv7.narutodelivery.dto.ProductDTO;
import com.marcosilv7.narutodelivery.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.dto.ProfileUserDTO;
import com.marcosilv7.narutodelivery.dto.RegisterUserDTO;
import com.marcosilv7.narutodelivery.dto.TokenDTO;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface NarutoApi {

    String REST_LOGIN="security/authenticacion";
    String REST_REFRESH_TOKEN="security/refreshtoken";

    @POST(REST_LOGIN)
    Call<LoginResponseDTO> validarAutenticacion(@Body LoginRequestDTO data);
    @GET(REST_REFRESH_TOKEN)
    Call<TokenDTO> obtenerNuevoAccessToken(@Query("refreshToken") String refreshToken);

    @POST("users")
    Call<ProfileUserDTO> registrarUsuario(@Body RegisterUserDTO data);

    @GET("products")
    Call<PageDTO<ProductDTO>> obtenerProductosPorPaginacion(@QueryMap Map<String,Object> paginacion);

    @GET("families")
    Call<ArrayList<ProductFamilyDTO>> obtenerFamiliasProductos();

    @GET("families/{id}/products")
    Call<ArrayList<ProductDTO>> obtenerProductosPorFamilia(@Path("id") Long idFamilia);


}
