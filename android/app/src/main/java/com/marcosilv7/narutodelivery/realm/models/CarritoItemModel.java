package com.marcosilv7.narutodelivery.realm.models;

import io.realm.RealmObject;

public class CarritoItemModel extends RealmObject {

    private long idProducto;
    private String nombreProducto;
    private String familiaProducto;
    private double precio;
    private Integer cantidad;
    private double subTotal;
    private String image;

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getFamiliaProducto() {
        return familiaProducto;
    }

    public void setFamiliaProducto(String familiaProducto) {
        this.familiaProducto = familiaProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }
}
