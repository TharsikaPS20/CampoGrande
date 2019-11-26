package com.example.campogrande;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.campogrande.Model.Properties;
import com.example.campogrande.ViewHolder.PropertyViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class HostListings extends AppCompatActivity {

    private DatabaseReference PropertyRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                            propertyViewHolder.txtPropertyPrice.setText(getString(R.string.price_d) + ": " + properties.getPrice() + " DKK");
                            propertyViewHolder.txtPropertySize.setText(getString(R.string.size_d) + ": " + properties.getSize() + " m²");
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


        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_new:
                        Intent intent = new Intent(HostListings.this, Listings.class);
                        startActivity(intent);
                        break;

                    case R.id.calendar:
                        Intent intent1 = new Intent(HostListings.this, HostBookings.class);
                        startActivity(intent1);
                        break;

                    //case R.id.listings:

                      //  break;



                }


                return false;
            }
        });
    }

}