package com.marcosilv7.narutodelivery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.dto.ProductDTO;
import com.marcosilv7.narutodelivery.ui.fragments.primero.hijos.ProductosFragment;
import com.marcosilv7.narutodelivery.util.Util;

import java.util.ArrayList;

public class ProductoAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<ProductDTO> data;
    ProductosFragment.OnClickListenerProducto listenerProducto;

    public ProductoAdapter(Context context, ArrayList<ProductDTO> data,
                           ProductosFragment.OnClickListenerProducto listenerProducto) {
        this.context = context;
        this.data = data;
        this.listenerProducto = listenerProducto;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_productos,parent,false);
        return new ProductoHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductoHolder pHolder = ((ProductoHolder)holder);
        pHolder.onBind(position);
    }

    public void actualizarData(ArrayList<ProductDTO> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        return data.size();
    }

    class ProductoHolder extends RecyclerView.ViewHolder {
        TextView lblNombreProductoItem;
        TextView lblPrecioProductoItem;
        ImageView btnAgregarProductoItem;
        ImageView productoItemImagen;

        public ProductoHolder(View itemView) {
            super(itemView);
            lblNombreProductoItem = itemView.findViewById(R.id.lblNombreProductoItem);
            lblPrecioProductoItem = itemView.findViewById(R.id.lblPrecioProductoItem);
            btnAgregarProductoItem = itemView.findViewById(R.id.btnAgregarProductoItem);
            productoItemImagen = itemView.findViewById(R.id.productoItemImagen);
        }

        void onBind(final int posicion){
            final ProductDTO item = data.get(posicion);
            lblNombreProductoItem.setText(item.getName());
            lblPrecioProductoItem.setText(Util.convertirFormatoDinero(item.getPrice().doubleValue()));
            Glide.with(context).load(item.getImage()).apply(RequestOptions.centerCropTransform()).into(productoItemImagen);
            btnAgregarProductoItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerProducto.onClick(item);
                }
            });
        }
    }
}
