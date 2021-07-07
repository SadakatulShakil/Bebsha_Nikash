package com.grameen.bebshanikashapp.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.grameen.bebshanikashapp.Adapters.CategoryAdapter;
import com.grameen.bebshanikashapp.Adapters.DivisionAdapter;
import com.grameen.bebshanikashapp.Adapters.UnitAdapter;
import com.grameen.bebshanikashapp.Adapters.UpdateCategoryAdapter;
import com.grameen.bebshanikashapp.Api.ApiInterface;
import com.grameen.bebshanikashapp.Api.RetrofitClient;
import com.grameen.bebshanikashapp.Model.AllProducts.Product;
import com.grameen.bebshanikashapp.Model.Categories.Categories;
import com.grameen.bebshanikashapp.Model.Categories.Category;
import com.grameen.bebshanikashapp.Model.Divisions.Division;
import com.grameen.bebshanikashapp.Model.Unit.Unit;
import com.grameen.bebshanikashapp.Model.UploadProduct.UploadProduct;
import com.grameen.bebshanikashapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class UpdateProductActivity extends AppCompatActivity {

    private Division divisionModel;
    private DivisionAdapter mDivisionAdapter;
    private Spinner categorySpinner, unitSpinner;
    private String divName, divId;
    private ArrayList<Division> divisionArrayList = new ArrayList<>();
    private ArrayList<Unit> mUnitList = new ArrayList<>();
    private TextView selectCategory, shopName;
    private String category, unitName, selectUnit = "", result = null,retrievedToken;
    private UnitAdapter unitAdapter;
    private ImageView photo1, photo2, backBtn;
    private EditText productName, write_notes, sku, buyingPrice, sellingPrice, quantity;
    private Double uBuyingPrice = 0.0, uSellingPrice= 0.0;
    private int uQuantity =0;
    private File proFile1, proFile2;
    private RequestBody requestBody1, requestBody2;
    private Intent pickIntent, chooseIntent;
    private Uri proImageUri1, proImageUri2;
    private ExtendedFloatingActionButton nextStepBtn;
    private ProgressBar progressBar;
    private SharedPreferences preferences;
    private Product productInfo;
    private ScrollView mainLay;
    private RelativeLayout categoryLay;
    private ArrayList<Category> categoryArrayListAll = new ArrayList<>();
    private ArrayList<Category> categoryArrayList = new ArrayList<>();
    private TextView toolbar, customAdd, customSearch, search;
    private EditText search_eText;
    private RecyclerView listRevView;
    private UpdateCategoryAdapter categoryAdapter;
    private String searchText, from = "update";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        inItUnitList();
        inItView();
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
        Intent intent = getIntent();
        productInfo = (Product) intent.getSerializableExtra("productInfo");

        unitSpinner.setSelection(getIndex(unitSpinner, productInfo.getUnit()));
        Log.d(TAG, "onUnit: " + productInfo.getUnit());

        selectCategory.setText(productInfo.getCategory());
        productName.setText(productInfo.getProductName());
        write_notes.setText(productInfo.getDescription());
        sku.setText(productInfo.getSku());
        buyingPrice.setText(productInfo.getBuyingPrice());
        sellingPrice.setText(productInfo.getSellingPrice());
        int qnty = productInfo.getQuantity();
        quantity.setText(String.valueOf(qnty));

        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopName.setText("তালিকা সমূহ");
                mainLay.setVisibility(View.GONE);
                categoryLay.setVisibility(View.VISIBLE);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shopName.setText("পণ্য আপডেট");
                        mainLay.setVisibility(View.VISIBLE);
                        categoryLay.setVisibility(View.GONE);
                    }
                });
            }
        });
       /* category = (String) intent.getSerializableExtra("categoryInfo");
        if (category == null) {

            selectCategory.setText("ক্যাটেগরি বাছাই করুন");
        } else {
            selectCategory.setText(category);
        }*/
        photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage1();
            }
        });

        photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage2();
            }
        });


        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpUpdate();
            }
        });
    }

    private void setUpUpdate() {

        String uCategory = selectCategory.getText().toString().trim();
        String uProductTitle = productName.getText().toString().trim();
        String uDescription =  write_notes.getText().toString().trim();
        String inBuyingPrice = buyingPrice.getText().toString().trim();
        String inSellingPrice = sellingPrice.getText().toString().trim();
        String uSku = sku.getText().toString().trim();
        String inQuantity = quantity.getText().toString().trim();

        if(selectUnit.equals("true")){
            callUploadProduct(uCategory, uProductTitle, uDescription, inBuyingPrice, inSellingPrice, uSku, inQuantity, unitName);
        }else {
            Toast.makeText(UpdateProductActivity.this, "দয়া করে একক বাছাই করুন", Toast.LENGTH_SHORT).show();
        }
    }

    private void callUploadProduct(String uCategory, String uProductTitle, String uDescription,
                                   String inBuyingPrice, String inSellingPrice, String uSku, String inQuantity, String unitName) {

        Log.d(TAG, "callUploadProduct: "+ "category: "+uCategory+ ", productTitle: "+uProductTitle+ ", description: "+uDescription+ ", buyingPrice: "
                +uBuyingPrice+ ", sellingPrice: "+uSellingPrice+ ", sku: "+uSku+ ", quantity: "+uQuantity+ ", unit: "+unitName);
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), uCategory);
        RequestBody productName = RequestBody.create(MediaType.parse("text/plain"), uProductTitle);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), uDescription);
        RequestBody buyingPrice = RequestBody.create(MediaType.parse("text/plain"), inBuyingPrice);
        RequestBody sellingPrice = RequestBody.create(MediaType.parse("text/plain"), inSellingPrice);
        RequestBody sku = RequestBody.create(MediaType.parse("text/plain"), uSku);
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), inQuantity);
        RequestBody unit = RequestBody.create(MediaType.parse("text/plain"), unitName);

        if(proImageUri1 != null){
            proFile1 = new File(getRealPathFromUri(proImageUri1));
            requestBody1 = RequestBody.create(MediaType.parse("application/octet-stream"), proFile1);
            Log.d(TAG, "onImage: " + requestBody1.toString());
        }

        if(proImageUri2 != null){
            proFile2 = new File(getRealPathFromUri(proImageUri2));
            requestBody2 = RequestBody.create(MediaType.parse("application/octet-stream"), proFile2);
            Log.d(TAG, "onImage: " + requestBody2.toString());
        }
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitClient1();
        ApiInterface api = retrofit.create(ApiInterface.class);

       /* Call<UpdateProduct> updateProductCall = api.postByUpdateProduct("Bearer "+retrievedToken, productName, description,
                buyingPrice, sellingPrice, sku, quantity, unit, category, requestBody1, requestBody2);

        updateProductCall.enqueue(new Callback<UpdateProduct>() {
            @Override
            public void onResponse(Call<UpdateProduct> call, Response<UpdateProduct> response) {
                Log.d(TAG, "onResponse: "+ response.code());
                if(response.code() == 200){
                    progressBar.setVisibility(View.GONE);
                    UpdateProduct updateProduct = response.body();
                    if(updateProduct.getStatus().equals("1")){
                        Toast.makeText(UpdateProductActivity.this, "পণ্যটি সফলভাবে আপডেট হয়েছে", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateProductActivity.this, FragmentContainerActivity.class);
                        startActivity(intent);
                    }
                }else if(response.code() == 401){
                    Toast.makeText(UpdateProductActivity.this, "নতুন করে লগ-ইন করুন", Toast.LENGTH_SHORT).show();
                    finish();
                    preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", null).apply();
                    Intent logOutIntent = new Intent(UpdateProductActivity.this, MainActivity.class);
                    startActivity(logOutIntent);
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(UpdateProductActivity.this, "সার্ভার এ সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProduct> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });*/
    }

    private void inItUnitList() {
        mUnitList.add(new Unit("একক বাছাই করুন"));
        mUnitList.add(new Unit("টি / পিছ"));
        mUnitList.add(new Unit("কেজি"));
        mUnitList.add(new Unit("গ্রাম"));
        mUnitList.add(new Unit("লিটার"));
        mUnitList.add(new Unit("মি.লি"));
        mUnitList.add(new Unit("ইঞ্চি"));
        mUnitList.add(new Unit("প্যাকেট / বাক্স"));
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
                    Toast.makeText(UpdateProductActivity.this, "নতুন করে লগ-ইন করুন", Toast.LENGTH_SHORT).show();
                    finish();
                    preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", null).apply();
                    Intent logOutIntent = new Intent(UpdateProductActivity.this, MainActivity.class);
                    startActivity(logOutIntent);
                }else {
                    Toast.makeText(UpdateProductActivity.this, "সার্ভার এ সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                }
                categoryAdapter = new UpdateCategoryAdapter(UpdateProductActivity.this, categoryArrayList, from, mainLay, categoryLay, shopName, selectCategory);
                listRevView.setLayoutManager(new LinearLayoutManager(UpdateProductActivity.this));
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
                    shopName.setText("পণ্য আপডেট");
                    mainLay.setVisibility(View.VISIBLE);
                    categoryLay.setVisibility(View.GONE);

                    selectCategory.setText(search_eText.getText().toString().trim());

                    search_eText.setText("");
                }
            });
        }
        categoryAdapter = new UpdateCategoryAdapter(UpdateProductActivity.this, categoryArrayList, from, mainLay, categoryLay, shopName, selectCategory);
        listRevView.setLayoutManager(new LinearLayoutManager(UpdateProductActivity.this));
        listRevView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (mUnitList.get(i).toString().equalsIgnoreCase(myString)){
                return i;

            }
            //Log.d(TAG, "getIndex: "+ mUnitList.get(i).toString());
        }

        return 0;
    }

    private void ChooseImage1() {
        chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(chooseIntent, 1);
    }

    private void ChooseImage2() {
        chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(chooseIntent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: " + "Activity result success");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == -1 && data != null && data.getData() != null) {
                proImageUri1 = data.getData();
                Log.d(TAG, "onActivityResult: " + proImageUri1);
                Picasso.get().load(proImageUri1).into(photo1);

            }
        }else if(requestCode == 2) {
            if (resultCode == -1 && data != null && data.getData() != null) {
                proImageUri2 = data.getData();
                Log.d(TAG, "onActivityResult: " + proImageUri2);
                Picasso.get().load(proImageUri2).into(photo2);

            }
        }
    }

    private String getRealPathFromUri(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(UpdateProductActivity.this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        if(cursor != null){
            result = cursor.getString(column_index);
        }
        cursor.close();
        return result;
    }


    private void inItView() {
        progressBar = findViewById(R.id.progressBar);
        photo1 = findViewById(R.id.photo1);
        photo2 = findViewById(R.id.photo2);
        productName = findViewById(R.id.productName);
        write_notes = findViewById(R.id.write_notes);
        sku = findViewById(R.id.sku);
        buyingPrice = findViewById(R.id.buyingPrice);
        sellingPrice = findViewById(R.id.sellingPrice);
        quantity = findViewById(R.id.quantity);
        nextStepBtn = findViewById(R.id.nextStepBtn);
        backBtn = findViewById(R.id.backBtn);
        mainLay = findViewById(R.id.mainLay);
        categoryLay = findViewById(R.id.categoryLay);
        shopName = findViewById(R.id.shopName);

        customAdd = findViewById(R.id.customAdd);
        search_eText = findViewById(R.id.search_eText);
        listRevView = findViewById(R.id.listRevView);
        search = findViewById(R.id.search);

        selectCategory = findViewById(R.id.selectCategory);
        unitSpinner = findViewById(R.id.unitSpinner);
        unitAdapter = new UnitAdapter(UpdateProductActivity.this, mUnitList);
        unitSpinner.setAdapter(unitAdapter);

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Unit clickedUnit = (Unit) parent.getItemAtPosition(position);

                unitName = clickedUnit.getUnit();
                if(position>0){
                    selectUnit = "true";
                }else {
                    selectUnit = "false";
                    Toast.makeText(UpdateProductActivity.this, "দয়া করে একক বাছাই করুন", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}