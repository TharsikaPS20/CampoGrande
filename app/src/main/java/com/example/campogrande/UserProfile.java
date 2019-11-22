package com.example.campogrande;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;


import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private StorageReference stor;
    RecyclerView.LayoutManager lm;
    TextView nameD, cityD, phoneD, introD;
    CircleImageView displayImg;
    private FirebaseUser user;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        stor = FirebaseStorage.getInstance().getReference();

        nameD=findViewById(R.id.fullnameRead);
        cityD=findViewById(R.id.cityRead);
        phoneD = findViewById(R.id.phoneRead);
        introD = findViewById(R.id.profiletextRead);
        displayImg=findViewById(R.id.image_profile_display);



        user = mAuth.getCurrentUser();
        if (user != null) {
            userid = user.getUid();
        }


        ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {String name = dataSnapshot.child("name").getValue().toString();
                String city = dataSnapshot.child("city").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String intro = dataSnapshot.child("intro").getValue().toString();
                String img = dataSnapshot.child("imageUrl").getValue(String.class);
                // displayImg.setImageURI(Uri.parse(image));
                nameD.setText(name);
                cityD.setText(city);
                phoneD.setText(phone);
                introD.setText(intro);
                Picasso.get().load(img).into(displayImg);}

                if(!dataSnapshot.exists())
                {
                    nameD.setText("");
                    cityD.setText("");
                    phoneD.setText("");
                    introD.setText("");
                    displayImg.setImageURI(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this,EditProfileActivity.class);
                startActivity(intent);
            }
        });

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

  /*  @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();


        if(id==R.id.nav_home){
            openHome();
        }
        else if(id==R.id.nav_reservations){
            openReservations();
        }

        else if(id == R.id.nav_favourites){
            openFavourites();
        }

        else if(id==R.id.nav_host)
        {
            openHost();
        }


        else if(id ==R.id.nav_messages)
        {
            openMessages();
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
        Intent intent = new Intent(UserProfile.this, Reservations.class);
        startActivity(intent);
    }

    private void openHost() {
        Intent intent = new Intent(UserProfile.this, Host.class);
        startActivity(intent);
    }

    private void openMessages() {
        Intent intent = new Intent(UserProfile.this, Messages.class);
        startActivity(intent);
    }

    private void openListings() {
        Intent intent = new Intent(UserProfile.this, Listings.class);
        startActivity(intent);
    }


    private void openAbout() {
        Intent intent = new Intent(UserProfile.this, About.class);
        startActivity(intent);
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getApplicationContext(), Discover.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Intent intent1 = new Intent(UserProfile.this,MainActivity.class);
        startActivity(intent1);
    }

    private void openFavourites() {
        Intent intent = new Intent(UserProfile.this, Favourites.class);
        startActivity(intent);
    }

    private void openHome() {
        Intent intent = new Intent(UserProfile.this, Discover.class);
        startActivity(intent);
    }


}




