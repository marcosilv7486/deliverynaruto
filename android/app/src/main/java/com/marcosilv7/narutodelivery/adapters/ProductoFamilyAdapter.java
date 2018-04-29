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
import com.marcosilv7.narutodelivery.dto.ProductFamilyDTO;
import com.marcosilv7.narutodelivery.ui.fragments.primero.hijos.CategoriasFragment;

import java.util.ArrayList;

public class ProductoFamilyAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<ProductFamilyDTO> data;
    CategoriasFragment.FamilyProductOnClickListener listener;

    public ProductoFamilyAdapter(Context context, ArrayList<ProductFamilyDTO> data,
                                 CategoriasFragment.FamilyProductOnClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
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
            final ProductFamilyDTO item = data.get(posicion);
            tituloTextView.setText(item.getName());
            cantidadProductosTextView.setText(item.getCountProducts().toString());
            Glide.with(context).load(item.getImage()).apply(RequestOptions.centerCropTransform()).into(fotoImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }
    }
}
