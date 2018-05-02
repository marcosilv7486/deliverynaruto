package com.marcosilv7.narutodelivery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.dto.AddressDTO;
import com.marcosilv7.narutodelivery.ui.fragments.cuarto.hijos.DireccionesFragment;
import com.marcosilv7.narutodelivery.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DireccionAdapter extends RecyclerView.Adapter {

    ArrayList<AddressDTO> data;
    Context context;
    DireccionesFragment.OperacionesDireccionEntrega listener;

    public DireccionAdapter(ArrayList<AddressDTO> data, Context context,
                            DireccionesFragment.OperacionesDireccionEntrega listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_direcciones,parent,false);
        return new DireccionHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DireccionHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void actualizarData(ArrayList<AddressDTO> data){
        this.data = data;
        notifyDataSetChanged();
    }

    class DireccionHolder extends RecyclerView.ViewHolder {

        ImageView imagenMapaDireccionesItem;
        TextView lblNombreAliasDireccionItem;
        TextView lblDireccionItem;
        ImageView favoritoDireccionItem;
        ImageView editarDireccionItem;
        ImageView btnEliminarDireccion;

        TextView lblTelefonoDireccionItem;

        public DireccionHolder(View itemView) {
            super(itemView);
            imagenMapaDireccionesItem = itemView.findViewById(R.id.imagenMapaDireccionesItem);
            lblNombreAliasDireccionItem = itemView.findViewById(R.id.lblNombreAliasDireccionItem);
            lblDireccionItem = itemView.findViewById(R.id.lblDireccionItem);
            favoritoDireccionItem = itemView.findViewById(R.id.favoritoDireccionItem);
            editarDireccionItem = itemView.findViewById(R.id.editarDireccionItem);
            btnEliminarDireccion = itemView.findViewById(R.id.btnEliminarDireccion);
            lblTelefonoDireccionItem = itemView.findViewById(R.id.lblTelefonoDireccionItem);
        }

        void onBind(int posicion){
            final AddressDTO item = data.get(posicion);
            lblNombreAliasDireccionItem.setText(item.getAlias());
            lblDireccionItem.setText(item.getAddress());
            lblTelefonoDireccionItem.setText(item.getPhone());
            Picasso.get()
                    .load(Util.obtenerUrlMapaStatic(item.getLatitude(),item.getLongitude()))
                    .into(imagenMapaDireccionesItem);
            Picasso.get()
                    .load(item.isFavorite() ? R.drawable.ic_star_black_24dp : R.drawable.ic_star_border_black_24dp)
                    .into(favoritoDireccionItem);
            favoritoDireccionItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.marcarComoFavorito(item);
                }
            });
            editarDireccionItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.editar(item);
                }
            });
            btnEliminarDireccion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.eliminar(item);
                }
            });
        }
    }
}
