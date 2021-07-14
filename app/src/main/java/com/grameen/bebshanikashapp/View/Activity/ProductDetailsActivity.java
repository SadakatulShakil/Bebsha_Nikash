package com.grameen.bebshanikashapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.grameen.bebshanikashapp.Model.AllProducts.Product;
import com.grameen.bebshanikashapp.R;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView image1, image2;
    private TextView priceTv, quantityTv, productName, write_notes;
    private ExtendedFloatingActionButton editProductBt;
    private Product productInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        inItView();
        Intent intent = getIntent();
        productInfo = (Product) intent.getSerializableExtra("productInfo");

        if(!productInfo.getPhoto1().isEmpty()) {
            Picasso.get().load("https://emails.futureskyltd.com/images/"+productInfo.getPhoto1()).into(image1);
        }else  if(!productInfo.getPhoto2().isEmpty()) {
            Picasso.get().load("https://emails.futureskyltd.com/images/"+productInfo.getPhoto2()).into(image2);
        }
        priceTv.setText("মূল্য ৳ "+productInfo.getSellingPrice());
        quantityTv.setText("আর মাত্র "+productInfo.getQuantity()+" "+ productInfo.getUnit()+" মজুদ আছে");
        productName.setText(productInfo.getProductName());
        write_notes.setText(productInfo.getDescription());

        editProductBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProductDetailsActivity.this, UpdateProductActivity.class);
                intent1.putExtra("productInfo", productInfo);
                startActivity(intent1);
            }
        });
    }

    private void inItView() {
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        priceTv = findViewById(R.id.priceTv);
        quantityTv = findViewById(R.id.quantityTv);
        productName = findViewById(R.id.productName);
        write_notes = findViewById(R.id.write_notes);
        editProductBt = findViewById(R.id.editProductBt);
    }
}