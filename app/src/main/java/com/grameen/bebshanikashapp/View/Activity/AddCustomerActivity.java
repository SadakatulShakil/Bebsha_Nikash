package com.grameen.bebshanikashapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.grameen.bebshanikashapp.Api.ApiInterface;
import com.grameen.bebshanikashapp.Api.RetrofitClient;
import com.grameen.bebshanikashapp.Model.AddCustomer.AddCustomer;
import com.grameen.bebshanikashapp.Model.Contact.ContactList;
import com.grameen.bebshanikashapp.Model.UploadProduct.UploadProduct;
import com.grameen.bebshanikashapp.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Random;

import static android.content.ContentValues.TAG;

public class AddCustomerActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton customerAdd, supplierAdd, nextStepBtn;
    private ImageView backBtn;
    private EditText customerName, phoneNumber;
    private TextView phoneBook;
    private ContactList contactInfo;
    private String role = "customer", retrievedToken;
    private String password;
    private SharedPreferences preferences;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        inItView();
        preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        retrievedToken  = preferences.getString("TOKEN",null);
        Intent i = getIntent();
        contactInfo = (ContactList) i.getSerializableExtra("contactInfo");
        if (contactInfo == null) {

            customerName.setText("কাস্টমারের নাম");
            phoneNumber.setText("মোবাইল নম্বর");
        } else {
            customerName.setText(contactInfo.getName());
            phoneNumber.setText(contactInfo.getNumber());
        }

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
         password = String.format("%06d", number);
        Log.d(TAG, "onCreate: " + password);

        customerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerName.setHint("কাস্টমারের নাম");
                role = "customer";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    supplierAdd.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    customerAdd.setBackgroundTintList(getResources().getColorStateList(R.color.color_primary_lite));
                }
            }
        });

        supplierAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerName.setHint("সাপ্লায়ারের নাম");
                role = "buyer";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    customerAdd.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    supplierAdd.setBackgroundTintList(getResources().getColorStateList(R.color.color_primary_lite));
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        phoneBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(AddCustomerActivity.this)
                        .withPermission(Manifest.permission.READ_CONTACTS)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                if(response.getPermissionName().equals(Manifest.permission.READ_CONTACTS)){
                                    Intent contact = new Intent(AddCustomerActivity.this, PhoneBookListActivity.class);
                                    startActivity(contact);
                                }
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        }).check();
            }
        });

        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = customerName.getText().toString().trim();
                String uNumber = phoneNumber.getText().toString().trim();
                String uRole = role;
                String uPassword = password;


                if (uName.isEmpty()) {
                    customerName.setError("নাম দিতে হবে");
                    customerName.requestFocus();
                    return;
                }

                if (uNumber.isEmpty()) {
                    phoneNumber.setError("নম্বর দিতে হবে");
                    phoneNumber.requestFocus();
                    return;
                }

                setCustomerAdd(uName, uNumber, uRole, uPassword);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(AddCustomerActivity.this, FragmentContainerActivity.class);
        startActivity(intent);
    }

    private void setCustomerAdd(String uName, String uNumber, String uRole, String uPassword) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitClient1();
        ApiInterface api = retrofit.create(ApiInterface.class);

        Log.d(TAG, "setCustomerAdd: " +uName+"........."+ uNumber+"........." +uRole+"........."+ uPassword);
        Call<AddCustomer> addCustomerCall = api.postByAddCustomer("Bearer "+retrievedToken, uName, uNumber, uRole, uPassword);

        addCustomerCall.enqueue(new Callback<AddCustomer>() {
            @Override
            public void onResponse(Call<AddCustomer> call, Response<AddCustomer> response) {
                Log.d(TAG, "onResponse: "+ response.code());
                if(response.code() == 200){
                    progressBar.setVisibility(View.GONE);
                    AddCustomer addCustomer = response.body();
                    if(addCustomer.getStatus().equals("1")){
                        Toast.makeText(AddCustomerActivity.this, "সফলভাবে যোগ হয়েছে", Toast.LENGTH_SHORT).show();
                        customerName.setText("");
                        phoneNumber.setText("");
                    }
                }else if(response.code() == 401){
                    Toast.makeText(AddCustomerActivity.this, "নতুন করে লগ-ইন করুন", Toast.LENGTH_SHORT).show();
                    finish();
                    preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", null).apply();
                    Intent logOutIntent = new Intent(AddCustomerActivity.this, MainActivity.class);
                    startActivity(logOutIntent);
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddCustomerActivity.this, "সার্ভার এ সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddCustomer> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void inItView() {
        backBtn = findViewById(R.id.backBtn);
        customerAdd = findViewById(R.id.customerAdd);
        supplierAdd = findViewById(R.id.supplierAdd);
        customerName = findViewById(R.id.customerName);
        phoneNumber = findViewById(R.id.phoneNumber);
        phoneBook = findViewById(R.id.phoneBook);
        nextStepBtn = findViewById(R.id.nextStepBtn);
        progressBar = findViewById(R.id.progressBar);
    }
}