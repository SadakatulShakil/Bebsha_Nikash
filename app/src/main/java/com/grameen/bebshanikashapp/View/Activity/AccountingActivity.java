package com.grameen.bebshanikashapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.grameen.bebshanikashapp.R;

public class AccountingActivity extends AppCompatActivity {
    private TextView sellCash, buyCash, expenseCash;
    private EditText buyAndSellMoney;
    private TextView selectProduct;
    private ImageView backBtn;
    private String product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting);
        inItView();
        sellCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAndSellMoney.setHint("পেলাম");
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
                Intent intent = new Intent(AccountingActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });

        Intent i = getIntent();
        product = (String) i.getSerializableExtra("productInfo");
        if (product == null) {

            selectProduct.setText("ক্যাটেগরি বাছাই করুন");
        } else {
            selectProduct.setText(product);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(AccountingActivity.this, FragmentContainerActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(AccountingActivity.this, FragmentContainerActivity.class);
        startActivity(i);
    }

    private void inItView() {
        sellCash = findViewById(R.id.sellCash);
        selectProduct = findViewById(R.id.selectProduct);
        buyCash = findViewById(R.id.buyCash);
        expenseCash = findViewById(R.id.expenseCash);
        buyAndSellMoney = findViewById(R.id.buyAndSellMoney);
        backBtn = findViewById(R.id.backBtn);
    }
}