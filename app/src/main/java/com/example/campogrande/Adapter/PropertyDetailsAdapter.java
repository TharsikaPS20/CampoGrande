package com.example.campogrande.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.campogrande.PropertyDescriptionFragment;
import com.example.campogrande.PropertyFacilitiesFragment;

public class PropertyDetailsAdapter extends FragmentPagerAdapter {

    private int totalTabs;
    public PropertyDetailsAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                PropertyDescriptionFragment propertyDescriptionFragment1 = new PropertyDescriptionFragment();
                return propertyDescriptionFragment1;
            case 1:
                PropertyFacilitiesFragment propertyFacilitiesFragment1 = new PropertyFacilitiesFragment();
                return propertyFacilitiesFragment1;
            case 2:
                PropertyDescriptionFragment propertyDescriptionFragment2 = new PropertyDescriptionFragment();
                return propertyDescriptionFragment2;
            default:

                return null;


        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
