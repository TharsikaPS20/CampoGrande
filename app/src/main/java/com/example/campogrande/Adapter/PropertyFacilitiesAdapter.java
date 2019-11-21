package com.example.campogrande.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campogrande.Model.PropertyFacilityModel;
import com.example.campogrande.R;

import java.util.List;

public class PropertyFacilitiesAdapter extends RecyclerView.Adapter<PropertyFacilitiesAdapter.ViewHolder>{

    private List<PropertyFacilityModel> propertyFacilityModelList;

    public PropertyFacilitiesAdapter(List<PropertyFacilityModel> propertyFacilityModelList) {
        this.propertyFacilityModelList = propertyFacilityModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_facilities_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String facilityTitle = propertyFacilityModelList.get(position).getFacilityName();
        String facilityDetail = propertyFacilityModelList.get(position).getFacilityValue();
        holder.setFacilities(facilityTitle, facilityDetail);
    }

    @Override
    public int getItemCount() {
        return propertyFacilityModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView facilityName;
        private TextView facilityValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            facilityName = itemView.findViewById(R.id.facility_name);
            facilityValue = itemView.findViewById(R.id.facility_value);
        }

        private void setFacilities(String facilityTitle, String facilityDetail) {
            facilityName.setText(facilityTitle);
            facilityValue.setText(facilityDetail);
        }
    }
}
