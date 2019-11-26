package com.example.campogrande;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class Host extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar1);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.host_reviews) {
                    Intent intent2 = new Intent(Host.this, HostReviews.class);
                    startActivity(intent2);
                }

                return false;
            }

        });
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.host, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            int id = item.getItemId();
            if (id == R.id.action_edit) {
                return true;
            }
            if (id == R.id.action_delete) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }


        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){
            int id = menuItem.getItemId();


            if (id == R.id.nav_home) {
                openHome();
            } else if (id == R.id.nav_reservations) {
                openReservations();
            } else if (id == R.id.nav_favourites) {
                openFavourites();
            } else if (id == R.id.nav_profile) {
                openProfile();
            } else if (id == R.id.nav_messages) {
                openMessages();
            } else if (id == R.id.nav_listings) {
                openListings();
            } else if (id == R.id.nav_about) {
                openAbout();
            } else if (id == R.id.nav_signout) {
                signOut();
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        private void openReservations () {
            Intent intent = new Intent(Host.this, Reservations.class);
            startActivity(intent);
        }

        private void openProfile () {
            user =mAuth.getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(Host.this, UserProfile.class);
                startActivity(intent);
            }
            else{
                AccessFragment dialog = new AccessFragment();
                dialog.show(getSupportFragmentManager(),"this");
            }
        }

        private void openMessages () {
            Intent intent = new Intent(Host.this, Messages.class);
            startActivity(intent);
        }

        private void openListings () {
            Intent intent = new Intent(Host.this, Listings.class);
            startActivity(intent);
        }


        private void openAbout () {
            Intent intent = new Intent(Host.this, About.class);
            startActivity(intent);
        }


        private void signOut () {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(getApplicationContext(), Discover.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Intent intent1 = new Intent(Host.this, MainActivity.class);
            startActivity(intent1);
        }

        private void openFavourites () {
            Intent intent = new Intent(Host.this, Favourites.class);
            startActivity(intent);
        }

        private void openHome () {
            Intent intent = new Intent(Host.this, Discover.class);
            startActivity(intent);
        }


    }




