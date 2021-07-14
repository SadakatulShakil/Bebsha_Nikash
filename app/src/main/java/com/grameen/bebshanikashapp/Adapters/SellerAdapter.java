package com.grameen.bebshanikashapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grameen.bebshanikashapp.Model.AllBuyer.Buyer;
import com.grameen.bebshanikashapp.Model.AllSellers.Seller;
import com.grameen.bebshanikashapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.viewHolder> {
    private Context context;
    private ArrayList<Seller> customerArrayList;

    public SellerAdapter(Context context, ArrayList<Seller> customerArrayList) {
        this.context = context;
        this.customerArrayList = customerArrayList;
    }

    @NonNull
    @Override
    public SellerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_lay, parent, false);
        return new SellerAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerAdapter.viewHolder holder, int position) {
        Seller seller = customerArrayList.get(position);

        holder.name.setText(seller.getUser().getName());
        holder.role.setText(seller.getUser().getEmail());
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
