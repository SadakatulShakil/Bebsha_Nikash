package com.grameen.bebshanikashapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grameen.bebshanikashapp.Model.AllCustomer.AllCustomer;
import com.grameen.bebshanikashapp.Model.AllCustomer.Customer;
import com.grameen.bebshanikashapp.Model.AllCustomer.Relation;
import com.grameen.bebshanikashapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.viewHolder> {
    private Context context;
    private ArrayList<Customer> customerArrayList;

    public CustomerAdapter(Context context, ArrayList<Customer> customerArrayList) {
        this.context = context;
        this.customerArrayList = customerArrayList;
    }

    @NonNull
    @Override
    public CustomerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_lay, parent, false);
        return new CustomerAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.viewHolder holder, int position) {
        Customer customerInfo = customerArrayList.get(position);

        holder.name.setText(customerInfo.getRelation().getName());
        holder.role.setText(customerInfo.getRelation().getEmail());
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
