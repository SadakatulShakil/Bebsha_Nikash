package com.grameen.bebshanikashapp.View.Activity;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.grameen.bebshanikashapp.Adapters.DivisionAdapter;
import com.grameen.bebshanikashapp.Adapters.UnitAdapter;
import com.grameen.bebshanikashapp.Api.ApiInterface;
import com.grameen.bebshanikashapp.Api.RetrofitClient;
import com.grameen.bebshanikashapp.Model.Divisions.Division;
import com.grameen.bebshanikashapp.Model.Unit.Unit;
import com.grameen.bebshanikashapp.Model.UploadProduct.UploadProduct;
import com.grameen.bebshanikashapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class ProductUploadActivity extends AppCompatActivity {

    private Division divisionModel;
    private DivisionAdapter mDivisionAdapter;
    private Spinner categorySpinner, unitSpinner;
    private String divName, divId;
    private ArrayList<Division> divisionArrayList = new ArrayList<>();
    private ArrayList<Unit> mUnitList = new ArrayList<>();
    private TextView selectCategory;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_upload);
        inItUnitList();
        inItView();
        preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        retrievedToken  = preferences.getString("TOKEN",null);

        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductUploadActivity.this, CategoryListViewActivity.class);
                intent.putExtra("from", "upload");
                startActivity(intent);
            }
        });

        Intent i = getIntent();
        category = (String) i.getSerializableExtra("categoryInfo");
        if (category == null) {

            selectCategory.setText("ক্যাটেগরি বাছাই করুন");
        } else {
            selectCategory.setText(category);
        }

       /* mDivisionAdapter = new DivisionAdapter(ProductUploadActivity.this, divisionArrayList);
        categorySpinner.setAdapter(mDivisionAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Division divisionInfo = (Division) parent.getItemAtPosition(position);
                divName = divisionInfo.getBnName();
                divId = divisionInfo.getId();
                //Toast.makeText(ProductUploadActivity.this, divisionInfo.getId()+"..........."+ divisionInfo.getBnName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        //getJsonFileFromLocally();

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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(ProductUploadActivity.this, FragmentContainerActivity.class);
                startActivity(i);
            }
        });
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uCategory = selectCategory.getText().toString().trim();
                String uProductTitle = productName.getText().toString().trim();
                String uDescription =  write_notes.getText().toString().trim();
                String inBuyingPrice = buyingPrice.getText().toString().trim();
                String inSellingPrice = sellingPrice.getText().toString().trim();
                String uSku = sku.getText().toString().trim();
                String inQuantity = quantity.getText().toString().trim();

               /* uBuyingPrice = Double.valueOf(inBuyingPrice);
                uSellingPrice = Double.valueOf(inSellingPrice);

                uQuantity = Integer.parseInt(inQuantity);*/

                if (uCategory.isEmpty()) {
                    selectCategory.setError("ক্যাটেগরি দিতে হবে");
                    selectCategory.requestFocus();
                    return;
                }

                if (uProductTitle.isEmpty()) {
                    productName.setError("পণ্যের নাম দিতে হবে");
                    productName.requestFocus();
                    return;
                }

                if (inBuyingPrice.isEmpty()) {
                    buyingPrice.setError("কেনা মূল্য দিতে হবে");
                    buyingPrice.requestFocus();
                    return;
                }

                if (inSellingPrice.isEmpty()) {
                    sellingPrice.setError("বিক্রির মূল্য দিতে হবে");
                    sellingPrice.requestFocus();
                    return;
                }

                if (uSku.isEmpty()) {
                    sku.setError("SKU দিতে হবে");
                    sku.requestFocus();
                    return;
                }

                if (inQuantity.isEmpty()) {
                    quantity.setError("পণ্যের পরিমান দিতে হবে");
                    quantity.requestFocus();
                    return;
                }

                if(selectUnit.equals("true")){
                    callUploadProduct(uCategory, uProductTitle, uDescription, inBuyingPrice, inSellingPrice, uSku, inQuantity, unitName);
                }else {
                    Toast.makeText(ProductUploadActivity.this, "দয়া করে একক বাছাই করুন", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void callUploadProduct(String uCategory, String uProductTitle, String uDescription, String uBuyingPrice,
                                   String uSellingPrice, String uSku, String uQuantity, String unitName) {

        Log.d(TAG, "callUploadProduct: "+ "category: "+uCategory+ ", productTitle: "+uProductTitle+ ", description: "+uDescription+ ", buyingPrice: "
                +uBuyingPrice+ ", sellingPrice: "+uSellingPrice+ ", sku: "+uSku+ ", quantity: "+uQuantity+ ", unit: "+unitName);
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), uCategory);
        RequestBody productName = RequestBody.create(MediaType.parse("text/plain"), uProductTitle);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), uDescription);
        RequestBody buyingPrice = RequestBody.create(MediaType.parse("text/plain"), uBuyingPrice);
        RequestBody sellingPrice = RequestBody.create(MediaType.parse("text/plain"), uSellingPrice);
        RequestBody sku = RequestBody.create(MediaType.parse("text/plain"), uSku);
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), uQuantity);
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

        Call<UploadProduct> uploadProductCall = api.postByUploadProduct("Bearer "+retrievedToken, productName, description,
                buyingPrice, sellingPrice, sku, quantity, unit, category, requestBody1, requestBody2);

        uploadProductCall.enqueue(new Callback<UploadProduct>() {
            @Override
            public void onResponse(Call<UploadProduct> call, Response<UploadProduct> response) {
                Log.d(TAG, "onResponse: "+ response.code());
                if(response.code() == 200){
                    progressBar.setVisibility(View.GONE);
                    UploadProduct uploadProduct = response.body();
                    if(uploadProduct.getStatus().equals("1")){
                        Toast.makeText(ProductUploadActivity.this, "পণ্যটি সফলভাবে আপলোড হয়েছে", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProductUploadActivity.this, FragmentContainerActivity.class);
                        startActivity(intent);
                    }
                }else if(response.code() == 401){
                    Toast.makeText(ProductUploadActivity.this, "নতুন করে লগ-ইন করুন", Toast.LENGTH_SHORT).show();
                    finish();
                    preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", null).apply();
                    Intent logOutIntent = new Intent(ProductUploadActivity.this, MainActivity.class);
                    startActivity(logOutIntent);
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProductUploadActivity.this, "সার্ভার এ সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadProduct> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });

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
        CursorLoader loader = new CursorLoader(ProductUploadActivity.this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        if(cursor != null){
            result = cursor.getString(column_index);
        }
        cursor.close();
        return result;
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


    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(ProductUploadActivity.this, FragmentContainerActivity.class);
        startActivity(i);
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

        selectCategory = findViewById(R.id.selectCategory);
        unitSpinner = findViewById(R.id.unitSpinner);
        unitAdapter = new UnitAdapter(ProductUploadActivity.this, mUnitList);
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
                    Toast.makeText(ProductUploadActivity.this, "দয়া করে একক বাছাই করুন", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}

   /* public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = ProductUploadActivity.this.getAssets().open("divisions.json");       //TODO Json File  name from assets folder
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void getJsonFileFromLocally() {
        try {

            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());

            JSONArray jsonArray = jsonObject.getJSONArray("data");                  //TODO pass array object name
            Log.e("keshav", "m_jArry -->" + jsonArray.length());


            for (int i = 0; i < jsonArray.length(); i++)
            {
                divisionModel = new Division();

                JSONObject jsonObjectEmployee = jsonArray.getJSONObject(i);


                String id = jsonObjectEmployee.getString("id");
                String name = jsonObjectEmployee.getString("name");
                String bn_name = jsonObjectEmployee.getString("bn_name");

                divisionModel.setId(""+id);
                divisionModel.setName(""+name);
                divisionModel.setBnName(""+bn_name);

                divisionArrayList.add(divisionModel);


            }
            if(divisionArrayList!=null) {
                mDivisionAdapter.notifyDataSetChanged();
            }
            Log.d(TAG, "getJsonFileFromLocally: " + divisionArrayList.size());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
