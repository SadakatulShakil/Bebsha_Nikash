package com.grameen.bebshanikashapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grameen.bebshanikashapp.Model.AllBuyer.Buyer;
import com.grameen.bebshanikashapp.Model.AllCustomer.Customer;
import com.grameen.bebshanikashapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BuyerAdapter extends RecyclerView.Adapter<BuyerAdapter.viewHolder> {
    private Context context;
    private ArrayList<Buyer> customerArrayList;

    public BuyerAdapter(Context context, ArrayList<Buyer> customerArrayList) {
        this.context = context;
        this.customerArrayList = customerArrayList;
    }

    @NonNull
    @Override
    public BuyerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_lay, parent, false);
        return new BuyerAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerAdapter.viewHolder holder, int position) {
        Buyer buyerInfo = customerArrayList.get(position);

        holder.name.setText(buyerInfo.getRelation().getName());
        holder.role.setText(buyerInfo.getRelation().getEmail());
    }

    @Override
    public int getItemCount() {
        return customerArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView name, role;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            role = itemView.findViewById(R.id.role);
        }
    }
}
