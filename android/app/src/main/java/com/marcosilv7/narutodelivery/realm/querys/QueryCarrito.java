package com.marcosilv7.narutodelivery.realm.querys;

import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.realm.models.CarritoModel;

import io.realm.Realm;

public class QueryCarrito {

    public static CarritoModel obtenerCarritoActual() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(CarritoModel.class).findFirst();
    }

    public static void limpiarCarrito() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(CarritoModel.class);
        realm.beginTransaction();
    }

    public static void agregarItemCarrito(final CarritoItemModel carritoItemModel){
        Realm realm = Realm.getDefaultInstance();
        final CarritoModel carritoModel = realm.where(CarritoModel.class).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(carritoModel == null){
                    CarritoModel nuevo = realm.createObject(CarritoModel.class);
                    nuevo.setCantidadItems(0);
                    nuevo.setTotal(0);
                }else {
                    CarritoItemModel itemBuscado = null;
                    for(CarritoItemModel itemModel : carritoModel.getItems()){
                        if(itemModel.getIdProducto() == carritoItemModel.getIdProducto()){
                            itemBuscado = itemModel;
                            break;
                        }
                    }
                    if (itemBuscado != null){
                        itemBuscado.setCantidad(itemBuscado.getCantidad()+carritoItemModel.getCantidad());
                        itemBuscado.setSubTotal(itemBuscado.getCantidad()*itemBuscado.getPrecio());
                        carritoModel.setCantidadItems(carritoModel.getItems().size());
                        carritoModel.setTotal(itemBuscado.getSubTotal());
                        realm.insertOrUpdate(carritoModel);
                        realm.insertOrUpdate(itemBuscado);
                    }else {
                        itemBuscado = realm.createObject(CarritoItemModel.class);
                        itemBuscado.setSubTotal(carritoItemModel.getSubTotal());
                        itemBuscado.setIdProducto(carritoItemModel.getIdProducto());
                        itemBuscado.setCantidad(carritoItemModel.getCantidad());
                        itemBuscado.setPrecio(carritoItemModel.getPrecio());
                        itemBuscado.setNombreProducto(carritoItemModel.getNombreProducto());
                        itemBuscado.setImage(carritoItemModel.getImage());
                        itemBuscado.setFamiliaProducto(carritoItemModel.getFamiliaProducto());
                        carritoModel.getItems().add(itemBuscado);
                        carritoModel.setCantidadItems(carritoModel.getItems().size());
                        realm.insertOrUpdate(carritoModel);
                    }
                }
            }
        });
    }

    public static void disminuirEnUno(final CarritoItemModel carritoItemModel) {
        Realm realm = Realm.getDefaultInstance();
        final CarritoModel carritoModel = realm.where(CarritoModel.class).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CarritoItemModel itemBuscado = null;
                for (CarritoItemModel itemModel : carritoModel.getItems()) {
                    if (itemModel.getIdProducto() == carritoItemModel.getIdProducto()) {
                        itemBuscado = itemModel;
                        break;
                    }
                }
                if(itemBuscado != null){
                    itemBuscado.setCantidad(itemBuscado.getCantidad() - 1);
                    itemBuscado.setSubTotal(itemBuscado.getCantidad() * itemBuscado.getPrecio());
                    realm.insertOrUpdate(itemBuscado);
                    double total= 0.0;
                    for (CarritoItemModel itemModel : carritoModel.getItems()) {
                        total = total + itemModel.getSubTotal();
                    }
                    carritoModel.setTotal(total);
                    realm.insertOrUpdate(carritoModel);
                }
            }
        });
    }

    public static void aumentarEnUno(final CarritoItemModel carritoItemModel) {
        Realm realm = Realm.getDefaultInstance();
        final CarritoModel carritoModel = realm.where(CarritoModel.class).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CarritoItemModel itemBuscado = null;
                for (CarritoItemModel itemModel : carritoModel.getItems()) {
                    if (itemModel.getIdProducto() == carritoItemModel.getIdProducto()) {
                        itemBuscado = itemModel;
                        break;
                    }
                }
                if (itemBuscado != null) {
                    itemBuscado.setCantidad(itemBuscado.getCantidad() + 1);
                    itemBuscado.setSubTotal(itemBuscado.getCantidad() * itemBuscado.getPrecio());
                    realm.insertOrUpdate(itemBuscado);
                    double total= 0.0;
                    for (CarritoItemModel itemModel : carritoModel.getItems()) {
                        total = total + itemModel.getSubTotal();
                    }
                    carritoModel.setTotal(total);
                    realm.insertOrUpdate(carritoModel);
                }
            }
        });
    }

    public static void eliminarItemCarrito(final CarritoItemModel carritoItemModel){
        Realm realm = Realm.getDefaultInstance();
        final CarritoModel carritoModel = realm.where(CarritoModel.class).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CarritoItemModel itemBuscado = null;
                for (CarritoItemModel itemModel : carritoModel.getItems()) {
                    if (itemModel.getIdProducto() == carritoItemModel.getIdProducto()) {
                        itemBuscado = itemModel;
                        break;
                    }
                }
                if (itemBuscado != null) {
                    carritoModel.setTotal(carritoModel.getTotal()-itemBuscado.getSubTotal());
                    itemBuscado.deleteFromRealm();
                    realm.insertOrUpdate(carritoModel);
                }
            }
        });
    }
}
