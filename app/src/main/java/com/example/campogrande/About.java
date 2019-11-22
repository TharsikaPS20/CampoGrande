package com.example.campogrande;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.LinearLayout;

public class About extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    LinearLayout about, terms, faq, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        about = findViewById(R.id.about_layout);
        terms = findViewById(R.id.terms_layout);
        faq = findViewById(R.id.faq_layout);
        contact = findViewById(R.id.contact_layout);

        about.setOnClickListener(this);
        terms.setOnClickListener(this);
        faq.setOnClickListener(this);
        contact.setOnClickListener(this);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();


        if (id == R.id.nav_home) {
            openHome();
        } else if (id == R.id.nav_reservations) {
            openReservations();
        } else if (id == R.id.nav_favourites) {
            openFavourites();
        } else if (id == R.id.nav_profile) {
            openProfile();
        } else if (id == R.id.nav_host) {
            openHost();
        } else if (id == R.id.nav_listings) {
            openListings();
        } else if (id == R.id.nav_messages) {
            openMessages();
        } else if (id == R.id.nav_signout) {
            signOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openReservations() {
        Intent intent = new Intent(About.this, Reservations.class);
        startActivity(intent);
    }

    private void openProfile() {
        Intent intent = new Intent(About.this, UserProfile.class);
        startActivity(intent);
    }

    private void openHost() {
        Intent intent = new Intent(About.this, Host.class);
        startActivity(intent);
    }

    private void openListings() {
        Intent intent = new Intent(About.this, Listings.class);
        startActivity(intent);
    }


    private void openMessages() {
        Intent intent = new Intent(About.this, Messages.class);
        startActivity(intent);
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getApplicationContext(), Discover.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Intent intent1 = new Intent(About.this, MainActivity.class);
        startActivity(intent1);
    }

    private void openFavourites() {
        Intent intent = new Intent(About.this, Favourites.class);
        startActivity(intent);
    }

    private void openHome() {
        Intent intent = new Intent(About.this, Discover.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_layout:
                break;
            case R.id.contact_layout:
                Intent i = new Intent(About.this,Contact.class);
                startActivity(i);
        }
    }
}



