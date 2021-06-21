package com.grameen.bebshanikashapp.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grameen.bebshanikashapp.R;
import com.grameen.bebshanikashapp.View.Fragment.AccountingFragment;
import com.grameen.bebshanikashapp.View.Fragment.BebshaFragment;
import com.grameen.bebshanikashapp.View.Fragment.HomeFragment;

public class FragmentContainerActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        initViews();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new HomeFragment())
                .commit();

        initBottomNavigation();
    }


    private void initBottomNavigation() {

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.homeBn:
                        selectedFragment = new HomeFragment();
                        break;

                    case R.id.accountingBn:
                        selectedFragment = new AccountingFragment();
                        break;

                    case R.id.bebshaBn:
                        selectedFragment = new BebshaFragment();
                        break;

                    default:
                        break;
                }
                if (selectedFragment != null) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragmentContainer, selectedFragment)
                            .commit();
                }
                return true;
            }
        });
    }

    private void initViews() {

        frameLayout = findViewById(R.id.fragmentContainer);
        bottomNav = findViewById(R.id.bottomNavigationView);
    }
}