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

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.grameen.bebshanikashapp.Adapters.ProductAdapter;
import com.grameen.bebshanikashapp.Api.ApiInterface;
import com.grameen.bebshanikashapp.Api.RetrofitClient;
import com.grameen.bebshanikashapp.Model.AllCustomer.Customer;
import com.grameen.bebshanikashapp.Model.AllProducts.AllProducts;
import com.grameen.bebshanikashapp.Model.AllProducts.Product;
import com.grameen.bebshanikashapp.Model.AllTransection.AllTransection;
import com.grameen.bebshanikashapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class UserWiseAccountingActivity extends AppCompatActivity {

    private TextView sellCash, buyCash, expenseCash, dateFieldText, userName, gettingBlnc, givenBlnc, detailTransection;
    private ProgressBar progressBar;
    private ExtendedFloatingActionButton nextStepBtn;
    private EditText buyAndSellMoney, description;
    private TextView selectProduct, product_id;
    private ImageView backBtn, photo1, photo2, backBtn2;
    private File proFile1, proFile2;
    private RequestBody requestBody1, requestBody2;
    private Intent pickIntent, chooseIntent;
    private Uri proImageUri1, proImageUri2;
    private DatePickerDialog.OnDateSetListener startSetListener;
    private Product product;
    private String ref_id;
    private Double credit = 0.0, debit = 0.0;
    private int productId;
    private SharedPreferences preferences;
    private String txn_type = "sell", result = null, retrievedToken, from ="customer_account";
    private Customer customer;
    private RelativeLayout parentLay, secondLay;

    private ArrayList<Product> productArrayList = new ArrayList<>();
    private TextView toolbar, customAdd, customSearch, search;
    private EditText search_eText;
    private RecyclerView listRevView;
    private ProductAdapter productAdapter;
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wise_accounting);

        inItView();
        preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        retrievedToken  = preferences.getString("TOKEN",null);
        getAllProducts();
        Log.d(TAG, "onCreate: " + productId);
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
        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("customerInfo");
        ref_id = String.valueOf(customer.getRelation().getId());
        userName.setText(customer.getRelation().getName());

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
        String currentDate = df.format(c);

        dateFieldText.setText(currentDate);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateFieldText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UserWiseAccountingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }

        });
        startSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String dateStr = dayOfMonth+"/"+month+"/"+year;
                SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                Date dateObj = null;
                try {
                    dateObj = curFormater.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat postFormater = new SimpleDateFormat("dd MMM, yyyy");

                String sDate = postFormater.format(dateObj);
                dateFieldText.setText(sDate);
            }
        };

        sellCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAndSellMoney.setHint("পেলাম");
                txn_type = "sell";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    sellCash.setBackground(getResources().getDrawable(R.drawable.background_card));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    buyCash.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    expenseCash.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
            }
        });

        buyCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAndSellMoney.setHint("দিলাম");
                txn_type = "buy";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    sellCash.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    buyCash.setBackground(getResources().getDrawable(R.drawable.background_card));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    expenseCash.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
            }
        });

        expenseCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAndSellMoney.setHint("দিলাম");
                txn_type = "expense";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    sellCash.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    buyCash.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    expenseCash.setBackground(getResources().getDrawable(R.drawable.background_card));
                }
            }
        });

        selectProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentLay.setVisibility(View.GONE);
                secondLay.setVisibility(View.VISIBLE);
            }
        });

        backBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentLay.setVisibility(View.VISIBLE);
                secondLay.setVisibility(View.GONE);
            }
        });

       /* Intent i = getIntent();
        product = (Product) i.getSerializableExtra("productInfo");
        if (product == null) {

            selectProduct.setText("পণ্য বাছাই করুন");
        } else {
            selectProduct.setText(product.getProductName());
            productId = product.getId();
        }*/

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(UserWiseAccountingActivity.this, FragmentContainerActivity.class);
                startActivity(i);
            }
        });

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
                String amount = buyAndSellMoney.getText().toString().trim();
                double creditOrDebit = Double.parseDouble(amount);
                String comments = description.getText().toString().trim();
                String pId = product_id.getText().toString().trim();

                if (amount.isEmpty()) {
                    buyAndSellMoney.setError("টাকার পরিমান দিতে হবে");
                    buyAndSellMoney.requestFocus();
                    return;
                }
                if (selectProduct.getText().toString().trim().equals("পণ্য বাছাই করুন")) {
                    selectProduct.setError("পণ্য বাছাই করতে হবে");
                    selectProduct.requestFocus();
                    return;
                }

                if(txn_type.equals("sell")){
                    credit = creditOrDebit;
                    debit = 0.0;
                }else if(txn_type.equals("buy")){
                    credit = 0.0;
                    debit = creditOrDebit;
                }else{
                    credit = 0.0;
                    debit = creditOrDebit;
                }
                setUpTransection(ref_id, pId, credit, debit, txn_type, dateFieldText.getText().toString().trim(), comments);
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

    private void setUpTransection(String ref_id, String pId, Double credit, Double debit, String txn_type, String date, String comments) {

        String creditBlnc = String.valueOf(credit);
        String debitBlnc = String.valueOf(debit);

        Log.d(TAG, "setUpTransection: "+ "ref_id: "+ref_id+ ", pId: "+pId+ ", creditBlnc: "+creditBlnc+ ", debitBlnc: "
                +debitBlnc+ ", txn_type: "+txn_type+ ", date: "+date+ ", comments: "+comments);

        RequestBody uRef_id = RequestBody.create(MediaType.parse("text/plain"), ref_id);
        RequestBody uPId = RequestBody.create(MediaType.parse("text/plain"), pId);
        RequestBody uCreditBlnc = RequestBody.create(MediaType.parse("text/plain"), creditBlnc);
        RequestBody uDebitBlnc = RequestBody.create(MediaType.parse("text/plain"), debitBlnc);
        RequestBody uTxn_type = RequestBody.create(MediaType.parse("text/plain"), txn_type);
        RequestBody uDate = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody uComments = RequestBody.create(MediaType.parse("text/plain"), comments);

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

        Call<AllTransection> allTransectionCall = api.postByTransection("Bearer "+retrievedToken, uRef_id, uDebitBlnc, uCreditBlnc,
                uPId, uTxn_type, uDate, uComments, requestBody1, requestBody2);

        allTransectionCall.enqueue(new Callback<AllTransection>() {
            @Override
            public void onResponse(Call<AllTransection> call, Response<AllTransection> response) {
                Log.d(TAG, "onResponse: "+ response.code());
                if(response.code() == 200){
                    progressBar.setVisibility(View.GONE);
                    AllTransection allTransection = response.body();
                    if(allTransection.getStatus().equals("1")){
                        Toast.makeText(UserWiseAccountingActivity.this, "লেনদেন সফলভাবে সম্পন্ন হয়েছে", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserWiseAccountingActivity.this, FragmentContainerActivity.class);
                        startActivity(i);
                    }


                }else if(response.code() == 401){
                    Toast.makeText(UserWiseAccountingActivity.this, "নতুন করে লগ-ইন করুন", Toast.LENGTH_SHORT).show();
                    finish();
                    preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", null).apply();
                    Intent logOutIntent = new Intent(UserWiseAccountingActivity.this, MainActivity.class);
                    startActivity(logOutIntent);
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(UserWiseAccountingActivity.this, "সার্ভার এ সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllTransection> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(UserWiseAccountingActivity.this, FragmentContainerActivity.class);
        startActivity(i);
    }

    private void ChooseImage1() {
        chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(chooseIntent, 1);
    }

    private void ChooseImage2() {
        pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickIntent, 2);
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
        CursorLoader loader = new CursorLoader(UserWiseAccountingActivity.this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        if(cursor != null){
            result = cursor.getString(column_index);
        }
        cursor.close();
        return result;
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
                    Toast.makeText(UserWiseAccountingActivity.this, "নতুন করে লগ-ইন করুন", Toast.LENGTH_SHORT).show();
                    finish();
                    preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", null).apply();
                    Intent logOutIntent = new Intent(UserWiseAccountingActivity.this, MainActivity.class);
                    startActivity(logOutIntent);
                }else {
                    Toast.makeText(UserWiseAccountingActivity.this, "সার্ভার এ সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                }
                productAdapter = new ProductAdapter(UserWiseAccountingActivity.this, productArrayList, from, parentLay, secondLay, selectProduct, product_id);
                listRevView.setLayoutManager(new LinearLayoutManager(UserWiseAccountingActivity.this));
                listRevView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AllProducts> call, Throwable t) {

            }
        });
    }

    private void inItView() {

        sellCash = findViewById(R.id.sellCash);
        selectProduct = findViewById(R.id.selectProduct);
        buyCash = findViewById(R.id.buyCash);
        expenseCash = findViewById(R.id.expenseCash);
        buyAndSellMoney = findViewById(R.id.buyAndSellMoney);
        backBtn = findViewById(R.id.backBtn);
        backBtn2 = findViewById(R.id.backBtn2);
        dateFieldText = findViewById(R.id.dateFieldText);
        photo1 = findViewById(R.id.photo1);
        photo2 = findViewById(R.id.photo2);
        nextStepBtn = findViewById(R.id.nextStepBtn);
        description = findViewById(R.id.description);
        progressBar = findViewById(R.id.progressBar);
        userName = findViewById(R.id.userName);
        gettingBlnc = findViewById(R.id.gettingBlnc);
        givenBlnc = findViewById(R.id.givenBlnc);
        detailTransection = findViewById(R.id.detailTransection);
        parentLay = findViewById(R.id.parentLay);
        secondLay = findViewById(R.id.secondLay);
        toolbar = findViewById(R.id.toolbar);
        search_eText = findViewById(R.id.search_eText);
        listRevView = findViewById(R.id.listRevView);
        search = findViewById(R.id.search);
        product_id = findViewById(R.id.product_id);
    }
}