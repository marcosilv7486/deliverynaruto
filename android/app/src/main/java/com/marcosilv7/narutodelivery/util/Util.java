package com.marcosilv7.narutodelivery.util;

import android.content.Context;

import com.marcosilv7.narutodelivery.api.ServiceGenerator;
import com.marcosilv7.narutodelivery.dto.error.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class Util {

    public static ErrorResponse parseError(Response<?> response, Context context) {
        Converter<ResponseBody, ErrorResponse> converter =
                ServiceGenerator.retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);

        ErrorResponse error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorResponse();
        }
        return error;
    }

    public static Date generarFecha(String fecha, String formato){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formato);
            return formatter.parse(fecha);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String convertirFormatoDinero(double monto){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter.format(monto);
    }
}
