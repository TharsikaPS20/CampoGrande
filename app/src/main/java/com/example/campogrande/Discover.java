package com.example.campogrande;

import android.content.Intent;
import android.os.Bundle;

import com.example.campogrande.Model.Properties;
import com.example.campogrande.ViewHolder.PropertyViewHolder;
import com.facebook.login.LoginManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;

public class Discover extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference PropertyRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PropertyRef = FirebaseDatabase.getInstance().getReference().child("Properties");
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

  @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_home);
      AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
      return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();


        if(id==R.id.nav_profile){
            openProfile();
        }
        else if(id==R.id.nav_reservations){
            openReservations();
        }

        else if(id == R.id.nav_favourites){
            openFavourites();
        }

        else if(id==R.id.nav_messages)
        {
            openMessages();
        }
        

        else if(id ==R.id.nav_host)
        {
            openHost();
        }

        else if(id ==R.id.nav_listings)
        {
            openListings();
        }


        else if (id==R.id.nav_about)
        {
            openAbout();
        }

        else if(id==R.id.nav_signout)
        {
            signOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openReservations() {
        Intent intent = new Intent(Discover.this, Reservations.class);
        startActivity(intent);
    }

    private void openMessages() {
        Intent intent = new Intent(Discover.this, Messages.class);
        startActivity(intent);
    }

    private void openHost() {
        Intent intent = new Intent(Discover.this, Host.class);
        startActivity(intent);
    }

    private void openListings() {
        Intent intent = new Intent(Discover.this, Listings.class);
        startActivity(intent);
    }


    private void openAbout() {
        Intent intent = new Intent(Discover.this, About.class);
        startActivity(intent);
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getApplicationContext(), Discover.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Intent intent1 = new Intent(Discover.this,MainActivity.class);
        startActivity(intent1);

    }

    private void openFavourites() {
        Intent intent = new Intent(Discover.this, Favourites.class);
        startActivity(intent);
    }

    private void openProfile() {
        Intent intent = new Intent(Discover.this, UserProfile.class);
        startActivity(intent);
    }


}

