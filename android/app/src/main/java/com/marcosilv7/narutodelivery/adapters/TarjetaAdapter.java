package com.marcosilv7.narutodelivery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooltechworks.creditcarddesign.CreditCardView;
import com.marcosilv7.narutodelivery.R;
import com.marcosilv7.narutodelivery.dto.PaymentMethodDTO;

import java.util.ArrayList;

public class TarjetaAdapter extends RecyclerView.Adapter {

    ArrayList<PaymentMethodDTO> data;
    Context context;

    public TarjetaAdapter(ArrayList<PaymentMethodDTO> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_creditcard,parent,false);
        return new TarjetaHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TarjetaHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void actualizarData(ArrayList<PaymentMethodDTO> data){
        this.data = data;
        notifyDataSetChanged();
    }

    class TarjetaHolder extends RecyclerView.ViewHolder {

        CreditCardView creditCardItem;

        public TarjetaHolder(View itemView) {
            super(itemView);
            creditCardItem = itemView.findViewById(R.id.creditCardItem);
        }

        void onBind(int posicion){
            final PaymentMethodDTO item = data.get(posicion);
            creditCardItem.setCardNumber(item.getNumberCreditCard());
            creditCardItem.setCardExpiry(item.getMonthExp()+"/"+item.getYearExp());
            creditCardItem.setCardHolderName("MARCOS");
            creditCardItem.setCVV(item.getCvv());
        }
    }
}
