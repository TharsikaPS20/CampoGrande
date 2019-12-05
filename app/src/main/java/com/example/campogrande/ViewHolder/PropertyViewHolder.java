package com.example.campogrande.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campogrande.R;

public class PropertyViewHolder extends RecyclerView.ViewHolder  {
    public TextView txtPropertyName, txtPropertyDescription, txtPropertyPrice, txtPropertySize;
    public ImageView imgPropertyImage;
    public RelativeLayout root;


    public PropertyViewHolder(@NonNull View itemView) {
        super(itemView);

        imgPropertyImage = (ImageView) itemView.findViewById(R.id.property_image);
        txtPropertyName = (TextView) itemView.findViewById(R.id.property_name1);
        txtPropertyDescription = (TextView) itemView.findViewById(R.id.property_description);
        txtPropertyPrice = (TextView) itemView.findViewById(R.id.property_price);
        txtPropertySize = (TextView) itemView.findViewById(R.id.property_size);
        root = itemView.findViewById(R.id.property_item_layout);

    }
}
