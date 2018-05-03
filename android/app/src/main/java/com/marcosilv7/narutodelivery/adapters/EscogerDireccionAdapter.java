package com.marcosilv7.narutodelivery.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.dto.AddressDTO;
import com.marcosilv7.narutodelivery.ui.fragments.segundo.hijos.SeleccionarDireccionFragment;

import java.util.ArrayList;

public class EscogerDireccionAdapter extends RecyclerView.Adapter {

    ArrayList<AddressDTO> data;
    Context context;
    SeleccionarDireccionFragment.eventos eventos;

    public EscogerDireccionAdapter(ArrayList<AddressDTO> data, Context context,SeleccionarDireccionFragment.eventos eventos) {
        this.data = data;
        this.context = context;
        this.eventos = eventos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_escoger_direcciones,parent,false);
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

        TextView lblNombreAliasDireccionItem;
        TextView lblTelefonoDireccionItem;
        TextView lblDireccionItem;
        CardView cardView;

        public DireccionHolder(View itemView) {
            super(itemView);
            lblNombreAliasDireccionItem = itemView.findViewById(R.id.lblNombreAliasDireccionItem);
            lblTelefonoDireccionItem = itemView.findViewById(R.id.lblTelefonoDireccionItem);
            lblDireccionItem = itemView.findViewById(R.id.lblDireccionItem);
            cardView = itemView.findViewById(R.id.cardView);
        }

        void onBind(int posicion){
            final AddressDTO item = data.get(posicion);
            lblNombreAliasDireccionItem.setText(item.getAlias());
            lblDireccionItem.setText(item.getAddress());
            lblTelefonoDireccionItem.setText(item.getPhone());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventos.onClickCardView(item);
                }
            });
        }
    }
}
