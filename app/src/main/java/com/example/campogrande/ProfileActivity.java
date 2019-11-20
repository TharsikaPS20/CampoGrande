package com.example.campogrande;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;*/


    MaterialEditText mName, mCity, mAge, mIntroduction;
    FloatingActionButton floatingActionButton;

    Uri uriProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Initialize views
       CircleImageView profilepic = findViewById(R.id.image_profile);
        mName = findViewById(R.id.fullname);
        mCity = findViewById(R.id.city);
        mAge = findViewById(R.id.age);
        mIntroduction=findViewById(R.id.profiletext);
        floatingActionButton = findViewById(R.id.fab);
        Button changePhoto = findViewById(R.id.photochange);
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,Messages.class);
                startActivity(i);
            }
        });




        //Initialize Firebase
       /* mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");*/



/*        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String name =""+dataSnapshot1.child("name").getValue();
                    String email =""+dataSnapshot1.child("email").getValue();
                    String phone = "" + dataSnapshot1.child("phone").getValue();
                    String image =""+dataSnapshot1.child("image").getValue();
                }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        })*/


        //Navigation drawer
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

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                uriProfilePic = data.getData();
                profilepic.setImageURI(uriProfilePic);
            }
            break;
        }
    }


    private void showImageChooser(){
        Intent intent = new Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

   /* private  void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);
    }*/

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
        NavController navController = Navigation.findNavController(this, R.id.nav_profile);
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

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
        Intent intent = new Intent(ProfileActivity.this, Reservations.class);
        startActivity(intent);
    }

    private void openMessages() {
        Intent intent = new Intent(ProfileActivity.this, Messages.class);
        startActivity(intent);
    }

    private void openHost() {
        Intent intent = new Intent(ProfileActivity.this, Host.class);
        startActivity(intent);
    }

    private void openListings() {
        Intent intent = new Intent(ProfileActivity.this, Listings.class);
        startActivity(intent);
    }


    private void openAbout() {
        Intent intent = new Intent(ProfileActivity.this, About.class);
        startActivity(intent);
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Intent intent1 = new Intent(ProfileActivity.this,MainActivity.class);
        startActivity(intent1);
    }

    private void openFavourites() {
        Intent intent = new Intent(ProfileActivity.this, Favourites.class);
        startActivity(intent);
    }

    private void openHome() {
        Intent intent = new Intent(ProfileActivity.this, Home.class);
        startActivity(intent);
    }


  /*  @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i== R.id.text_change_photo) {
            showImageChooser();
        } else if (i == R.id.fab) {
            Snackbar.make(v, "Replace with your action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }*/
}
