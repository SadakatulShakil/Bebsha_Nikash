package com.grameen.bebshanikashapp.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grameen.bebshanikashapp.Adapters.CategoryAdapter;
import com.grameen.bebshanikashapp.Api.ApiInterface;
import com.grameen.bebshanikashapp.Api.RetrofitClient;
import com.grameen.bebshanikashapp.Model.Categories.Categories;
import com.grameen.bebshanikashapp.Model.Categories.Category;
import com.grameen.bebshanikashapp.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class CategoryListViewActivity extends AppCompatActivity {

    private ArrayList<Category> categoryArrayListAll = new ArrayList<>();
    private ArrayList<Category> categoryArrayList = new ArrayList<>();
    private TextView toolbar, customAdd, customSearch, search;
    private EditText search_eText;
    private RecyclerView listRevView;
    private CategoryAdapter categoryAdapter;
    private String searchText, retrievedToken;
    private SharedPreferences preferences;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_view);

        //initCategoryList();
        initView();
        preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        retrievedToken  = preferences.getString("TOKEN",null);
        getCategories();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = search_eText.getText().toString().trim();
                getSearchResult(searchText);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

/*        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText = search_eText.getText().toString().trim();
                if (searchText.isEmpty()) {
                    initCategoryList();
                    cancelSearch.setVisibility(View.GONE);
                }else {
                    searchText = searchText.substring(0,1).toUpperCase() + searchText.substring(1).toLowerCase();
                    getSearchResult(searchText);
                    cancelSearch.setVisibility(View.VISIBLE);
                    cancelSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            search_eText.setText("");
                            initCategoryList();
                        }
                    });

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        search_eText.addTextChangedListener(textWatcher);*/
    }

    private void getCategories() {
        Retrofit retrofit = RetrofitClient.getRetrofitClient1();
        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<Categories> categoriesCall = api.getByCategory("Bearer " + retrievedToken);

        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if(response.code() == 200){
                    Categories categories = response.body();

                    categoryArrayList.addAll(categories.getCategories());
                }else if(response.code() == 401){
                    Toast.makeText(CategoryListViewActivity.this, "নতুন করে লগ-ইন করুন", Toast.LENGTH_SHORT).show();
                    finish();
                    preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", null).apply();
                    Intent logOutIntent = new Intent(CategoryListViewActivity.this, MainActivity.class);
                    startActivity(logOutIntent);
                }else {
                    Toast.makeText(CategoryListViewActivity.this, "সার্ভার এ সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                }
                categoryAdapter = new CategoryAdapter(CategoryListViewActivity.this, categoryArrayList);
                listRevView.setLayoutManager(new LinearLayoutManager(CategoryListViewActivity.this));
                listRevView.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

            }
        });
    }

    private void getSearchResult(String text) {
        for(Category category : categoryArrayList){
            if(category.getCategoryName().equals(text)){

                categoryArrayListAll.add(category);
            }
        }
        Log.d(TAG, "getSearchResult: "+categoryArrayListAll.size());
        if(categoryArrayListAll.size() == 0){
            customAdd.setVisibility(View.VISIBLE);
            customAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CategoryListViewActivity.this, ProductUploadActivity.class);
                    intent.putExtra("categoryInfo", search_eText.getText().toString().trim());
                    startActivity(intent);
                }
            });
        }
        categoryAdapter = new CategoryAdapter(CategoryListViewActivity.this, categoryArrayListAll);
        listRevView.setLayoutManager(new LinearLayoutManager(CategoryListViewActivity.this));
        listRevView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }



    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        customAdd = findViewById(R.id.customAdd);
        search_eText = findViewById(R.id.search_eText);
        listRevView = findViewById(R.id.listRevView);
        search = findViewById(R.id.search);
        backBtn = findViewById(R.id.backBtn);
    }

   /* private void initCategoryList() {
        categoryArrayList.add(new Category("গ্রোসারি"));
        categoryArrayList.add(new Category("গৃহসজ্জা"));
        categoryArrayList.add(new Category("লাইফস্টাইল"));
        categoryArrayList.add(new Category("বাচ্চাদের খেলনা"));
        categoryArrayList.add(new Category("ছেলেদের ফ্যাশন"));
        categoryArrayList.add(new Category("কুটির শিল্প"));
        categoryArrayList.add(new Category("main"));

    }*/


}