package com.marcosilv7.narutodelivery.constantes;

public interface Constantes {
    String PARAM_LATITUD="%latitud%";
    String PARAM_LONGITUD="%longitud%";
    String TIPO_TOKEN="Beaber ";
    String HEADER_TOKEN="X-Authorization";
    int DURACION_SPLASH=2000;
    String URL_PREVIEW_GMAP="https://maps.googleapis.com/maps/api/staticmap?" +
            "size=600x300&maptype=roadma&markers=color:red%7Clabel:M%7C"+PARAM_LATITUD+","+PARAM_LONGITUD+"&key=";
    String API_KEY_GOOGLE ="AIzaSyBFt0L2H-2lhSU8rw3ieNzAXHbV6_il94c";
}
