package com.example.campogrande;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.campogrande.Adapter.PropertyFacilitiesAdapter;
import com.example.campogrande.Model.PropertyFacilityModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyFacilitiesFragment extends Fragment {


    public PropertyFacilitiesFragment() {
        // Required empty public constructor
    }


    private RecyclerView propertyFacilitiesRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_property_facilities, container, false);
        propertyFacilitiesRecyclerView = view.findViewById(R.id.property_facilities_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        propertyFacilitiesRecyclerView.setLayoutManager(linearLayoutManager);

        List<PropertyFacilityModel> propertyFacilityModelList = new ArrayList<>();
        propertyFacilityModelList.add(new PropertyFacilityModel(getString(R.string.electricity), "100 DKK"));
        propertyFacilityModelList.add(new PropertyFacilityModel(getString(R.string.water), "50 DKK"));
        propertyFacilityModelList.add(new PropertyFacilityModel(getString(R.string.playground), "0 DKK"));
        propertyFacilityModelList.add(new PropertyFacilityModel(getString(R.string.shower), "100 DKK"));

        PropertyFacilitiesAdapter propertyFacilitiesAdapter = new PropertyFacilitiesAdapter(propertyFacilityModelList);
        propertyFacilitiesRecyclerView.setAdapter(propertyFacilitiesAdapter);
        propertyFacilitiesAdapter.notifyDataSetChanged();
        return view;
    }

}
