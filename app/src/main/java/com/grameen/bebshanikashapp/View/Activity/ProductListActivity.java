package com.grameen.bebshanikashapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grameen.bebshanikashapp.Adapters.CategoryAdapter;
import com.grameen.bebshanikashapp.Adapters.ProductAdapter;
import com.grameen.bebshanikashapp.Api.ApiInterface;
import com.grameen.bebshanikashapp.Api.RetrofitClient;
import com.grameen.bebshanikashapp.Model.AllProducts.AllProducts;
import com.grameen.bebshanikashapp.Model.AllProducts.Product;
import com.grameen.bebshanikashapp.Model.Categories.Categories;
import com.grameen.bebshanikashapp.Model.Categories.Category;
import com.grameen.bebshanikashapp.R;
import com.grameen.bebshanikashapp.View.Fragment.AccountingFragment;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ProductListActivity extends AppCompatActivity {

    private ImageView backBtn;
    private ArrayList<Product> productArrayList = new ArrayList<>();
    private TextView toolbar, customAdd, customSearch, search;
    private EditText search_eText;
    private RecyclerView listRevView;
    private ProductAdapter productAdapter;
    private String searchText, retrievedToken, from;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        inItView();
        preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        retrievedToken  = preferences.getString("TOKEN",null);
        getAllProducts();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        Log.d(TAG, "onCreate: " + from);
       /* search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = search_eText.getText().toString().trim();
                searchByText(text);
            }
        });*/
        search_eText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchByText(s.toString());
            }
        });
    }

    private void searchByText(String text) {

        ArrayList<Product> filterProductList = new ArrayList<>();
        for(Product product : productArrayList){
            if(product.getProductName().toLowerCase().contains(text.toLowerCase())){
                filterProductList.add(product);
            }
        }
        productAdapter.filterList(filterProductList);
    }

    private void getAllProducts() {
        Retrofit retrofit = RetrofitClient.getRetrofitClient1();
        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<AllProducts> productCall = api.getByProduct("Bearer " + retrievedToken);

        productCall.enqueue(new Callback<AllProducts>() {
            @Override
            public void onResponse(Call<AllProducts> call, Response<AllProducts> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if(response.code() == 200){
                    AllProducts product = response.body();

                    productArrayList.addAll(product.getProducts());
                }else if(response.code() == 401){
                    Toast.makeText(ProductListActivity.this, "নতুন করে লগ-ইন করুন", Toast.LENGTH_SHORT).show();
                    finish();
                    preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", null).apply();
                    Intent logOutIntent = new Intent(ProductListActivity.this, MainActivity.class);
                    startActivity(logOutIntent);
                }else {
                    Toast.makeText(ProductListActivity.this, "সার্ভার এ সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                }
                productAdapter = new ProductAdapter(ProductListActivity.this, productArrayList, from);
                listRevView.setLayoutManager(new LinearLayoutManager(ProductListActivity.this));
                listRevView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AllProducts> call, Throwable t) {

            }
        });
    }

    private void inItView() {
        backBtn = findViewById(R.id.backBtn);
        toolbar = findViewById(R.id.toolbar);
        search_eText = findViewById(R.id.search_eText);
        listRevView = findViewById(R.id.listRevView);
        search = findViewById(R.id.search);
    }
}