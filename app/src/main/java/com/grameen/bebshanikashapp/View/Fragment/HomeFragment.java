package com.grameen.bebshanikashapp.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.grameen.bebshanikashapp.Adapters.CustomerListAdapter;
import com.grameen.bebshanikashapp.R;
import com.grameen.bebshanikashapp.View.Activity.AddCustomerActivity;

public class HomeFragment extends Fragment {
    private Context context;
    private DrawerLayout drawerLayout;
    private Toolbar dToolbar;
    private NavigationView navigationView;
    private View rootView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private CustomerListAdapter customerListAdapter;
    private ExtendedFloatingActionButton addCustomerBt;
    private LinearLayout demoLay, cashCalculationView;
    private String magic = "1";
    private ImageView magicTool, magicTool2;
    public HomeFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        drawerLayout = rootView.findViewById(R.id.drawerLayout);
        navigationView = rootView.findViewById(R.id.navigationDrawer);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNavigationViewDrawer();
        inItView(view);

        if (getActivity() != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(dToolbar);
        }

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, dToolbar,
                R.string.drawer_open, R.string.drawer_closed);
        drawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawerToggle.setDrawerArrowDrawable(drawerToggle.getDrawerArrowDrawable());
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(8);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.actionbar_icon);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        customerListAdapter = new CustomerListAdapter(fm, getLifecycle());
        viewPager2.setAdapter(customerListAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("কাস্টমার"));
        tabLayout.addTab(tabLayout.newTab().setText("বিক্রেতা"));
        tabLayout.addTab(tabLayout.newTab().setText("সাপ্লায়ার"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        addCustomerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddCustomerActivity.class);
                startActivity(intent);
            }
        });

        magicTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoLay.setVisibility(View.GONE);
                magicTool2.setVisibility(View.VISIBLE);
            }
        });
        magicTool2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoLay.setVisibility(View.VISIBLE);
                magicTool2.setVisibility(View.GONE);
            }
        });
    }

    private void initNavigationViewDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch ((item.getItemId())) {

                    case R.id.buy_sellNv:
                        //fragment = new SelfServiceFragment();
                        Toast.makeText(context, "Settings Under Construction be Patient!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.expenseNv:
                        //fragment = new AttendanceFragment();
                        Toast.makeText(context, "FeedBack Under Construction be Patient!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.cashCalculateNv:
                        //fragment = new EmployeeDirectoryFragment();
                        Toast.makeText(context, "Admin Under Construction be Patient!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.dueCalculationNv:
                       /* Intent intent3 = new Intent(context, AboutUsActivity.class);
                        startActivity(intent3);*/
                        Toast.makeText(context, "About Under Construction be Patient!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.backupDataNv:
                       /* Intent intent3 = new Intent(context, AboutUsActivity.class);
                        startActivity(intent3);*/
                        Toast.makeText(context, "About Under Construction be Patient!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.reminderNv:
                       /* Intent intent3 = new Intent(context, AboutUsActivity.class);
                        startActivity(intent3);*/
                        Toast.makeText(context, "About Under Construction be Patient!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.settingsNv:
                       /* Intent intent3 = new Intent(context, AboutUsActivity.class);
                        startActivity(intent3);*/
                        Toast.makeText(context, "About Under Construction be Patient!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.referNv:
                       /* Intent intent3 = new Intent(context, AboutUsActivity.class);
                        startActivity(intent3);*/
                        Toast.makeText(context, "About Under Construction be Patient!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.logOutNv:
                        /*firebaseAuth.signOut();
                        finish();
                        Intent intent1 = new Intent(UserUiContainerActivity.this, MainActivity.class);
                        startActivity(intent1);*/
                        Toast.makeText(context, "Successfully Log Out", Toast.LENGTH_LONG).show();
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void inItView(View view) {
        /*drawerLayout = view.findViewById(R.id.drawerLayout);
        navigationView = view.findViewById(R.id.navigationDrawer);*/
        tabLayout = view.findViewById(R.id.tabLayOut);
        viewPager2 = view.findViewById(R.id.viewPager);
        dToolbar = view.findViewById(R.id.toolbarLayLayout);
        addCustomerBt = view.findViewById(R.id.addCustomerBt);
        demoLay = view.findViewById(R.id.demoLay);
        cashCalculationView = view.findViewById(R.id.cashCalculationView);
        magicTool = view.findViewById(R.id.magicTool);
        magicTool2 = view.findViewById(R.id.magicTool2);

        //progressBar = view.findViewById(R.id.progressBar);

    }
}