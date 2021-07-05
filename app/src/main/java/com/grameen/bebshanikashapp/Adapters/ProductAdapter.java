package com.grameen.bebshanikashapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grameen.bebshanikashapp.Model.AllProducts.Product;
import com.grameen.bebshanikashapp.Model.Categories.Category;
import com.grameen.bebshanikashapp.R;
import com.grameen.bebshanikashapp.View.Activity.AccountingActivity;
import com.grameen.bebshanikashapp.View.Activity.ProductUploadActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder>{
    private Context context;
    private ArrayList<Product>categoryArrayList;

    public ProductAdapter(Context context, ArrayList<Product> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public ProductAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_view, parent, false);
        return new ProductAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.viewHolder holder, int position) {
        Product product = categoryArrayList.get(position);
        holder.spinnerView.setText(product.getProductName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
                Intent intent = new Intent(context, AccountingActivity.class);
                intent.putExtra("productInfo", product.getProductName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size(); }
    public void filterList(ArrayList<Product> filteredList){
    categoryArrayList = filteredList;
    notifyDataSetChanged();
    }

/*    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Category> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(categoryArrayListAll);
            }else {
                for(Category category:categoryArrayListAll){
                    if(category.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(category);
                    }
                }
            }
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };*/

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView spinnerView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            spinnerView = itemView.findViewById(R.id.spinnerView);
        }
    }
}
