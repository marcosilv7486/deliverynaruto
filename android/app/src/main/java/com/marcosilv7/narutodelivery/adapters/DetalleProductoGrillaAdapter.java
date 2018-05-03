package com.marcosilv7.narutodelivery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.dto.OrderDetailDTO;
import com.marcosilv7.narutodelivery.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 22/04/2018.
 */

public class DetalleProductoGrillaAdapter extends RecyclerView.Adapter {

    Context context;
    List<OrderDetailDTO> data;

    public DetalleProductoGrillaAdapter(Context context, List<OrderDetailDTO> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_items_details_productos, parent, false);
        return new DetalleProductoCarritoHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DetalleProductoGrillaAdapter.DetalleProductoCarritoHolder) holder).onBind(position);
    }

    public void actualizarData(ArrayList<OrderDetailDTO> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    public void actualizarData(List<OrderDetailDTO> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public class DetalleProductoCarritoHolder extends RecyclerView.ViewHolder {

        TextView lblDescripcion;
        TextView lblPrecioUnitario;
        TextView lblCantidad;
        TextView lblSubTotal;

        public DetalleProductoCarritoHolder(View itemView) {
            super(itemView);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcion);
            lblPrecioUnitario = itemView.findViewById(R.id.lblPrecioUnitario);
            lblCantidad = itemView.findViewById(R.id.lblCantidad);
            lblSubTotal = itemView.findViewById(R.id.lblSubtotal);

        }

        void onBind(int posicion) {
            final OrderDetailDTO item = data.get(posicion);
            lblDescripcion.setText(item.getDescription());
            lblPrecioUnitario.setText(Util.convertirFormatoDinero(item.getUnitPrice().doubleValue()));
            lblCantidad.setText(item.getQuantity()+"");
            lblSubTotal.setText(Util.convertirFormatoDinero(item.getTotal().doubleValue()));
        }
    }
}
