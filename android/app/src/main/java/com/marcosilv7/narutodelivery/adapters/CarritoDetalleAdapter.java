package com.marcosilv7.narutodelivery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.util.Util;

import java.util.ArrayList;

/**
 * Created by Fernando on 22/04/2018.
 */

public class CarritoDetalleAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<CarritoItemModel> data;

    public CarritoDetalleAdapter(Context context, ArrayList<CarritoItemModel> data) {
        this.context = context;
        this.data = data;
        //this.listenerOperaciones = listenerOperaciones;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_items_details_productos, parent, false);
        return new DetalleProductoCarritoHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CarritoDetalleAdapter.DetalleProductoCarritoHolder) holder).onBind(position);
    }

    public void actualizarData(ArrayList<CarritoItemModel> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    class DetalleProductoCarritoHolder extends RecyclerView.ViewHolder {

        TextView lblDescripcion;
        TextView lblPrecioUnitario;
        TextView lblCantidad;
        TextView lblSubTotal;
        TextView lblValorTotal;


        public DetalleProductoCarritoHolder(View itemView) {
            super(itemView);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcion);
            lblPrecioUnitario = itemView.findViewById(R.id.lblPrecioUnitario);
            lblCantidad = itemView.findViewById(R.id.lblCantidad);
            lblSubTotal = itemView.findViewById(R.id.lblSubtotal);

        }

        void onBind(int posicion) {
            final CarritoItemModel item = data.get(posicion);
            lblDescripcion.setText(item.getNombreProducto());
            lblPrecioUnitario.setText(Util.convertirFormatoDinero(item.getPrecio()));
            lblCantidad.setText(item.getCantidad().toString());
            lblSubTotal.setText(Util.convertirFormatoDinero(item.getSubTotal()));
        }
    }
}
