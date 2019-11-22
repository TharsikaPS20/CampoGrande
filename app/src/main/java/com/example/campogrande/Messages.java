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

    public class Messages extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_messages);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

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
            Intent intent = new Intent(Messages.this, UserProfile.class);
            startActivity(intent);
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




