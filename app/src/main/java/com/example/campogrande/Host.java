package com.example.campogrande;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import de.hdodenhof.circleimageview.CircleImageView;

public class Host extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String uUserId, uName, uAge, uCity, uIntro, uDownloadProfImageUrl;
    private FloatingActionButton updateProfile;
    private static final int GalleryPick = 1;
    private Uri uriProfilePic;
    private MaterialEditText mName, mCity,mAge,mIntroduction;
    private StorageReference profilePictureRef;
    private DatabaseReference rootRef;

    private static final String TAG = "AddToDB";

    FirebaseDatabase mFirebaseDatabase;
    CircleImageView profilepic;
    private static final int CHOOSE_IMAGE =101;
    String profilePicUrl;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userid;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        else if(id==R.id.nav_profile)
        {
            openProfile();
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
        Intent intent = new Intent(Host.this, Reservations.class);
        startActivity(intent);
    }

    private void openProfile() {
        Intent intent = new Intent(Host.this, UserProfile.class);
        startActivity(intent);
    }

    private void openMessages() {
        Intent intent = new Intent(Host.this, Messages.class);
        startActivity(intent);
    }

    private void openListings() {
        Intent intent = new Intent(Host.this, Listings.class);
        startActivity(intent);
    }


    private void openAbout() {
        Intent intent = new Intent(Host.this, About.class);
        startActivity(intent);
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getApplicationContext(), Discover.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Intent intent1 = new Intent(Host.this,MainActivity.class);
        startActivity(intent1);
    }

    private void openFavourites() {
        Intent intent = new Intent(Host.this, Favourites.class);
        startActivity(intent);
    }

    private void openHome() {
        Intent intent = new Intent(Host.this, Discover.class);
        startActivity(intent);
    }


}




