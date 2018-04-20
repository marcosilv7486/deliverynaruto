package com.marcosilv7.narutodelivery.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.CarritoFragment;
import com.marcosilv7.narutodelivery.CategoriasFragment;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.events.onMoveAndSwipedListener;
import com.marcosilv7.narutodelivery.realm.models.CarritoItemModel;
import com.marcosilv7.narutodelivery.realm.models.CarritoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarritoItemAdapter extends RecyclerView.Adapter implements onMoveAndSwipedListener {

    Context context;
    ArrayList<CarritoItemModel> data;
    CarritoFragment.clickOperacionesCantidad listenerOperaciones;

    public CarritoItemAdapter(Context context, ArrayList<CarritoItemModel> data,
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

    public void actualizarData(ArrayList<CarritoItemModel> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        return data.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(RecyclerView.ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
        final CarritoItemModel item = data.get(position);

        Snackbar snackbar = Snackbar
                .make(((Activity) context).findViewById(R.id.recyclerCarritoProductos),
                        "Se removio el producto", Snackbar.LENGTH_LONG)
                .setAction("Deshacer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listenerOperaciones.agregarClickListener(item);
                    }
                });
        snackbar.show();
        listenerOperaciones.removerClickListener(item);
    }

    class ProductoCarritoHolder extends RecyclerView.ViewHolder {

        TextView lblTituloProductoCarritoItem;
        TextView lblCategoriaProductoCarritoItem;
        TextView lblCantidadProductoCarritoItem;
        TextView lblPrecioProductoCarritoItem;
        ImageView imagenProductoItemCarrito;
        ImageButton btnAumentarProductoItem;
        ImageButton btnDisminuirProductoItem;

        public ProductoCarritoHolder(View itemView) {
            super(itemView);
            lblTituloProductoCarritoItem = itemView.findViewById(R.id.lblTituloProductoCarritoItem);
            lblCategoriaProductoCarritoItem = itemView.findViewById(R.id.lblCategoriaProductoCarritoItem);
            lblCantidadProductoCarritoItem = itemView.findViewById(R.id.lblCantidadProductoCarritoItem);
            lblPrecioProductoCarritoItem = itemView.findViewById(R.id.lblPrecioProductoCarritoItem);
            imagenProductoItemCarrito = itemView.findViewById(R.id.imagenProductoItemCarrito);
            btnAumentarProductoItem = itemView.findViewById(R.id.btnAumentarProductoItem);
            btnAumentarProductoItem = itemView.findViewById(R.id.btnAumentarProductoItem);
            btnDisminuirProductoItem = itemView.findViewById(R.id.btnDisminuirProductoItem);
        }

        void onBind(int posicion){
            final CarritoItemModel item = data.get(posicion);
            lblTituloProductoCarritoItem.setText(item.getNombreProducto());
            lblCategoriaProductoCarritoItem.setText(item.getFamiliaProducto());
            lblCantidadProductoCarritoItem.setText(item.getCantidad().toString());
            lblPrecioProductoCarritoItem.setText(item.getSubTotal()+"");
            Picasso.with(context).load(item.getImage()).fit().into(imagenProductoItemCarrito);
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
        }
    }
}