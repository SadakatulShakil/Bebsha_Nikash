package com.grameen.bebshanikashapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.grameen.bebshanikashapp.Model.Categories.Category;
import com.grameen.bebshanikashapp.R;
import com.grameen.bebshanikashapp.View.Activity.ProductUploadActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder>{
    private Context context;
    private ArrayList<Category>categoryArrayList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_view, parent, false);
        return new CategoryAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewHolder holder, int position) {
        Category category = categoryArrayList.get(position);
        holder.spinnerView.setText(category.getCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
                Intent intent = new Intent(context, ProductUploadActivity.class);
                intent.putExtra("categoryInfo", category.getCategoryName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
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
