package com.grameen.bebshanikashapp.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.grameen.bebshanikashapp.Adapters.CustomerAdapter;
import com.grameen.bebshanikashapp.Adapters.CustomerListAdapter;
import com.grameen.bebshanikashapp.Adapters.MyProductAdapter;
import com.grameen.bebshanikashapp.Api.ApiInterface;
import com.grameen.bebshanikashapp.Api.RetrofitClient;
import com.grameen.bebshanikashapp.Model.AllCustomer.AllCustomer;
import com.grameen.bebshanikashapp.Model.AllCustomer.Customer;
import com.grameen.bebshanikashapp.Model.AllCustomer.Relation;
import com.grameen.bebshanikashapp.Model.AllProducts.AllProducts;
import com.grameen.bebshanikashapp.R;
import com.grameen.bebshanikashapp.View.Activity.AddCustomerActivity;
import com.grameen.bebshanikashapp.View.Activity.FragmentContainerActivity;
import com.grameen.bebshanikashapp.View.Activity.MainActivity;
import com.grameen.bebshanikashapp.View.Activity.MyProductActivity;
import com.grameen.bebshanikashapp.View.Activity.ProductUploadActivity;
import com.grameen.bebshanikashapp.View.Activity.ProfileActivity;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

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
    private String magic = "1", retrievedToken;
    public static LinearLayout profileOption;
    public static CircleImageView user_image;
    public static TextView businessName, phoneNumber;
    private ImageView magicTool, magicTool2;
    SharedPreferences preferences;
    private TextView customer, seller, supplier;
    private ArrayList<Customer> customerArrayList = new ArrayList<>();
    private CustomerAdapter customerAdapter;
    private ProgressBar progressBar;
    private RecyclerView listRevView1, listRevView2, listRevView3;
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
        preferences = context.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        retrievedToken  = preferences.getString("TOKEN",null);
        getAllCustomerList();
        View header = navigationView.getHeaderView(0);
        profileOption = header.findViewById(R.id.profileOption);
        user_image = header.findViewById(R.id.user_image);
        businessName = header.findViewById(R.id.businessName);
        phoneNumber = header.findViewById(R.id.phoneNumber);
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

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    customer.setBackground(getResources().getDrawable(R.drawable.background_card));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    seller.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    supplier.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    customer.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    seller.setBackground(getResources().getDrawable(R.drawable.background_card));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    supplier.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
            }
        });

        supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    customer.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    seller.setBackground(getResources().getDrawable(R.drawable.background_card_black));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    supplier.setBackground(getResources().getDrawable(R.drawable.background_card));
                }
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

        profileOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(context, ProfileActivity.class);
                startActivity(profile);
            }
        });
    }

    private void getAllCustomerList() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitClient1();
        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<AllCustomer> customerCall = api.getByAllCustomer("Bearer " + retrievedToken);

        customerCall.enqueue(new Callback<AllCustomer>() {
            @Override
            public void onResponse(Call<AllCustomer> call, Response<AllCustomer> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if(response.code() == 200){
                    progressBar.setVisibility(View.GONE);
                    AllCustomer customer = response.body();
                    customerArrayList.addAll(customer.getCustomers());
                }else if(response.code() == 401){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "নতুন করে লগ-ইন করুন", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    preferences = context.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", null).apply();
                    Intent logOutIntent = new Intent(context, MainActivity.class);
                    startActivity(logOutIntent);
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "সার্ভার এ সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show();
                }
                customerAdapter = new CustomerAdapter(context, customerArrayList);
                listRevView1.setLayoutManager(new LinearLayoutManager(context));
                listRevView1.setAdapter(customerAdapter);
                customerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AllCustomer> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " +t.getLocalizedMessage());
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

                    case R.id.productUploadNv:
                        Intent intent3 = new Intent(context, ProductUploadActivity.class);
                        startActivity(intent3);
                        //Toast.makeText(context, "About Under Construction be Patient!", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.myProductNv:
                        Intent intent4 = new Intent(context, MyProductActivity.class);
                        startActivity(intent4);
                        //Toast.makeText(context, "About Under Construction be Patient!", Toast.LENGTH_LONG).show();
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
                        getActivity().finish();
                        preferences = context.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                        preferences.edit().putString("TOKEN", null).apply();
                        Intent logOutIntent = new Intent(context, MainActivity.class);
                        startActivity(logOutIntent);
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
        customer = view.findViewById(R.id.customer);
        seller = view.findViewById(R.id.seller);
        supplier = view.findViewById(R.id.supplier);
        dToolbar = view.findViewById(R.id.toolbarLayLayout);
        addCustomerBt = view.findViewById(R.id.addCustomerBt);
        demoLay = view.findViewById(R.id.demoLay);
        cashCalculationView = view.findViewById(R.id.cashCalculationView);
        magicTool = view.findViewById(R.id.magicTool);
        magicTool2 = view.findViewById(R.id.magicTool2);
        listRevView1 = view.findViewById(R.id.listRevView1);
        listRevView2 = view.findViewById(R.id.listRevView2);
        listRevView3 = view.findViewById(R.id.listRevView3);
        progressBar = view.findViewById(R.id.progressBar);

        //progressBar = view.findViewById(R.id.progressBar);

    }
}