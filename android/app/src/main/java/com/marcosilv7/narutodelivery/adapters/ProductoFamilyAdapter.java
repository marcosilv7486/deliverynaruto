package com.marcosilv7.narutodelivery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.dto.ProductFamilyDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductoFamilyAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<ProductFamilyDTO> data;

    public ProductoFamilyAdapter(Context context, ArrayList<ProductFamilyDTO> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_familia_producto,parent,false);
        return new ProductoFamilyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ProductoFamilyHolder)holder).onBind(position);
    }

    public void actualizarData(ArrayList<ProductFamilyDTO> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        return data.size();
    }

    class ProductoFamilyHolder extends RecyclerView.ViewHolder {

        TextView tituloTextView;
        TextView cantidadProductosTextView;
        ImageView fotoImageView;

        public ProductoFamilyHolder(View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.lblTituloFamiliaItem);
            cantidadProductosTextView = itemView.findViewById(R.id.lblCantidadProductosFamiliaItem);
            fotoImageView = itemView.findViewById(R.id.imagenFamiliaItem);
        }

        void onBind(int posicion){
            ProductFamilyDTO item = data.get(posicion);
            tituloTextView.setText(item.getName());
            cantidadProductosTextView.setText(item.getCountProducts());
            Picasso.with(context).load(item.getImage()).into(fotoImageView);
        }
    }
}
