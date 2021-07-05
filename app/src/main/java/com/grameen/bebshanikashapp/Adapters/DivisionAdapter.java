package com.grameen.bebshanikashapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.grameen.bebshanikashapp.Model.Divisions.Division;
import com.grameen.bebshanikashapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DivisionAdapter extends ArrayAdapter<Division> {
    private ArrayList<Division> divisionArrayList;
    private Context context;
    public DivisionAdapter(@NonNull Context context, ArrayList<Division> divisionArrayList) {
        super(context, 0, divisionArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }


    @Override
    public boolean isEnabled(int position) {
        return position != -1;
    }

    private View initView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_view, parent, false);

        }

        TextView productTypeName = convertView.findViewById(R.id.spinnerView);
        Division division = getItem(position);


        if (division != null) {
            productTypeName.setText(division.getBnName());
        }

        return convertView;
    }
}
