package com.marcosilv7.narutodelivery.realm.models;

import java.math.BigDecimal;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CarritoModel extends RealmObject {

    private int cantidadItems;
    private double total;

    private RealmList<CarritoItemModel> items;

    public int getCantidadItems() {
        return cantidadItems;
    }

    public void setCantidadItems(int cantidadItems) {
        this.cantidadItems = cantidadItems;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public RealmList<CarritoItemModel> getItems() {
        if(items == null)
            items = new RealmList<CarritoItemModel>();
        return items;
    }

    public void setItems(RealmList<CarritoItemModel> items) {
        this.items = items;
    }
}
