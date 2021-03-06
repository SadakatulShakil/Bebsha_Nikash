package com.grameen.bebshanikashapp.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.grameen.bebshanikashapp.R;
import com.grameen.bebshanikashapp.View.Activity.ProductListActivity;

public class AccountingFragment extends Fragment {

    private TextView sellCash, buyCash, expenseCash;
    private EditText buyAndSellMoney;
    private TextView selectProduct;
    private Context context;
    public AccountingFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accounting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inItView(view);
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
                Intent intent = new Intent(context, ProductListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void inItView(View view) {
        sellCash = view.findViewById(R.id.sellCash);
        selectProduct = view.findViewById(R.id.selectProduct);
        buyCash = view.findViewById(R.id.buyCash);
        expenseCash = view.findViewById(R.id.expenseCash);
        buyAndSellMoney = view.findViewById(R.id.buyAndSellMoney);
    }
}