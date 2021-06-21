package com.grameen.bebshanikashapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.grameen.bebshanikashapp.R;

public class AddCustomerActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton customerAdd, supplierAdd;
    private ImageView backBtn;
    private EditText customerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        inItView();

        customerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerName.setHint("কাস্টমারের নাম");
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

    }

    private void inItView() {
        backBtn = findViewById(R.id.backBtn);
        customerAdd = findViewById(R.id.customerAdd);
        supplierAdd = findViewById(R.id.supplierAdd);
        customerName = findViewById(R.id.customerName);
    }
}