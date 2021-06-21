package com.grameen.bebshanikashapp.Adapters;

import com.grameen.bebshanikashapp.View.Fragment.CustomerListFragment;
import com.grameen.bebshanikashapp.View.Fragment.SellerListFragment;
import com.grameen.bebshanikashapp.View.Fragment.SupplierListFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CustomerListAdapter extends FragmentStateAdapter {
    public CustomerListAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new SellerListFragment();

            case 2:
                return new SupplierListFragment();

        }
        return new CustomerListFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
