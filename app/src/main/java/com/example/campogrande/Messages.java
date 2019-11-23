package com.example.campogrande;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.login.LoginManager;

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

    public class Messages extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

        FirebaseAuth mAuth;
        FirebaseUser user;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_messages);
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
            Intent intent = new Intent(Messages.this, Reservations.class);
            startActivity(intent);
        }

        private void openProfile() {
            user =mAuth.getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(Messages.this, UserProfile.class);
                startActivity(intent);
            }
            else{
                AccessFragment dialog = new AccessFragment();
                dialog.show(getSupportFragmentManager(),"this");
            }
        }

        private void openHost() {
            Intent intent = new Intent(Messages.this, Host.class);
            startActivity(intent);
        }

        private void openListings() {
            Intent intent = new Intent(Messages.this, Listings.class);
            startActivity(intent);
        }


        private void openAbout() {
            Intent intent = new Intent(Messages.this, About.class);
            startActivity(intent);
        }


        private void signOut() {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(getApplicationContext(), Discover.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Intent intent1 = new Intent(Messages.this,MainActivity.class);
            startActivity(intent1);
        }

        private void openFavourites() {
            Intent intent = new Intent(Messages.this, Favourites.class);
            startActivity(intent);
        }

        private void openHome() {
            Intent intent = new Intent(Messages.this, Discover.class);
            startActivity(intent);
        }


    }




