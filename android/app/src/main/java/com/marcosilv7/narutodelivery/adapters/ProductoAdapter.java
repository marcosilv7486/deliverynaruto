package com.marcosilv7.narutodelivery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.ProductosFragment;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.dto.ProductDTO;
import com.marcosilv7.narutodelivery.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductoAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<ProductDTO> data;
    int clickedPos = -1;
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
        if (clickedPos == position) {
            pHolder.linearTransparent.setVisibility(View.VISIBLE);
            pHolder.lblSubCategoriaProductoItem.setVisibility(View.INVISIBLE);
            pHolder.lblNombreProductoItem.setVisibility(View.INVISIBLE);
            pHolder.btnSettingsItemProducto.setVisibility(View.INVISIBLE);
        } else {
            pHolder.linearTransparent.setVisibility(View.INVISIBLE);
            pHolder.lblSubCategoriaProductoItem.setVisibility(View.VISIBLE);
            pHolder.lblNombreProductoItem.setVisibility(View.VISIBLE);
            pHolder.btnSettingsItemProducto.setVisibility(View.VISIBLE);
        }
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
        LinearLayout linearTransparent;
        TextView lblNombreProductoItem;
        TextView lblPrecioProductoItem;
        TextView lblSubCategoriaProductoItem;
        Button btnVerDetalleProductoItem;
        Button btnAgregarProductoItem;
        Button btnRealizarPedidoProductoItem;
        ImageView productoItemImagen;
        ImageView btnSettingsItemProducto;

        public ProductoHolder(View itemView) {
            super(itemView);
            lblNombreProductoItem = itemView.findViewById(R.id.lblNombreProductoItem);
            lblPrecioProductoItem = itemView.findViewById(R.id.lblPrecioProductoItem);
            lblSubCategoriaProductoItem = itemView.findViewById(R.id.lblSubCategoriaProductoItem);
            btnVerDetalleProductoItem = itemView.findViewById(R.id.btnVerDetalleProductoItem);
            btnAgregarProductoItem = itemView.findViewById(R.id.btnAgregarProductoItem);
            btnRealizarPedidoProductoItem = itemView.findViewById(R.id.btnRealizarPedidoProductoItem);
            productoItemImagen = itemView.findViewById(R.id.productoItemImagen);
            linearTransparent = itemView.findViewById(R.id.linearTransparent);
            btnSettingsItemProducto = itemView.findViewById(R.id.btnSettingsItemProducto);
        }

        void onBind(final int posicion){
            final ProductDTO item = data.get(posicion);
            lblNombreProductoItem.setText(item.getName());
            lblPrecioProductoItem.setText(Util.convertirFormatoDinero(item.getPrice().doubleValue()));
            lblSubCategoriaProductoItem.setText(item.getSubFamily());
            Picasso.with(context).load(item.getImage()).fit().into(productoItemImagen);
            btnSettingsItemProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedPos = posicion;
                    notifyDataSetChanged();
                }
            });
            btnAgregarProductoItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerProducto.onClick(item);
                }
            });
        }
    }
}
