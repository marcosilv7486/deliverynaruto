package com.marcosilv7.narutodelivery.realm.querys;

import com.marcosilv7.narutodelivery.dto.ProductDTO;
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;


import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

public class QueryCarrito {

    public static List<CarritoItemModel> obtenerCarritoActual() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(CarritoItemModel.class).equalTo("removido",false).findAll();
    }

    public static int obtenerCantidadActualCarrito(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(CarritoItemModel.class).equalTo("removido",false).sum("cantidad").intValue();
    }

    public static double obtenerImporteTotal(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(CarritoItemModel.class).equalTo("removido",false).sum("subTotal").doubleValue();
    }

    public static void limpiarCarrito() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(CarritoItemModel.class);
            }
        });
    }

    public static void agregarProductDTO(final ProductDTO productDTO){
        Realm realm = Realm.getDefaultInstance();
        final CarritoItemModel itemBuscado = realm.where(CarritoItemModel.class)
                .equalTo("idProducto",productDTO.getId())
                .equalTo("removido",false)
                .sort("numeroItem", Sort.DESCENDING)
                .findFirst();
        if(itemBuscado == null){
            final long numeroItem = realm.where(CarritoItemModel.class).count();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    CarritoItemModel nuevo = realm.createObject(CarritoItemModel.class);
                    nuevo.setSubTotal(productDTO.getPrice().doubleValue());
                    nuevo.setIdProducto(productDTO.getId());
                    nuevo.setCantidad(1);
                    nuevo.setPrecio(productDTO.getPrice().doubleValue());
                    nuevo.setNombreProducto(productDTO.getName());
                    nuevo.setImage(productDTO.getImage());
                    nuevo.setFamiliaProducto(productDTO.getFamily());
                    nuevo.setRemovido(false);
                    nuevo.setNumeroItem((int)(numeroItem+1L));
                }
            });
        }else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    itemBuscado.setCantidad(itemBuscado.getCantidad()+1);
                    itemBuscado.setSubTotal(itemBuscado.getCantidad()*itemBuscado.getPrecio());
                    realm.insertOrUpdate(itemBuscado);
                }
            });
        }
    }

    public static void modificarItemCarrito(final CarritoItemModel carritoItemModel, final int cantidadAgregada){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                carritoItemModel.setCantidad(carritoItemModel.getCantidad()+cantidadAgregada);
                carritoItemModel.setSubTotal(carritoItemModel.getCantidad()*carritoItemModel.getPrecio());
                realm.insertOrUpdate(carritoItemModel);
            }
        });
    }

    public static void removerItemCarrito(final CarritoItemModel carritoItemModel){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                carritoItemModel.setRemovido(true);
                realm.insertOrUpdate(carritoItemModel);
            }
        });
    }

    public static void resucitarItemCarrito(final CarritoItemModel carritoItemModel){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                carritoItemModel.setRemovido(false);
                realm.insertOrUpdate(carritoItemModel);
            }
        });
    }

}
