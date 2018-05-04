package com.marcosilv7.narutodelivery.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.dto.OrderDTO;
import com.marcosilv7.narutodelivery.util.Util;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrdenPedidoAdapter extends RecyclerView.Adapter {

    List<OrderDTO> data;
    Context context;

    public OrdenPedidoAdapter(List<OrderDTO> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orden_pedido,parent,false);
        return new OrdenHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((OrdenHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void actualizarData(List<OrderDTO> data){
        this.data = data;
        notifyDataSetChanged();
    }

    class OrdenHolder extends RecyclerView.ViewHolder {

        TextView txtCodigoOrden;
        TextView txtLugarEntrega;
        TextView txtTelefono;
        TextView txtFormaPago;
        TextView txtFechaEmision;
        TextView txtImporteEfectivo;
        TextView txtEstado;
        CardView cardView;

        public OrdenHolder(View itemView) {
            super(itemView);
            txtCodigoOrden = itemView.findViewById(R.id.txtCodigoOrden);
            txtLugarEntrega = itemView.findViewById(R.id.txtLugarEntrega);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtFormaPago = itemView.findViewById(R.id.txtFormaPago);
            txtFechaEmision = itemView.findViewById(R.id.txtFechaEmision);
            cardView = itemView.findViewById(R.id.cardView);
            txtImporteEfectivo = itemView.findViewById(R.id.txtImporteEfectivo);
            txtEstado = itemView.findViewById(R.id.txtEstado);
        }

        void onBind(int posicion){
            final OrderDTO item = data.get(posicion);
            txtCodigoOrden.setText(item.getNumber());
            txtLugarEntrega.setText(item.getUserAddress());
            txtTelefono.setText(item.getUserPhone());
            txtFormaPago.setText(item.getPaymentType());
            String fechaEmision = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(item.getShippingDate());
            txtFechaEmision.setText(fechaEmision);
            txtImporteEfectivo.setText(Util.convertirFormatoDinero(item.getTotal().doubleValue()));
            txtEstado.setText(item.getStatus());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //eventos.onClickCardView(item);
                }
            });
        }
    }
}
