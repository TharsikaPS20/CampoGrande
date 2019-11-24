package com.example.campogrande;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HostReviews extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_reviews);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar1);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.host_profile){

                        Intent i = new Intent(HostReviews.this, Host.class);
                        startActivity(i);
                }

                return false;
            }
        });
    }


}