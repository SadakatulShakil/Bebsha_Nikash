package com.grameen.bebshanikashapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grameen.bebshanikashapp.Model.Contact.ContactList;
import com.grameen.bebshanikashapp.R;
import com.grameen.bebshanikashapp.View.Activity.AddCustomerActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.viewHolder> {
    private Context context;
    private ArrayList<ContactList> contactListArrayList;

    public ContactAdapter(Context context, ArrayList<ContactList> contactListArrayList) {
        this.context = context;
        this.contactListArrayList = contactListArrayList;
    }

    @NonNull
    @Override
    public ContactAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_lay, parent, false);
        return new ContactAdapter.viewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.viewHolder holder, int position) {
        ContactList contactList = contactListArrayList.get(position);

        holder.name.setText(contactList.getName());
        holder.number.setText(contactList.getNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
                Intent intent = new Intent(context, AddCustomerActivity.class);
                intent.putExtra("contactInfo", contactList);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactListArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView name, number;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
        }
    }
}
