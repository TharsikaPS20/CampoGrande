package com.example.campogrande.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campogrande.Interface.ItemClickListener;
import com.example.campogrande.R;

public class PropertyViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
    public TextView txtPropertyName, txtPropertyDescription, txtPropertyPrice, txtPropertySize;
    public ImageView imgPropertyImage;
    public RelativeLayout root;

    //public OnPropertyListener listener;
    public PropertyViewHolder(@NonNull View itemView) {
        super(itemView);

        imgPropertyImage = (ImageView) itemView.findViewById(R.id.property_image);
        txtPropertyName = (TextView) itemView.findViewById(R.id.property_name1);
        txtPropertyDescription = (TextView) itemView.findViewById(R.id.property_description);
        txtPropertyPrice = (TextView) itemView.findViewById(R.id.property_price);
        txtPropertySize = (TextView) itemView.findViewById(R.id.property_size);
        root = itemView.findViewById(R.id.property_item_layout);
        //itemView.setOnClickListener(this);
    }

   // @Override
   /* public void onClick(View v) {
        listener.OnPropertyListener(getAdapterPosition());
    }*/

    /*public void setItemClickListener (ItemClickListener listener)
    {
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        listener.onListItemClick(getAdapterPosition());
    }*/

   /* public interface OnPropertyListener{
        void OnPropertyListener(int position);
    }*/
}
