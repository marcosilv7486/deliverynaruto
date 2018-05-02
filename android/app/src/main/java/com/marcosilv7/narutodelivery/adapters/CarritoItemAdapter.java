package com.marcosilv7.narutodelivery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos.CarritoFragment;
import com.marcosilv7.narutodelivery.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarritoItemAdapter extends RecyclerView.Adapter {

    Context context;
    List<CarritoItemModel> data;
    CarritoFragment.clickOperacionesCantidad listenerOperaciones;

    public CarritoItemAdapter(Context context, List<CarritoItemModel> data,
                              CarritoFragment.clickOperacionesCantidad listenerOperaciones) {
        this.context = context;
        this.data = data;
        this.listenerOperaciones = listenerOperaciones;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_items_productos_carrito,parent,false);
        return new ProductoCarritoHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ProductoCarritoHolder)holder).onBind(position);
    }

    public void actualizarData(List<CarritoItemModel> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        return data.size();
    }

    class ProductoCarritoHolder extends RecyclerView.ViewHolder {

        TextView lblTituloProductoCarritoItem;
        TextView lblCategoriaProductoCarritoItem;
        TextView lblCantidadProductoCarritoItem;
        TextView lblSubTotalProductoCarritoItem;
        ImageView imagenProductoItemCarrito;
        ImageButton btnAumentarProductoItem;
        ImageButton btnDisminuirProductoItem;
        ImageButton btnEliminarProductoCarrito;

        public ProductoCarritoHolder(View itemView) {
            super(itemView);
            lblTituloProductoCarritoItem = itemView.findViewById(R.id.lblTituloProductoCarritoItem);
            lblCategoriaProductoCarritoItem = itemView.findViewById(R.id.lblCategoriaProductoCarritoItem);
            lblCantidadProductoCarritoItem = itemView.findViewById(R.id.lblCantidadProductoCarritoItem);
            lblSubTotalProductoCarritoItem = itemView.findViewById(R.id.lblSubTotalProductoCarritoItem);
            imagenProductoItemCarrito = itemView.findViewById(R.id.imagenProductoItemCarrito);
            btnAumentarProductoItem = itemView.findViewById(R.id.btnAumentarProductoItem);
            btnAumentarProductoItem = itemView.findViewById(R.id.btnAumentarProductoItem);
            btnDisminuirProductoItem = itemView.findViewById(R.id.btnDisminuirProductoItem);
            btnEliminarProductoCarrito = itemView.findViewById(R.id.btnEliminarProductoCarrito);
        }

        void onBind(int posicion){
            final CarritoItemModel item = data.get(posicion);
            lblTituloProductoCarritoItem.setText(item.getNombreProducto());
            lblCategoriaProductoCarritoItem.setText(item.getFamiliaProducto());
            lblCantidadProductoCarritoItem.setText(item.getCantidad().toString());
            lblSubTotalProductoCarritoItem.setText(Util.convertirFormatoDinero(item.getSubTotal()));
            Picasso.get().load(item.getImage()).fit().into(imagenProductoItemCarrito);
            btnAumentarProductoItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerOperaciones.agregarEnUnoClickListener(item);
                }
            });
            btnDisminuirProductoItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerOperaciones.disminuirEnUnoClickListener(item);
                }
            });
            btnEliminarProductoCarrito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerOperaciones.removerClickListener(item);
                }
            });
        }
    }
}
