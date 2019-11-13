package com.example.campogrande.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campogrande.Interface.ItemClickListener;
import com.example.campogrande.R;

public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtPropertyName, txtPropertyDescription, txtPropertyPrice, txtPropertySize;
    public ImageView imgPropertyImage;
    public ItemClickListener listener;
    public PropertyViewHolder(@NonNull View itemView) {
        super(itemView);

        imgPropertyImage = (ImageView) itemView.findViewById(R.id.property_image);
        txtPropertyName = (TextView) itemView.findViewById(R.id.property_name1);
        txtPropertyDescription = (TextView) itemView.findViewById(R.id.property_description);
        txtPropertyPrice = (TextView) itemView.findViewById(R.id.property_price);
        txtPropertySize = (TextView) itemView.findViewById(R.id.property_size);
    }

    public void setItemClickListener (ItemClickListener listener)
    {
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false );
    }
}
