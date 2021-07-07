package com.grameen.bebshanikashapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.grameen.bebshanikashapp.Adapters.MyProductAdapter;
import com.grameen.bebshanikashapp.Adapters.ProductAdapter;
import com.grameen.bebshanikashapp.Api.ApiInterface;
import com.grameen.bebshanikashapp.Api.RetrofitClient;
import com.grameen.bebshanikashapp.Model.AllProducts.AllProducts;
import com.grameen.bebshanikashapp.Model.AllProducts.Product;
import com.grameen.bebshanikashapp.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MyProductActivity extends AppCompatActivity {

    private ImageView backBtn;
    private RecyclerView listRevView;
    private MyProductAdapter myProductAdapter;
    private ArrayList<Product> mProductArrayList = new ArrayList<>();
    private String searchText, retrievedToken;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product);
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

                    mProductArrayList.addAll(product.getProducts());
                }else if(response.code() == 401){
                    Toast.makeText(MyProductActivity.this, "নতুন করে লগ-ইন করুন", Toast.LENGTH_SHORT).show();
                    finish();
                    preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", null).apply();
                    Intent logOutIntent = new Intent(MyProductActivity.this, MainActivity.class);
                    startActivity(logOutIntent);
                }else {
                    Toast.makeText(MyProductActivity.this, "সার্ভার এ সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                }
                myProductAdapter = new MyProductAdapter(MyProductActivity.this, mProductArrayList);
                listRevView.setLayoutManager(new LinearLayoutManager(MyProductActivity.this));
                listRevView.setAdapter(myProductAdapter);
                myProductAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AllProducts> call, Throwable t) {

            }
        });
    }

    private void inItView() {
        backBtn = findViewById(R.id.backBtn);
        listRevView = findViewById(R.id.listRevView);
    }
}