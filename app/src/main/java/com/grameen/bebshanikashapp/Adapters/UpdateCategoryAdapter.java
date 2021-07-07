package com.grameen.bebshanikashapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.grameen.bebshanikashapp.Model.Categories.Category;
import com.grameen.bebshanikashapp.R;
import com.grameen.bebshanikashapp.View.Activity.ProductUploadActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UpdateCategoryAdapter extends RecyclerView.Adapter<UpdateCategoryAdapter.viewHolder> {
    private Context context;
    private ArrayList<Category>categoryArrayList;
    private String from;
    private ScrollView mainLay;
    private RelativeLayout categoryLay;
    private TextView title, categoryTv;

    public UpdateCategoryAdapter(Context context, ArrayList<Category> categoryArrayList, String from,
                                 ScrollView mainLay, RelativeLayout categoryLay, TextView title, TextView categoryTv) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.from = from;
        this.mainLay = mainLay;
        this.categoryLay = categoryLay;
        this.title = title;
        this.categoryTv = categoryTv;
    }

    @NonNull
    @Override
    public UpdateCategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_view, parent, false);
        return new UpdateCategoryAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateCategoryAdapter.viewHolder holder, int position) {
        Category category = categoryArrayList.get(position);
        holder.spinnerView.setText(category.getCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(from.equals("update")){
                    mainLay.setVisibility(View.VISIBLE);
                    categoryLay.setVisibility(View.GONE);
                    title.setText("পণ্য আপডেট");
                    categoryTv.setText(category.getCategoryName());
                }
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
