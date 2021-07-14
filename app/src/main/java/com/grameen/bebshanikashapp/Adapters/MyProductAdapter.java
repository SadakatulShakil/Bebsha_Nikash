package com.grameen.bebshanikashapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grameen.bebshanikashapp.Model.AllProducts.Product;
import com.grameen.bebshanikashapp.R;
import com.grameen.bebshanikashapp.View.Activity.ProductDetailsActivity;
import com.grameen.bebshanikashapp.View.Activity.UpdateProductActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.viewHolder> {
    private Context context;
    private ArrayList<Product> productsArrayList;

    public MyProductAdapter(Context context, ArrayList<Product> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
    }

    @NonNull
    @Override
    public MyProductAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new MyProductAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductAdapter.viewHolder holder, int position) {
    Product productInfo = productsArrayList.get(position);

    if(!productInfo.getPhoto1().isEmpty()) {
        Picasso.get().load("https://emails.futureskyltd.com/images/"+productInfo.getPhoto1()).into(holder.productImage);
    }
    holder.productName.setText("Name: "+productInfo.getProductName());
    holder.buyingPrice.setText("Buying price: ৳ "+productInfo.getBuyingPrice());
    holder.sellingPrice.setText("Selling price: ৳ "+productInfo.getSellingPrice());
    int qnty = productInfo.getQuantity();
    holder.quantity.setText("Quantity: "+String.valueOf(qnty)+" "+productInfo.getUnit());

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("productInfo", productInfo);
            context.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, buyingPrice, sellingPrice, quantity;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.itemImage);
            productName = itemView.findViewById(R.id.productName);
            buyingPrice = itemView.findViewById(R.id.buyingPrice);
            sellingPrice = itemView.findViewById(R.id.sellingPrice);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
