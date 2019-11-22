package com.example.campogrande.Old_classes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.campogrande.Model.Properties;
import com.example.campogrande.R;
import com.example.campogrande.ViewHolder.PropertyViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewProperties extends AppCompatActivity {

    private DatabaseReference PropertyRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_properties);

        PropertyRef = FirebaseDatabase.getInstance().getReference().child("Properties");
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Properties> options =
                new FirebaseRecyclerOptions.Builder<Properties>()
                .setQuery(PropertyRef, Properties.class)
                .build();

        FirebaseRecyclerAdapter<Properties, PropertyViewHolder> adapter =
                new FirebaseRecyclerAdapter<Properties, PropertyViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PropertyViewHolder propertyViewHolder, int i, @NonNull Properties properties) {
                        propertyViewHolder.txtPropertyName.setText(properties.getPropertyName());
                        propertyViewHolder.txtPropertyDescription.setText(properties.getDescription());
                        propertyViewHolder.txtPropertyPrice.setText("Price: " + properties.getPrice() + " DKK");
                        propertyViewHolder.txtPropertySize.setText("Size: " + properties.getSize() + " m^2");
                        Picasso.get().load(properties.getImageUrl()).into(propertyViewHolder.imgPropertyImage);
                    }

                    @NonNull
                    @Override
                    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item_layout, parent, false);
                        PropertyViewHolder holder = new PropertyViewHolder(view);
                        return holder;

                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
