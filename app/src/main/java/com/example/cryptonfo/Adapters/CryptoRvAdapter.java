package com.example.cryptonfo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptonfo.Modals.CryptoRvModal;
import com.example.cryptonfo.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CryptoRvAdapter extends RecyclerView.Adapter<CryptoRvAdapter.customViewholder> {

    private ArrayList<CryptoRvModal> cryptoRvModalArrayList;
    private Context context;
    private static DecimalFormat df = new DecimalFormat("#.##");

    public CryptoRvAdapter(ArrayList<CryptoRvModal> cryptoRvModalArrayList, Context context) {
        this.cryptoRvModalArrayList = cryptoRvModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public customViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crypto_rv_item,parent,false);
        return new CryptoRvAdapter.customViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customViewholder holder, int position) {
        CryptoRvModal cryptoRvModal= cryptoRvModalArrayList.get(position);
        holder.currNameTV.setText(cryptoRvModal.getName());
        holder.symbolTV.setText(cryptoRvModal.getSymbol());
        holder.rateTV.setText("$"+df.format(cryptoRvModal.getPrice()));
    }

    @Override
    public int getItemCount() {

        return cryptoRvModalArrayList.size();
    }
    public class customViewholder extends RecyclerView.ViewHolder{
        private TextView currNameTV, symbolTV,rateTV;
        public customViewholder(@NonNull View itemView) {
            super(itemView);
            currNameTV=itemView.findViewById(R.id.cryptoName);
            symbolTV=itemView.findViewById(R.id.cryptoSymbol);
            rateTV=itemView.findViewById(R.id.cryptoValue);
        }
    }
}

